package edu.rosehulman.quota.javasdk;

class Partner {

  private String partnerId;

  Partner(String partnerId) {
    this.partnerId = partnerId;
  }

  protected static Partner fromApiKey(String apiKey) {
    return QuotaClient.getPartner(apiKey);
  }

  protected String getPartnerId() {
    return partnerId;
  }
}
