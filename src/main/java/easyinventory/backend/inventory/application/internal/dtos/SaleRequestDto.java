package easyinventory.backend.inventory.application.internal.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRequestDto {

    Long productId;

    Integer quantity;

    public SaleRequestDto() {
    }

}
