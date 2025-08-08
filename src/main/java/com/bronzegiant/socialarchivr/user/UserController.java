
package com.bronzegiant.socialarchivr.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
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
import org.springframework.web.bind.annotation.RestController;

import com.bronzegiant.socialarchivr.security.AccessToken;
import com.bronzegiant.socialarchivr.security.AccessTokenManager;
import com.bronzegiant.socialarchivr.security.LoginCredentials;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
class UserController {

  private final UserRepository repository;

  UserController(UserRepository repository) {
    this.repository = repository;
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

//  @PostMapping("/users")
//  User newUser(@RequestBody User newUser) {
//    return repository.save(newUser);
//  }
  @PostMapping("/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
      // TODO: save user to DB (PostgreSQL or Mongo)
      // TODO: hash password before saving (e.g. BCryptPasswordEncoder)

      return ResponseEntity.ok("User signed up: " + signupRequest.getEmail());
  }
  
  @PostMapping("/login")
  AccessToken newUser(@RequestBody LoginCredentials credentials) {
    AccessToken newToken =  AccessTokenManager.createToken(credentials, repository);
    return newToken;
  }

  // Single item
  
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
}