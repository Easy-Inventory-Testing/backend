package easyinventory.backend.inventory.interfaces.rest.transform;

import easyinventory.backend.inventory.application.internal.dtos.SaleRequestDto;
import easyinventory.backend.inventory.domain.model.commands.CreateSaleCommand;

import java.util.List;

public class CreateSaleCommandFromResourceAssembler {
    public static CreateSaleCommand toCommandFromDto(List<SaleRequestDto> requestDtoList){
        return new CreateSaleCommand(requestDtoList);
    }
}
