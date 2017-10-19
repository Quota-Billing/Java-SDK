package edu.rosehulman.quota.javasdk;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

class QuotaClient {

  private static QuotaClient instance;

  private QuotaClient() {
  }

  /**
   * 
   * @param partner
   * @param product
   * @param userId
   * @return true if user added successfully, false otherwise
   */
  boolean addUser(Partner partner, Product product, String userId) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest
          .post("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}")
          .routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId())
          .routeParam("userId", userId).asString();
      return response.getStatus() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 
   * @param partner
   * @param product
   * @param userId
   * @return User if exists, null otherwise
   */
  User getUser(Partner partner, Product product, String userId) {
    try {
      HttpResponse<String> response = Unirest
          .get("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}")
          .routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId())
          .routeParam("userId", userId).asString();
      if (response.getStatus() == 200) {
        return new User(partner.getPartnerId(), product.getProductId(), userId);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  static synchronized QuotaClient getInstance() {
    if (instance == null) {
      instance = new QuotaClient();
    }
    return instance;
  }
}
