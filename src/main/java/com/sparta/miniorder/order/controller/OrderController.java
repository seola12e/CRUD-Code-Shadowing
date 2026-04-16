package com.sparta.miniorder.order.controller;

import com.sparta.miniorder.order.dto.OrderRequest;
import com.sparta.miniorder.order.dto.OrderResponse;
import com.sparta.miniorder.order.entity.Order;
import com.sparta.miniorder.order.repository.OrderRepository;
import com.sparta.miniorder.product.entity.Product;
import com.sparta.miniorder.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        /**
         * ProductRepository 에서 상품 ID를 통해 해당 상품이 데이터베이스에 존재하는지 찾음
         * 만약 없으면 IllegalArgumentException 을 throw 함
         */
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 상품이 존재하지 않습니다. id = " + request.getProductId()
                ));

        /**
         * 가져온 product로 Order 생성자를 통해 주문을 생성한 후
         * OrderRepository 를 통해 save 함
         * 이후 201 상태코드와 함께 OrderResponse DTO를 반환함
         */
        Order order = new Order(product);
        Order saved = orderRepository.save(order);
        OrderResponse response = new OrderResponse(saved);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getOrderId())).body(response);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        /**
         * OrderRepository에서 주문 ID를 통해 해당 주문이 데이터베이스에 존재하는지 찾음
         * 만약 없으면 IllegalArgumentException 을 throw 함
         */
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 주문이 존재하지 않습니다. id = " + id
                ));

        /**
         * 해당 주문(order)을 OrderResponse DTO로 감싸서 반환함
         */
        return ResponseEntity.ok(new OrderResponse(order));
    }
}
