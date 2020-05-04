package jpabook.jpashop.domain.valuetype;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    @Column(length = 20)
    private String city;

    @Column(length = 30)
    private String street;

    @Column(length = 10)
    private String zipcode;

    /*
        * 값 타입
        * 의미있는 비지니스 메서드를 사용할 수 있다.
        * Validation 적용
     */

    public String fullAddress() {
        return getCity() + " " + getStreet() + " " + getZipcode();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setCity(String city) {
        this.city = city;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /*
        * Side Effect 를 방지하는 불변 객체임을 보장하기 위해
        * 불변 객체로 만들고 equals 와 hashCode 재정의
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return getCity().equals(address.getCity()) &&
                getStreet().equals(address.getStreet()) &&
                getZipcode().equals(address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
