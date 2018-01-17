package edu.rosehulman.quota.javasdk;

public class Product {

  private Partner partner;
  private String productId;

  Product(Partner partner, String productId) {
    this.partner = partner;
    this.productId = productId;
  }

  public String getProductId() {
    return productId;
  }

  public boolean addUser(String userId) {
    return QuotaClient.getInstance().addUser(partner, this, userId);
  }

  public boolean removeUser(String userId) {
    return QuotaClient.getInstance().removeUser(partner, this, userId);
  }

  public User getUser(String userId) {
    return QuotaClient.getInstance().getUser(partner, this, userId);
  }
}
