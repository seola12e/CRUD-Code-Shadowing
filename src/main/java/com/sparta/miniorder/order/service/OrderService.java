package com.sparta.miniorder.order.service;

import com.sparta.miniorder.order.dto.OrderRequest;
import com.sparta.miniorder.order.dto.OrderResponse;
import com.sparta.miniorder.order.entity.Order;
import com.sparta.miniorder.order.repository.OrderRepository;
import com.sparta.miniorder.product.entity.Product;
import com.sparta.miniorder.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(@Valid OrderRequest request) {
        /**
         * findProductById()를 통해 해당 product가 DB에 존재하는지 확인
         */
        Product product = findProductById(request.getProductId());

        /**
         * 가져온 product로 Order 생성자를 통해 주문을 생성한 후
         * OrderRepository 를 통해 save 함
         * 이후 OrderResponse DTO를 반환함
         */
        Order order = new Order(product);
        return new OrderResponse(orderRepository.save(order));
    }

    // @Transactional(readOnly = true) 서비스단에 있으므로 생략
    public OrderResponse getOrder(Long id) {
        /**
         * findOrderById()를 통해 해당 order가 DB에 존재하는지 확인 후 반환
         */
        return new OrderResponse(findOrderById(id));
    }

    /**
     * - 해당 Order가 DB에 존재하는지 확인하기 위해 findOrderById() 메서드로 분리
     * - 해당 Product가 DB에 존재하는지 확인하기 위해 findProductById() 메서드로 분리
     */
    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 주문이 존재하지 않습니다. id = " + id
                ));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 상품이 존재하지 않습니다. id=" + id
                ));
    }
}
