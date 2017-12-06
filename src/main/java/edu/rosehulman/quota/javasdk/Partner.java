package edu.rosehulman.quota.javasdk;

public class Partner {

  private String apiKey;

  public Partner(String apiKey) {
    this.apiKey = apiKey;
  }

  protected static Partner fromApiKey(String apiKey) {
    return QuotaClient.getPartner(apiKey);
  }

  public String getApiKey() {
    return this.apiKey;
  }
}
