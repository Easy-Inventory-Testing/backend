package easyinventory.backend.inventory.domain.services;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.domain.model.queries.GetAllUsersQuery;
import easyinventory.backend.inventory.domain.model.queries.GetUserByEmailQuery;
import easyinventory.backend.inventory.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {

    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}
