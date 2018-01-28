package edu.rosehulman.quota.javasdk;

import java.math.BigInteger;

public class Quota {

  private Partner partner;
  private Product product;
  private User user;
  private String quotaId;
  private BigInteger max;
  private BigInteger value;

  protected Quota(Partner partner, Product product, User user, String quotaId, BigInteger max, BigInteger value) {
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

  public IncrementQuotaStatus increment(BigInteger count) {
    return QuotaClient.getInstance().incrementQuota(partner, product, user, this, count);
  }

  public boolean setTier(String tierId) {
    return QuotaClient.getInstance().setUserTier(partner, product, user, quotaId, tierId, false);
  }

  public boolean setTier(String tierId, boolean rollover) {
    return QuotaClient.getInstance().setUserTier(partner, product, user, quotaId, tierId, rollover);
  }

  public String getQuotaId() {
    return quotaId;
  }

  public BigInteger getMax() {
    return this.max;
  }

  public BigInteger getValue() {
    return this.value;
  }
}
