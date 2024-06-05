package easyinventory.backend.inventory.interfaces.rest.transform;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user){
        return new UserResource(user.getId(), user.getName(), user.getLastName(), user.getEmail(),
                user.getPassword());
    }
}
