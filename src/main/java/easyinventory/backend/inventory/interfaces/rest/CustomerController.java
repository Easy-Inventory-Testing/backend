package easyinventory.backend.inventory.interfaces.rest;

import easyinventory.backend.inventory.domain.model.commands.DeleteCustomerCommand;
import easyinventory.backend.inventory.domain.model.commands.UpdateCustomerCommand;
import easyinventory.backend.inventory.domain.model.queries.GetAllCustomersQuery;
import easyinventory.backend.inventory.domain.model.queries.GetCustomerByIdQuery;
import easyinventory.backend.inventory.domain.services.CustomerCommandService;
import easyinventory.backend.inventory.domain.services.CustomerQueryService;
import easyinventory.backend.inventory.interfaces.rest.resources.CreateCustomerResource;
import easyinventory.backend.inventory.interfaces.rest.resources.CustomerResource;
import easyinventory.backend.inventory.interfaces.rest.transform.CreateCustomerCommandFromResourceAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.CustomerResourceFromEntityAssembler;
import easyinventory.backend.inventory.interfaces.rest.transform.UpdateCustomerCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customers", description = "Customers Management Endpoints")
public class CustomerController {
    private final CustomerCommandService customerCommandService;
    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerCommandService customerCommandService, CustomerQueryService customerQueryService) {
        this.customerCommandService = customerCommandService;
        this.customerQueryService = customerQueryService;
    }

    @PostMapping
    public ResponseEntity<CustomerResource> createCustomer(@RequestBody CreateCustomerResource resource){
        var createCustomerCommand = CreateCustomerCommandFromResourceAssembler.toCommandFromResource(resource);
        var customerId = customerCommandService.handle(createCustomerCommand);
        if(customerId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var getCustomerByIdQuery = new GetCustomerByIdQuery(customerId);
        var customer = customerQueryService.handle(getCustomerByIdQuery);
        if(customer.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var customerResource = CustomerResourceFromEntityAssembler.toResourceFromEntity(customer.get());
        return new ResponseEntity<>(customerResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResource> putCustomer(@PathVariable Long id, @RequestBody CreateCustomerResource resource){
        var updateCustomerCommand = UpdateCustomerCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedCustomer = customerCommandService.handle(updateCustomerCommand);
        if(updatedCustomer.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var customerResource = CustomerResourceFromEntityAssembler.toResourceFromEntity(updatedCustomer.get());
        return ResponseEntity.ok(customerResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResource> getCustomerById(@PathVariable Long id){
        var getCustomerByIdQuery = new GetCustomerByIdQuery(id);
        var customer = customerQueryService.handle(getCustomerByIdQuery);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var customerResource = CustomerResourceFromEntityAssembler.toResourceFromEntity(customer.get());
        return ResponseEntity.ok(customerResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCustomer(@PathVariable Long id){
        var deleteCustomerCommand = new DeleteCustomerCommand(id);
        var customerId = customerCommandService.handle(deleteCustomerCommand);
        return ResponseEntity.ok(customerId);
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResource>> getAllCustomers(){
        var getAllCustomersQuery = new GetAllCustomersQuery();
        var customers = customerQueryService.handle(getAllCustomersQuery);
        if (customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var customerResource = customers.stream().map(CustomerResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(customerResource);
    }

}
