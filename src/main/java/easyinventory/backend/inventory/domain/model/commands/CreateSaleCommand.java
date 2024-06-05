package easyinventory.backend.inventory.domain.model.commands;

import easyinventory.backend.inventory.application.internal.dtos.SaleRequestDto;

import java.util.List;

public record CreateSaleCommand(List<SaleRequestDto> saleRequestDtoList) {
}
