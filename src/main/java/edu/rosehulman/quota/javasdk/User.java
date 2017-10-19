package edu.rosehulman.quota.javasdk;

public class User {

  private String partnerId;
  private String productId;
  private String userId;

  public User(String partnerId, String productId, String userId) {
    this.partnerId = partnerId;
    this.productId = productId;
    this.userId = userId;
  }
}
