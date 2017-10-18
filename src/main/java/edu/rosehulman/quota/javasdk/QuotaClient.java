package edu.rosehulman.quota.javasdk;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

class QuotaClient {

  private static QuotaClient instance;

  private QuotaClient() {
  }

  boolean addUser(Partner partner, Product product, User user) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest.post("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/addUser/{userId}")
          .routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId())
          .routeParam("userId", user.getUserId()).asString();
      return response.getStatus() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  static synchronized QuotaClient getInstance() {
    if (instance == null) {
      instance = new QuotaClient();
    }
    return instance;
  }
}
