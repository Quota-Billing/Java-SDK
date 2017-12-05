package edu.rosehulman.quota.javasdk;

public class Product {

  private Partner partner;
  private String productId;

  Product(Partner partner, String productId) {
    this.partner = partner;
    this.productId = productId;
  }

  protected String getProductId() {
    return productId;
  }

  protected boolean addUser(String userId) {
    return QuotaClient.getInstance().addUser(partner, this, userId);
  }

  protected boolean removeUser(String userId) {
    return QuotaClient.getInstance().removeUser(partner, this, userId);
  }

  protected User getUser(String userId) {
    return QuotaClient.getInstance().getUser(partner, this, userId);
  }
}
