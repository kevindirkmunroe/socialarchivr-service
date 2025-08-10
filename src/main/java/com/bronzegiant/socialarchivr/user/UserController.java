
package com.bronzegiant.socialarchivr.user;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bronzegiant.socialarchivr.security.AccessToken;
import com.bronzegiant.socialarchivr.security.AccessTokenManager;
import com.bronzegiant.socialarchivr.security.LoginCredentials;
import com.bronzegiant.socialarchivr.user.profileimage.UserProfileImage;
import com.bronzegiant.socialarchivr.user.profileimage.UserProfileImageService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
class UserController {

  private final UserRepository repository;
  private final UserProfileImageService profileImageService;

  UserController(UserRepository repository, UserProfileImageService upiService) {
    this.repository = repository;
    this.profileImageService = upiService;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping
  CollectionModel<EntityModel<User>> all() {

	  List<EntityModel<User>> users = repository.findAll().stream()
	      .map(user -> EntityModel.of(user,
	          linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
	          linkTo(methodOn(UserController.class).all()).withRel("users")))
	      .collect(Collectors.toList());

	  return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
  // end::get-aggregate-root[]
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest sur) {

	  User newUser = new User(sur.getFirstname(), sur.getLastname(), sur.getEmail(), sur.getPassword());
	  try {
	      // TODO: hash password before saving (e.g. BCryptPasswordEncoder)
		  repository.save(newUser);
		  
	      return ResponseEntity.ok("User signed up: " + sur.getEmail());
	  }catch(Error e) {
		  return ResponseEntity.badRequest().body("Invalid user signup request: " + e.getMessage());
	  }

  }
  
  @PostMapping("/login")
  ResponseEntity<AccessToken> newUser(@RequestBody LoginCredentials credentials) {
    AccessToken newToken =  AccessTokenManager.createToken(credentials, repository);
    if(newToken == null) {
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(newToken);
    }
    return ResponseEntity.ok(newToken);
  }
  
  @GetMapping("/{id}")
  EntityModel<User> one(@PathVariable Long id) {
    
    User emp = repository.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
    
    return EntityModel.of(emp, 
    		linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
    		linkTo(methodOn(UserController.class).all()).withRel("Users"));
  }

  @PutMapping("/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(User -> {
          User.setFirstname(newUser.getFirstname());
          User.setLastname(newUser.getLastname());
        User.setEmail(newUser.getEmail());
        return repository.save(User);
      })
      .orElseGet(() -> {
        return repository.save(newUser);
      });
  }

  @DeleteMapping("{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
  
  @PostMapping("/{id}/profile-image")
  public ResponseEntity<?> uploadProfileImage(@PathVariable Long id,
                                              @RequestParam MultipartFile image) {
      try {
          profileImageService.uploadProfileImage(id, image);
          return ResponseEntity.ok("Profile image uploaded successfully");
      } catch (IllegalArgumentException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      } catch (IOException e) {
          return ResponseEntity.status(500).body("Failed to read image file");
      }
  }

  @GetMapping("/{id}/profile-image")
  public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
	    return profileImageService.getProfileImage(id)
	            .map((UserProfileImage image) -> 
	                ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
	                    .contentType(MediaType.parseMediaType(image.getContentType()))
	                    .body(image.getImageData())
	            )
	            .orElseGet(() -> ResponseEntity.notFound().build());
  }
}