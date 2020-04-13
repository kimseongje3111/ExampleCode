package jpabook.japshop.domain.item;

import jpabook.japshop.domain.Category;
import jpabook.japshop.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // == 빌더 == //
    abstract static class Builder<T extends Builder<T>> {
        private final String name;
        private final int price;
        private final int stockQuantity;

        Builder(String name, int price, int stockQuantity) {
            this.name = name;
            this.price = price;
            this.stockQuantity = stockQuantity;
        }

        abstract Item build();
    }

    Item(Builder<?> builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
    }

    // == 비지니스 로직 == //
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) throw new NotEnoughStockException("재고가 더 필요합니다.");

        this.stockQuantity -= quantity;
    }
}
