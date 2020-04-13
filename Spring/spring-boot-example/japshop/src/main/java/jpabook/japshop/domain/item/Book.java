package jpabook.japshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    public static class BookBuilder extends Item.Builder<BookBuilder> {
        private String author;
        private String isbn;

        public BookBuilder(String name, int price, int stockQuantity) {
            super(name, price, stockQuantity);
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        @Override
        public Book build() {
            return new Book(this);
        }
    }

    private Book(BookBuilder bookBuilder) {
        super(bookBuilder);
        this.author = bookBuilder.author;
        this.author = bookBuilder.isbn;
    }
}
