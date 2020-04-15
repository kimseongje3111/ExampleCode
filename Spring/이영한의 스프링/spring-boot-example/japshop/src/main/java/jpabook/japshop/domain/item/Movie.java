package jpabook.japshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item {

    private String director;
    private String actor;

    public static class MovieBuilder extends Item.Builder<MovieBuilder> {
        private String director;
        private String actor;

        public MovieBuilder(String name, int price, int stockQuantity) {
            super(name, price, stockQuantity);
        }

        public MovieBuilder director(String director) {
            this.director = director;
            return this;
        }

        public MovieBuilder actor(String actor) {
            this.actor = actor;
            return this;
        }

        @Override
        public Movie build() {
            return new Movie(this);
        }
    }

    private Movie(MovieBuilder movieBuilder) {
        super(movieBuilder);
        this.director = movieBuilder.director;
        this.actor = movieBuilder.actor;
    }
}
