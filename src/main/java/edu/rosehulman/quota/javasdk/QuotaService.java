package edu.rosehulman.quota.javasdk;

public class QuotaService {

  private Partner partner;

  private QuotaService(Partner partner) {
    this.partner = partner;
  }

  public static QuotaService getReference(String apiKey) {
    return new QuotaService(Partner.fromApiKey(apiKey));
  }

  public Product getProductById(String productId) {
    return new Product(partner, productId);
  }
}
