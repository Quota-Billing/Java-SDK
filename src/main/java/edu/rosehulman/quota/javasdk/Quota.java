package edu.rosehulman.quota.javasdk;

public class Quota {

  private Partner partner;
  private Product product;
  private User user;
  private String quotaId;

  public Quota(Partner partner, Product product, User user, String quotaId) {
    this.partner = partner;
    this.product = product;
    this.user = user;
    this.quotaId = quotaId;
  }

  public IncrementQuotaStatus increment() {
    return QuotaClient.getInstance().incrementQuota(partner, product, user, this);
  }

  String getQuotaId() {
    return quotaId;
  }
}
