package easyinventory.backend.inventory.interfaces.rest;

import easyinventory.backend.inventory.domain.model.commands.DeleteProductCommand;
import easyinventory.backend.inventory.domain.model.queries.GetAllProductsQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductByIdQuery;
import easyinventory.backend.inventory.domain.model.queries.GetProductsByUserIdQuery;
import easyinventory.backend.inventory.domain.services.ProductCommandService;
import easyinventory.backend.inventory.domain.services.ProductQueryService;
import easyinventory.backend.inventory.interfaces.rest.resources.CreateProductResource;
import easyinventory.backend.inventory.interfaces.rest.resources.ProductResource;
import easyinventory.backend.inventory.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Products Management Endpoints")
public class ProductController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResource>> getAllProducts(){
        var getAllProductsQuery = new GetAllProductsQuery();
        var products = productQueryService.handle(getAllProductsQuery);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var productResource = products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(productResource);
    }

    @PostMapping
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource resource){
        var createProductCommand = CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var productId = productCommandService.handle(createProductCommand);
        if(productId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var product = productQueryService.handle(getProductByIdQuery);
        if(product.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return new ResponseEntity<>(productResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> putProduct(@PathVariable Long id, @RequestBody CreateProductResource resource){
        var updateProductCommand = UpdateProductCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedProduct = productCommandService.handle(updateProductCommand);
        if(updatedProduct.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(updatedProduct.get());
        return ResponseEntity.ok(productResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResource> getProductById(@PathVariable Long id){
        var getProductByIdQuery = new GetProductByIdQuery(id);
        var product = productQueryService.handle(getProductByIdQuery);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return ResponseEntity.ok(productResource);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<ProductResource>> getProductByUserId(@PathVariable Long userId){
        var getProductByUserIdQuery = new GetProductsByUserIdQuery(userId);
        var products = productQueryService.handle(getProductByUserIdQuery);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var productResource = products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(productResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id){
        var deleteProductCommand = new DeleteProductCommand(id);
        var productId = productCommandService.handle(deleteProductCommand);
        return ResponseEntity.ok(productId);
    }
}
