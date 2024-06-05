package easyinventory.backend.inventory.interfaces.rest.resources;

public record CreateUserResource(String name, String lastName, String email, String password) {
}
