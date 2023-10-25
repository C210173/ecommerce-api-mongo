package com.sky.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String cardNumber; // Số thẻ thanh toán (có thể lưu ẩn hoặc mã hóa)
    private String cardHolderName; // Tên chủ thẻ
    private LocalDate expirationDate; // Ngày hết hạn thẻ
    private String cvv; // Mã CVV/CVC (có thể lưu ẩn hoặc mã hóa)
}
