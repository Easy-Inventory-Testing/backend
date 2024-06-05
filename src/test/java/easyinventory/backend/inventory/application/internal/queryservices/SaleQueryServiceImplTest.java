package easyinventory.backend.inventory.application.internal.queryservices;

import easyinventory.backend.inventory.domain.model.aggregates.Sale;
import easyinventory.backend.inventory.domain.model.queries.GetSaleByIdQuery;
import easyinventory.backend.inventory.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaleQueryServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleQueryServiceImpl saleQueryService;

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
        when(saleRepository.findById(anyLong())).thenReturn(Optional.of(sale));
    }

    @Test
    public void testHandleGetSaleByIdQuery() {
        GetSaleByIdQuery query = new GetSaleByIdQuery(1L);
        Optional<Sale> foundSale = saleQueryService.handle(query);
        assertEquals(sale.getName(), foundSale.get().getName());
        verify(saleRepository, times(1)).findById(anyLong());
    }
}
