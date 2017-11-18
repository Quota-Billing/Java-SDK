package edu.rosehulman.quota.javasdk;

public enum IncrementQuotaStatus {
  SUCCESS(""),
  LIMIT_REACHED_FAILURE(""),
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
