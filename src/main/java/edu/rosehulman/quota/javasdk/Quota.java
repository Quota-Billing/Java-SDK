package edu.rosehulman.quota.javasdk;

import java.math.BigInteger;

public class Quota {

  private Partner partner;
  private Product product;
  private User user;
  private String quotaId;
  private BigInteger max;
  private BigInteger value;
 

  Quota(Partner partner, Product product, User user, String quotaId, BigInteger max, BigInteger value) {
    this.partner = partner;
    this.product = product;
    this.user = user;
    this.quotaId = quotaId;
    this.max = max;
    this.value = value;
  }

  public IncrementQuotaStatus increment() {
    return QuotaClient.getInstance().incrementQuota(partner, product, user, this);
  }

  String getQuotaId() {
    return quotaId;
  }
  
  BigInteger getMax() {
    return this.max;
  }
  
  BigInteger getValue() {
    return this.value;
  }
}
