package easyinventory.backend.inventory.application.internal.commandservices;

import easyinventory.backend.inventory.domain.model.aggregates.Product;
import easyinventory.backend.inventory.domain.model.aggregates.Sale;
import easyinventory.backend.inventory.domain.model.commands.CreateSaleCommand;
import easyinventory.backend.inventory.domain.model.commands.DeleteSaleCommand;
import easyinventory.backend.inventory.domain.model.commands.UpdateSaleCommand;
import easyinventory.backend.inventory.domain.services.SaleCommandService;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.ProductRepository;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long handle(CreateSaleCommand command) {
        Sale sale = new Sale();
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        sale.setSaleDate(date);
        command.saleRequestDtoList().forEach(saleRequestDto -> {
            List<Product> productsToAdd = getProductByIdToList(saleRequestDto.getProductId(), saleRequestDto.getQuantity());
            sale.addProducts(productsToAdd);
        });
        sale.calculateTotalCost();
        saleRepository.save(sale);
        return sale.getId();
    }

    @Override
    public Optional<Sale> handle(UpdateSaleCommand command) {
        var saleToUpdate = saleRepository.findByName(command.name()).orElseThrow(() -> new RuntimeException("Sale not found"));
        if(command.saleDate() != null)
            saleToUpdate.setSaleDate(command.saleDate());
        if(command.totalCost() != null)
            saleToUpdate.setTotalCost(command.totalCost());
        saleRepository.save(saleToUpdate);
        return Optional.of(saleToUpdate);
    }

    @Override
    public Long handle(DeleteSaleCommand command) {
        var sale = saleRepository.findById(command.id()).orElseThrow(() -> new RuntimeException("Sale not found"));
        saleRepository.delete(sale);
        return sale.getId();
    }

    private List<Product> getProductByIdToList(Long productId, Integer quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        Product product = optionalProduct.get();

        return IntStream.range(0, quantity)
                .mapToObj(i -> product)
                .collect(Collectors.toList());
    }
}
