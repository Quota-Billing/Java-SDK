package edu.rosehulman.quota.javasdk;

class Partner {

  private String partnerId;

  Partner(String apiKey) {
    Partner p = QuotaClient.getPartner(apiKey);
    this.partnerId = p.getPartnerId();
  }

  Partner(String apiKey, String partnerId) {
    this.partnerId = partnerId;
  }

  String getPartnerId() {
    return partnerId;
  }
}
