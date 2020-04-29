package jpabook.japshop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrdersQueryDto() {
        List<OrderQueryDto> result = findOrders();

        // 컬렉션
        result.forEach(orderQueryDto -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
            orderQueryDto.setOrderItems(orderItems);
        });

        return result;
    }

    public List<OrderQueryDto> findAllByDtoOptimization() {
        // 루트 쿼리 (주문 조회, xToOne)
        List<OrderQueryDto> result = findOrders();

        // Id 리스트와 IN 절을 사용하여 쿼리 1번으로 컬렉션 조회 (주문 상품 리스트)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemsByOptimization(toOrderIds(result));

        // Map -> 컬렉션 매핑
        result.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId())));

        return result;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemsByOptimization(List<Long> orderIds) {     // 한번에 조회
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new jpabook.japshop.repository.order.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));       // List -> Map (groupBy 주문 Id)
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {      // 별도의 메서드로 따로 조회
        return em.createQuery(
                "select new jpabook.japshop.repository.order.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.japshop.repository.order.query.OrderQueryDto" +
                        "(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d"
                , OrderQueryDto.class).getResultList();
    }

    public List<OrderFlatDto> findAllByDtoFlat() {
        return em.createQuery(
                "select new jpabook.japshop.repository.order.query.OrderFlatDto" +
                        "(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i"
                , OrderFlatDto.class)
                .getResultList();
    }
}
