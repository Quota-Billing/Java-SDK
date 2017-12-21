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

  protected IncrementQuotaStatus increment() {
    return QuotaClient.getInstance().incrementQuota(partner, product, user, this);
  }

  protected IncrementQuotaStatus increment(BigInteger count) {
    return QuotaClient.getInstance().incrementQuota(partner, product, user, this, count);
  }
  
  protected boolean setUserTier(String tierId) {
    return QuotaClient.getInstance().setUserTier(partner, product, user, quotaId, tierId, false);
  }

  protected boolean setUserTier(String tierId, boolean rollover) {
    return QuotaClient.getInstance().setUserTier(partner, product, user, quotaId, tierId, rollover);
  }

  protected String getQuotaId() {
    return quotaId;
  }
  
  protected BigInteger getMax() {
    return this.max;
  }
  
  protected BigInteger getValue() {
    return this.value;
  }
}
