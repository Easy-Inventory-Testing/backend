package easyinventory.backend.inventory.domain.services;

import easyinventory.backend.inventory.domain.model.aggregates.Product;
import easyinventory.backend.inventory.domain.model.queries.GetAllProductsQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductByIdQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    Optional<Product> handle(GetProductByIdQuery query);
    List<Product> handle(GetProductsByUserIdQuery query);

    List<Product> handle(GetAllProductsQuery query);
}
