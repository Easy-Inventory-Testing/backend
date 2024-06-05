package easyinventory.backend.inventory.interfaces.rest.resources;

import java.util.Date;

public record UpdateSaleResource(String name, Date saleDate, Integer totalCost) {
}
