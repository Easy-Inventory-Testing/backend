package easyinventory.backend.inventory.application.internal.queryservices;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.domain.model.queries.GetAllUsersQuery;
import easyinventory.backend.inventory.domain.model.queries.GetUserByEmailQuery;
import easyinventory.backend.inventory.domain.model.queries.GetUserByIdQuery;
import easyinventory.backend.inventory.domain.services.UserQueryService;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }
}
