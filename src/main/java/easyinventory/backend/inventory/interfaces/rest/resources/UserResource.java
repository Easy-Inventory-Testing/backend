package easyinventory.backend.inventory.interfaces.rest.resources;

public record UserResource(Long id, String name, String lastName, String email, String password) {
}
