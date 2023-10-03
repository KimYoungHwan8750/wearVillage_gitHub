package com.example.wearVillage.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletBalanceController {
  private int balance = 0; // 데이터베이스 쿼리로 유저 잔액 가져오기

  @GetMapping("/get-balance")
  public int getBalance() {
    return balance;
  }

  @PostMapping("/add-funds")
  public int addFunds(@RequestParam int amount) {
    // 충전 요청 처리
    balance += amount;
    return balance;
  }
}
