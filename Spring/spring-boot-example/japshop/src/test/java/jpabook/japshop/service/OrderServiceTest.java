package jpabook.japshop.service;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderStatus;
import jpabook.japshop.domain.item.Book;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.exception.NotEnoughStockException;
import jpabook.japshop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember("kim", createAddress("Seoul", "river", "123-123"));
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER 이다.", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품들의 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("총 주문 가격은 (가격 * 수량) 이다.", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량_초과() throws Exception {
        // Given
        Member member = createMember("kim", createAddress("Seoul", "river", "123-123"));
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 11;

        // When
        orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        fail("해당 상품의 재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        // Given
        Member member = createMember("kim", createAddress("Seoul", "river", "123-123"));
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소시 재고 수량이 복구되야 한다.", 10, item.getStockQuantity());
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);

        em.persist(member);

        return member;
    }

    private Address createAddress(String city, String street, String zipCode) {
        return new Address(city, street, zipCode);
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book.BookBuilder(name, price, stockQuantity).build();
        em.persist(book);

        return book;
    }
}