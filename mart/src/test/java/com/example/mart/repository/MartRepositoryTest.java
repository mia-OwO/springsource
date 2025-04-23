package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Test
    public void testMemberInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .city("서울" + i)
                    .street("724-11" + i)
                    .zipcode("1650" + i)
                    .build();
            memberRepository.save(member);
        });

    }

    @Test
    public void testItemInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .naem("티셔츠" + i)
                    .price(i * 20000)
                    .stockQuantity(i * 5)

                    .build();
            itemRepository.save(item);
        });
    }

    // 주문하다 : Order + OrderItem insert
    @Test
    public void testOrderInsert() {

        // 주문에 대한것만
        Order order = Order.builder()
                .member(Member.builder().id(9L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        // 주문과 관련된 상품은 ORDERITEM 테이블에 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(6L).get())
                .order(order)
                .orderPrice(39000)
                .count(1)
                .build();
        orderItemRepository.save(orderItem);

        orderItem = OrderItem.builder()
                .item(itemRepository.findById(6L).get())
                .order(order)
                .orderPrice(39000)
                .count(1)
                .build();
        orderItemRepository.save(orderItem);

    }

    // 조회
    @Transactional
    @Test
    public void testRead1() {
        // 주문 조회(주문번호 이용)
        Order order = orderRepository.findById(3L).get();
        System.out.println(order);

        // 주문자 정보 조회
        System.out.println(order.getMember());
    }

    @Transactional
    @Test
    public void testRead2() {
        // 특정 회원의 주문 내역 전체 조회
        Member member = memberRepository.findById(9L).get();
        System.out.println(member.getOrders());
    }

    @Transactional
    @Test
    public void testRead3() {
        // 주문상품의 정보 조회
        OrderItem orderItem = orderItemRepository.findById(1L).get();
        System.out.println(orderItem);

        // 주문 상품의 상품명 조회
        System.out.println(orderItem.getItem().getNaem());

        // 주문상품을 주문한 고객 정보 조회

        Member member = orderItem.getOrder().getMember();
        System.out.println(member);

    }

    @Transactional
    @Test
    public void testRead4() {
        Order order = orderRepository.findById(5L).get();
        System.out.println(order);

        // 주문을 통해 주문 아이템 조회
        order.getOrderItems().forEach(item -> System.out.println(item));

    }

    // 삭제
    @Test
    public void testDelete1() {
        // memberRepository.deleteById(12L);

        // member id로 주문 찾아오기 -> 메소드 생성(findById는 orders만)

        // 주문상품 취소 :
        // 주문 취소
        // 멤버 제거

        memberRepository.deleteById(9L);

    }

    @Test
    public void testDelete2() {
        // 주문아이템 제거(자식)
        orderItemRepository.deleteById(2L);

        // 주문제거(부모)
        orderRepository.deleteById(4L);

    }

    @Test
    public void testDelete3() {
        // 부모쪽에 cascade작성
        // 주문제거(주문상품 같이 제거)
        orderRepository.deleteById(5L);

    }

    @Commit
    @Transactional
    @Test
    public void testDelete4() {
        Order order = orderRepository.findById(6L).get();
        // 현재 주문과 연결된 주문 상품 조회
        System.out.println(order.getOrderItems());

        // 첫번쨰 자식 제거
        order.getOrderItems().remove(0);
        // 첫번쨰 자식 제거 orphanRemoval = true + commit + CascadeType.PERSIST

        orderRepository.save(order);
    }

    @Test
    public void testOrderInsert2() {
        // order 저장시 OrderItem같이 저장
        // cascade : Cascade.PERSIST설정
        // 주문에 대한것만
        Order order = Order.builder()
                .member(Member.builder().id(9L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        // 주문과 관련된 상품은 ORDERITEM 테이블에 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(6L).get())
                .order(order)
                .orderPrice(25000)
                .count(1)
                .build();
        // orderItemRepository.save(orderItem);
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

    }

    @Test
    // 배송정보 입력
    public void testDeliveryInsert() {
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        deliveryRepository.save(delivery);

        // 주문과 연결
        Order order = orderRepository.findById(6L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Transactional
    @Test
    // 배송정보 입력
    public void testDeliveryRead() {
        // 배송 조회
        System.out.println(deliveryRepository.findById(1L));
        // 배송과 관련있는 주문 조회(x -> 양방향 안 열어서)
        // 주문과 관련있는 배송은 조회 가능

        Order order = orderRepository.findById(6L).get();
        System.out.println(order.getDelivery().getDeliveryStatus());

    }

    // 배송과 관련있는 주문 조회 => 양방향 열어서
    @Transactional
    @Test
    // 배송정보 입력
    public void testDeliveryRead2() {
        // 배송 조회
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println("주문 조회" + delivery.getOrder());
        System.out.println("주문자 조회" + delivery.getOrder().getMember());
        System.out.println("주문 아이템" + delivery.getOrder().getOrderItems());

    }

    @Test
    // 배송정보 입력 -> 새로 저장 -> persist
    public void testDeliveryInsert2() {
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        // deliveryRepository.save(delivery); cascade = CascadeType.PERSIST

        // 주문과 연결 -> 수정 -> merge
        Order order = orderRepository.findById(7L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void deleteTest() {
        // order 지우면서 배송정보 제거, 주문상품 제거
        orderRepository.deleteById(5L);

    }

    @Test
    public void testCategoryItemInsert1() {

        // 카테고리 입력

        Category category1 = Category.builder()
                .name("가전제품")
                .build();

        Category category2 = Category.builder()
                .name("식품")
                .build();

        Category category3 = Category.builder()
                .name("생활용품")
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // 아템 입력
        Item item1 = Item.builder().naem("TV").price(2500000).stockQuantity(15).build();

        itemRepository.save(item1);

        CategoryItem categoryItem = CategoryItem.builder().category(category1).item(item1).build();
        categoryItemRepository.save(categoryItem);

        item1 = Item.builder().naem("콩나물").price(1200).stockQuantity(5).build();
        itemRepository.save(item1);

        categoryItem = CategoryItem.builder().category(category2).item(item1).build();
        categoryItemRepository.save(categoryItem);

        item1 = Item.builder().naem("샴푸").price(1200).stockQuantity(7).build();
        itemRepository.save(item1);

        categoryItem = CategoryItem.builder().category(category3).item(item1).build();
        categoryItemRepository.save(categoryItem);
    }

    @Transactional
    @Test
    public void readCateItem() {
        // categoryItem => Category, categoryItem => Item

        CategoryItem categoryItem = categoryItemRepository.findById(1L).get();

        System.out.println(categoryItem);
        System.out.println(categoryItem.getCategory().getName());
        System.out.println(categoryItem.getItem().getNaem());

        Category category = categoryRepository.findById(1L).get();
        category.getCategoryItems().forEach(item -> System.out.println(item.getItem()));
    }
}
