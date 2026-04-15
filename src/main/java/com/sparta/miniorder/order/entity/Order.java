package com.sparta.miniorder.order.entity;

import com.sparta.miniorder.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문은 상품 1개만 참조 (요구사항)
    // 상품 이름을 복사해서 저장하지 않고, 연관관계로 연결 -> 상품 이름 변경 시 주문 조회에도 반영됨
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order(Product product) {
        this.product = product;
    }
}
