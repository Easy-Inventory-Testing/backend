package easyinventory.backend.inventory.domain.model.commands;

public record CreateUserCommand(String name, String lastName, String email, String password) {
}
