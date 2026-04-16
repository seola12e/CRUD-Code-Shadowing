package com.sparta.miniorder.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "주문하기 위해서는 상품 ID가 필요합니다.")
    private Long productId;
}
