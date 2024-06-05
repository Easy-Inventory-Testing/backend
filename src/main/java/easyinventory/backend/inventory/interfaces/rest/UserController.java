package easyinventory.backend.inventory.interfaces.rest;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.domain.model.commands.CreateUserCommand;
import easyinventory.backend.inventory.domain.model.commands.UpdateUserCommand;
import easyinventory.backend.inventory.domain.model.queries.*;
import easyinventory.backend.inventory.domain.services.UserCommandService;
import easyinventory.backend.inventory.domain.services.UserQueryService;
import easyinventory.backend.inventory.interfaces.rest.resources.*;
import easyinventory.backend.inventory.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Users Management Endpoints")
public class UserController {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource resource){
        CreateUserCommand createUserCommand = CreateUserCommandFromResourceAssembler
                .toCommandFromResource(resource);
        Long userId = userCommandService.handle(createUserCommand);
        if(userId == 0L){
            return ResponseEntity.badRequest().build();
        }
        GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery(userId);
        Optional<User> user = userQueryService.handle(getUserByIdQuery);
        if(user.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        UserResource userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> putUser(@PathVariable Long id, @RequestBody UpdateUserResource resource){
        UpdateUserCommand updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<User> updatedUser = userCommandService.handle(updateUserCommand);
        if(updatedUser.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        UserResource userResource = UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get());
        return ResponseEntity.ok(userResource);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id){
        GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery(id);
        Optional<User> user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserResource saleResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(saleResource);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResource> getUserByEmail(@PathVariable String email){
        GetUserByEmailQuery getUserByEmailQuery = new GetUserByEmailQuery(email);
        Optional<User> user = userQueryService.handle(getUserByEmailQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserResource saleResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(saleResource);
    }

    @GetMapping()
    public ResponseEntity<List<UserResource>> getAllUsers(){
        GetAllUsersQuery getAllUsersQuery = new GetAllUsersQuery();
        List<User> userList = userQueryService.handle(getAllUsersQuery);
        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserResource> userResources = userList.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }
}
