package jpabook.japshop.api;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderStatus;
import jpabook.japshop.repository.OrderRepository;
import jpabook.japshop.repository.OrderSearch;
import jpabook.japshop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.japshop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Embedded;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 주문 조회 V1 (엔티티 직접 노출)
     * < 문제점 >
     * 이전 엔티티 직접 노출의 문제
     * 양방향 관계 문제 발생 (무한 루프) -> 한쪽에 @JsonIgnore 가 필요
     * 지연 로딩으로 생성된 프록시 객체 처리를 위해 Hibernate5Module 모듈을 등록하거나 직접 LAZY 초기화를 통해 강제 지연 로딩이 필요하다
     * (jackson 라이브러리는 기본적으로 프록시 객체 처리를 하지 않음)
     */
    @GetMapping(value = "/api/v1/simple-orders")
    public List<Order> ordersV1() {
        return orderRepository.findAllByString(new OrderSearch());
    }

    /**
     * 주문 조회 V2 (엔티티를 조회해서 DTO 변환)
     * < 단점 >
     * N + 1 문제 : 지연 로딩으로 쿼리 N 번 호출 (모든 엔티티 DTO 변환마다 쿼리 호출)
     * N개의 주문 -> Member, Delivery 최악의 경우 -> 1(처음 쿼리) + N(회원) + N(배송)
     */
    @GetMapping(value = "/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    /**
     * 주문 조회 V3 (페치 조인으로 최적화)
     * 페치 조인을 사용해서 쿼리 1번으로 해결
     */
    @GetMapping(value = "/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    /**
     * 주문 조회 V4 (JPA 에서 DTO 로 바로 조회)
     * 쿼리 1번 호출
     * select 절에서 원하는 데이터만 선택해서 조회
     * Repository 재사용성이 떨어짐, 특정 화면에 종속적
     * 필요하다면 Repository 패키지 하위에 최적화 쿼리용 패키지를 따로 만들자
     */
    @GetMapping(value = "/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrdersDto();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;

        @Embedded
        private Address address;

        SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
