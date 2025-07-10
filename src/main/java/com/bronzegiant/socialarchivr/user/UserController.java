
package com.bronzegiant.socialarchivr.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class UserController {

  private final UserRepository repository;

  UserController(UserRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/users")
  CollectionModel<EntityModel<User>> all() {

	  List<EntityModel<User>> users = repository.findAll().stream()
	      .map(user -> EntityModel.of(user,
	          linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
	          linkTo(methodOn(UserController.class).all()).withRel("users")))
	      .collect(Collectors.toList());

	  return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
  // end::get-aggregate-root[]
  }

  @PostMapping("/users")
  User newUser(@RequestBody User newUser) {
    return repository.save(newUser);
  }

  // Single item
  
  @GetMapping("/users/{id}")
  EntityModel<User> one(@PathVariable Long id) {
    
    User emp = repository.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
    
    return EntityModel.of(emp, 
    		linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
    		linkTo(methodOn(UserController.class).all()).withRel("Users"));
  }

  @PutMapping("/users/{id}")
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

  @DeleteMapping("/Users/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
}