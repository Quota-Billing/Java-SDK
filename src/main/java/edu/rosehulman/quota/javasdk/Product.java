package edu.rosehulman.quota.javasdk;

public class Product {

  private Partner partner;
  private String productId;

  Product(Partner partner, String productId) {
    this.partner = partner;
    this.productId = productId;
  }

  String getProductId() {
    return productId;
  }

  public boolean addUser(User user) {
    return QuotaClient.getInstance().addUser(partner, this, user);
  }

  public User getUserById(String userId) {
    return null; // TODO
  }
}
