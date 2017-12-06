package edu.rosehulman.quota.javasdk;

class Partner {

  private String apiKey;

  Partner(String apiKey) {
    this.apiKey = apiKey;
  }

  protected static Partner fromApiKey(String apiKey) {
    return QuotaClient.getPartner(apiKey);
  }

  String getApiKey() {
    return this.apiKey;
  }
}
