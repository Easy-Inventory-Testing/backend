package easyinventory.backend.inventory.interfaces.rest.transform;

import easyinventory.backend.inventory.domain.model.commands.UpdateSaleCommand;
import easyinventory.backend.inventory.interfaces.rest.resources.UpdateSaleResource;

public class UpdateSaleCommandFromResourceAssembler {
    public static UpdateSaleCommand toCommandFromResource(Long saleId, UpdateSaleResource resource){
        return new UpdateSaleCommand(saleId, resource.name(), resource.saleDate(), resource.totalCost());
    }
}
