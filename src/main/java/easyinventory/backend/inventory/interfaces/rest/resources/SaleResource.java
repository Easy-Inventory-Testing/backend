package easyinventory.backend.inventory.interfaces.rest.resources;

import java.util.Date;

public record SaleResource(Long id, String name, Date saleDate, Integer totalCost) {
}
