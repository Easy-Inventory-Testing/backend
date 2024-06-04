package easyinventory.backend.inventory.interfaces.rest;

import easyinventory.backend.inventory.domain.model.commands.DeleteSaleCommand;
import easyinventory.backend.inventory.domain.model.queries.GetAllSalesQuery;
import easyinventory.backend.inventory.domain.model.queries.GetSaleByIdQuery;
import easyinventory.backend.inventory.domain.services.SaleCommandService;
import easyinventory.backend.inventory.domain.services.SaleQueryService;
import easyinventory.backend.inventory.interfaces.rest.resources.CreateSaleResource;
import easyinventory.backend.inventory.interfaces.rest.resources.SaleResource;
import easyinventory.backend.inventory.interfaces.rest.transform.CreateSaleCommandFromResourceAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.SaleResourceFromEntityAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.UpdateSaleCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/sales", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sales", description = "Sales Management Endpoints")
public class SaleController {
    private final SaleCommandService saleCommandService;
    private final SaleQueryService saleQueryService;

    public SaleController(SaleCommandService saleCommandService, SaleQueryService saleQueryService) {
        this.saleCommandService = saleCommandService;
        this.saleQueryService = saleQueryService;
    }

    @PostMapping
    public ResponseEntity<SaleResource> createSale(@RequestBody CreateSaleResource resource){
        var createSaleCommand = CreateSaleCommandFromResourceAssembler.toCommandFromResource(resource);
        var saleId = saleCommandService.handle(createSaleCommand);
        if(saleId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var getSaleByIdQuery = new GetSaleByIdQuery(saleId);
        var sale = saleQueryService.handle(getSaleByIdQuery);
        if(sale.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
        return new ResponseEntity<>(saleResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResource> putSale(@PathVariable Long id, @RequestBody CreateSaleResource resource){
        var updateSaleCommand = UpdateSaleCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedSale = saleCommandService.handle(updateSaleCommand);
        if(updatedSale.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(updatedSale.get());
        return ResponseEntity.ok(saleResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResource> getSaleById(@PathVariable Long id){
        var getSaleByIdQuery = new GetSaleByIdQuery(id);
        var sale = saleQueryService.handle(getSaleByIdQuery);
        if (sale.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var saleResource = SaleResourceFromEntityAssembler.toResourceFromEntity(sale.get());
        return ResponseEntity.ok(saleResource);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Long> deleteSale(@PathVariable String name){
        var deleteSaleCommand = new DeleteSaleCommand(name);
        var saleId = saleCommandService.handle(deleteSaleCommand);
        return ResponseEntity.ok(saleId);
    }

    @GetMapping()
    public ResponseEntity<List<SaleResource>> getAllSales(){
        var getAllSalesQuery = new GetAllSalesQuery();
        var sales = saleQueryService.handle(getAllSalesQuery);
        if (sales.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var saleResource = sales.stream().map(SaleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(saleResource);
    }

}
