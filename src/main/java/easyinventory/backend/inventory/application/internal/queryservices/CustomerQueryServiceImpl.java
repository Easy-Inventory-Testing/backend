package easyinventory.backend.inventory.application.internal.queryservices;

import easyinventory.backend.inventory.domain.model.aggregates.Customer;
import easyinventory.backend.inventory.domain.model.queries.GetAllCustomersQuery;
import easyinventory.backend.inventory.domain.model.queries.GetCustomerByIdQuery;
import easyinventory.backend.inventory.domain.services.CustomerQueryService;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerQueryServiceImpl implements CustomerQueryService {
    private final CustomerRepository customerRepository;

    public CustomerQueryServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> handle(GetCustomerByIdQuery query) {
        return customerRepository.findById(query.id());
    }

    @Override
    public List<Customer> handle(GetAllCustomersQuery query) {
        return customerRepository.findAll();
    }
}
