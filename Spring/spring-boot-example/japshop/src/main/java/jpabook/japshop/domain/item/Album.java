package jpabook.japshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item {

    private String artist;
    private String etc;

    public static class AlbumBuilder extends Item.Builder<AlbumBuilder> {
        private String artist;
        private String etc;

        public AlbumBuilder(String name, int price, int stockQuantity) {
            super(name, price, stockQuantity);
        }

        public AlbumBuilder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public AlbumBuilder etc(String etc) {
            this.etc = etc;
            return this;
        }

        @Override
        public Album build() {
            return new Album(this);
        }
    }

    private Album(AlbumBuilder albumBuilder) {
        super(albumBuilder);
        this.artist = albumBuilder.artist;
        this.etc = albumBuilder.etc;
    }
}
