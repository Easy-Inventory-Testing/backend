package easyinventory.backend.inventory.interfaces.rest.transform;

import easyinventory.backend.inventory.domain.model.commands.UpdateUserCommand;
import easyinventory.backend.inventory.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource){
        return new UpdateUserCommand(userId, resource.name(), resource.lastName(), resource.email(),
                resource.password());
    }
}
