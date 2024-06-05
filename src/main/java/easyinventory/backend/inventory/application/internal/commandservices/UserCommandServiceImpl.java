package easyinventory.backend.inventory.application.internal.commandservices;

import easyinventory.backend.inventory.domain.model.aggregates.User;
import easyinventory.backend.inventory.domain.model.commands.CreateUserCommand;
import easyinventory.backend.inventory.domain.model.commands.UpdateUserCommand;
import easyinventory.backend.inventory.domain.services.UserCommandService;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long handle(CreateUserCommand command) {
        User user = new User(command.name(), command.lastName(), command.email(),
                command.password());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        Optional<User> userToUpdate = userRepository.findById(command.userId());
        if(userToUpdate.isEmpty()) return Optional.empty();
        User userUpdated = userToUpdate.get();
        userUpdated.setName(command.name());
        userUpdated.setLastName(command.lastName());
        userUpdated.setEmail(command.email());
        userUpdated.setPassword(command.password());
        userRepository.save(userUpdated);
        return Optional.of(userUpdated);
    }
}
