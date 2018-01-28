package edu.rosehulman.quota.javasdk;

public enum IncrementQuotaStatus {
  SUCCESS(""),
  GRACE_PERIOD(""),
  LIMIT_REACHED_FAILURE(""),
  TIER_NOT_SET_ERROR(""),
  OTHER_ERROR("");

  private String extra;

  private IncrementQuotaStatus(String extra) {
    this.extra = extra;
  }

  public String getExtra() {
    return extra;
  }

  void setExtra(String extra) {
    this.extra = extra;
  }
}
