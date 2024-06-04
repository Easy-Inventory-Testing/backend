package easyinventory.backend.inventory.application.internal.queryservices;

import easyinventory.backend.inventory.domain.model.aggregates.Product;
import easyinventory.backend.inventory.domain.model.queries.GetAllProductsQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductByIdQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductsByUserIdQuery;
import easyinventory.backend.inventory.domain.services.ProductQueryService;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.id());
    }

    @Override
    public List<Product> handle(GetProductsByUserIdQuery query) {
        return productRepository.findByUserId(query.userId());
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }
}
