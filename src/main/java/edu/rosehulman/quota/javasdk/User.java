package edu.rosehulman.quota.javasdk;

public class User {

  private Partner partner;
  private Product product;
  private String userId;

  public User(Partner partner, Product product, String userId) {
    this.partner = partner;
    this.product = product;
    this.userId = userId;
  }

  protected String getUserId() {
    return userId;
  }

  protected Quota getQuota(String quotaId) {
    return QuotaClient.getInstance().getQuota(partner, product, this, quotaId);
  }
}
