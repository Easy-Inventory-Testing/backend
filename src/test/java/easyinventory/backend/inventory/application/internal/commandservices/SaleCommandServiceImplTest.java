package easyinventory.backend.inventory.application.internal.commandservices;

import easyinventory.backend.inventory.application.internal.dtos.SaleRequestDto;
import easyinventory.backend.inventory.domain.model.aggregates.Product;
import easyinventory.backend.inventory.domain.model.aggregates.Sale;
import easyinventory.backend.inventory.domain.model.commands.*;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.ProductRepository;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaleCommandServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleCommandServiceImpl saleCommandService;

    private Sale sale;

    @Before
    public void setUp() {
        String dateString = "2022-01-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sale = new Sale("Venta", date, 100);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(saleRepository.findByName(anyString())).thenReturn(Optional.of(sale));
        Product firstProduct = new Product("Galleta", 5, 5, 0, 5, 10, 1L);
        Product secondProduct = new Product("Cereal", 10, 10, 0, 5, 10, 1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(firstProduct));
        when(productRepository.findById(2L)).thenReturn(Optional.of(secondProduct));
    }

    @Test
    public void testHandleCreateSaleCommand() {
        List<SaleRequestDto> saleRequestDtoList = fillSaleRequestDtoList();
        CreateSaleCommand command = new CreateSaleCommand(saleRequestDtoList);
        Long saleId = saleCommandService.handle(command);
        assertEquals(sale.getId(), saleId);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    private List<SaleRequestDto> fillSaleRequestDtoList() {
        List<SaleRequestDto> saleRequestDtoList = new ArrayList<>();
        SaleRequestDto firstSale = new SaleRequestDto();
        firstSale.setProductId(1L);
        firstSale.setQuantity(2);
        saleRequestDtoList.add(firstSale);
        SaleRequestDto secondSale = new SaleRequestDto();
        secondSale.setProductId(2L);
        secondSale.setQuantity(3);
        saleRequestDtoList.add(secondSale);
        return saleRequestDtoList;
    }

    @Test
    public void testHandleUpdateSaleCommand() {
        String dateString = "2022-01-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UpdateSaleCommand command = new UpdateSaleCommand(1L, "Venta", date, 100);
        Optional<Sale> updatedSale = saleCommandService.handle(command);
        assertEquals(sale.getName(), updatedSale.get().getName());
        verify(saleRepository, times(1)).findByName(anyString());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void testHandleDeleteSaleCommand() {
        DeleteSaleCommand command = new DeleteSaleCommand("Venta");
        Long deletedSaleId = saleCommandService.handle(command);
        assertEquals(sale.getId(), deletedSaleId);
        verify(saleRepository, times(1)).findByName(anyString());
        verify(saleRepository, times(1)).delete(any(Sale.class));
    }
}