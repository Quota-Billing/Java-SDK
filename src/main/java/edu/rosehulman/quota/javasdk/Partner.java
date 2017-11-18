package edu.rosehulman.quota.javasdk;

class Partner {

  private String partnerId;

  Partner(String partnerId) {
    this.partnerId = partnerId;
  }

  static Partner fromApiKey(String apiKey) {
    return QuotaClient.getPartner(apiKey);
  }

  String getPartnerId() {
    return partnerId;
  }
}
