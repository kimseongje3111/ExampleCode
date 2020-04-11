package jpabook.japshop.service;

import jpabook.japshop.domain.*;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.ItemRepository;
import jpabook.japshop.repository.MemberRepository;
import jpabook.japshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /*
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 회원, 상품 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // CASCADE.ALL 옵션 : orderItem, delivery 가 자동으로 persist (보통 private 한 owner 만 관리하는 자원일 경우)
        orderRepository.save(order);

        return order.getId();
    }

    /*
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    /*
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
