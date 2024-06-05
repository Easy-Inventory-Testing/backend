package easyinventory.backend.inventory.domain.model.commands;

import java.util.Date;

public record UpdateSaleCommand(Long id, String name, Date saleDate, Integer totalCost) {
}
