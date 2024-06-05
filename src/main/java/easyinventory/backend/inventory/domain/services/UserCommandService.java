package easyinventory.backend.inventory.domain.services;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.domain.model.commands.*;

import java.util.Optional;

public interface UserCommandService {

    Long handle(CreateUserCommand command);
    Optional<User> handle(UpdateUserCommand command);
}
