package easyinventory.backend.inventory.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private Date saleDate ;
    private Integer totalCost ;
    @ManyToMany
    private List<Product> products = new ArrayList<>();

    public Sale(String name, Date saleDate, Integer totalCost) {
        this.name = name;
        this.saleDate = saleDate;
        this.totalCost = totalCost;
    }

    //obtener cantidad de productos
    public Integer getProductsCount(){
        return this.products.size();
    }

    //agregar producto a la venta
    public void addProducts(List<Product> product){
        this.products.addAll(product);
    }


    //eliminar producto de la venta
    public void removeProduct(Product product){
        this.products.remove(product);
    }

    //calcular el total de la venta
    public Integer calculateTotalCost(){
        this.totalCost = 0;
        for(Product product : this.products){
            this.totalCost += product.getRealPrice();
        }
        return this.totalCost;
    }

    public Sale() {}
}
