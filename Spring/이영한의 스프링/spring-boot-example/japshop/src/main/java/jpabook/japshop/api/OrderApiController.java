package jpabook.japshop.api;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderItem;
import jpabook.japshop.domain.OrderStatus;
import jpabook.japshop.repository.OrderRepository;
import jpabook.japshop.repository.OrderSearch;
import jpabook.japshop.repository.order.query.OrderFlatDto;
import jpabook.japshop.repository.order.query.OrderItemQueryDto;
import jpabook.japshop.repository.order.query.OrderQueryDto;
import jpabook.japshop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Embedded;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * 컬렉션 조회 최적화
 * OneToMany 추가
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * 주문 조회 V1 (엔티티 직접 노출)
     * 앞서 말한 문제점들 존재
     */
    @GetMapping(value = "/api/v1/orders")
    public List<Order> ordersV1() {
        return orderRepository.findAllByString(new OrderSearch());
    }

    /**
     * 주문 조회 V2 (엔티티 DTO 변환)
     * 지연 로딩으로 인한 너무 많은 쿼리 수
     */
    @GetMapping(value = "/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * 주문 조회 V3 (페치 조인으로 최적화)
     * 쿼리 1번
     * distinct 로 중복 row 제거, 애플리케이션에서 중복된 엔티티(참조가 같은) 식별
     * < 한계 >
     * 페이징 불가 : DB 입장에서는 뻥튀기된 조인 테이블이 그대로
     * ex. Order(One) 를 기준으로 페이징하고 싶지만 OrderItem(Many) 이 기준이 되버림
     * DB 에서 애플리케이션으로 전송하는 데이터 전송량 증가
     */
    @GetMapping(value = "/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * 주문 조회 V3.1 (페이징 한계 돌파)
     * 1. 먼저 xToOne 관계는 페치 조인
     * 2. 컬렉션은 지연 로딩으로 조회
     * 3. hibernate.default_batch_fetch_size(글로벌), @BatchSize(개별) 설정
     * 컬렉션이나 프록시 객체를 한꺼번에 설정한 size 만큼 IN 쿼리로 조회, 정규화된 테이블
     * 쿼리 수 : 1(Order) + 1(OrderItems) + 1(item)
     * DB 에서 애플리케이션으로 전송하는 데이터 전송량 감소
     */
    @GetMapping(value = "/api/v3.1/orders")
    public List<OrderDto> ordersV3_paging(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDeliveryByPaging(offset, limit);

        return orders.stream().map(OrderDto::new).collect(toList());
    }

    /**
     * 주문 조회 V4 (JPA 에서 DTO 직접 조회)
     * xToOne(N:1, 1:1) 관계들을 먼저 조회하고, oneToMany(1:N) 관계는 각각 별도로 처리
     * DTO 를 통해 원하는 데이터만 select 할 수 있지만 결국 컬렉션을 처리하는 쿼리가 N 번
     */
    @GetMapping(value = "/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrdersQueryDto();
    }

    /**
     * 주문 조회 V5 (V4 의 컬렉션 조회 최적화)
     * IN 절을 추가하여 쿼리 1번으로 조회
     * 쿼리 수 : 1(Order) + 1(OrderItems)
     * Map 을 사용해서 매칭 성능 향상 : O(1)
     */
    @GetMapping(value = "/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDtoOptimization();
    }

    /**
     * 주문 조회 V6 (JPA 에서 DTO 직접 조회, 플랫 데이터 최적화)
     * 단 1 번의 쿼리
     * 그러나 애플리케이션에서 추가 작업이 크다
     * 조인으로 인한 중복 데이터, 페이징 불가
     */
    @GetMapping(value = "/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flatData = orderQueryRepository.findAllByDtoFlat();

        return flatData.stream().collect(
                groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())))
                .entrySet()
                .stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;

        @Embedded
        private Address address;

        private List<OrderItemDto> orderItems;      // DTO 는 엔티티와 의존관계를 완전히 끊어야 함

        OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
