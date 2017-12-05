package edu.rosehulman.quota.javasdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.math.BigInteger;

import static edu.rosehulman.quota.javasdk.IncrementQuotaStatus.*;

class QuotaClient {

  private static QuotaClient instance;

  private QuotaClient() {
  }

  static synchronized QuotaClient getInstance() {
    if (instance == null) {
      instance = new QuotaClient();
    }
    return instance;
  }

  /**
   * @param partner
   * @param product
   * @param userId
   * @return true if user added successfully, false otherwise
   */
  protected boolean addUser(Partner partner, Product product, String userId) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest.post("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}").routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId()).routeParam("userId", userId).asString();
      return response.getStatus() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @param partner
   * @param product
   * @param userId
   * @return true if user removed successfully, false otherwise
   */
  protected boolean removeUser(Partner partner, Product product, String userId) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest.delete("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}").routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId()).routeParam("userId", userId).asString();
      return response.getStatus() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @param partner
   * @param product
   * @param userId
   * @return User if exists, null otherwise
   */
  protected User getUser(Partner partner, Product product, String userId) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest.get("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}").routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId()).routeParam("userId", userId).asString();
      if (response.getStatus() == 200) {
        return new User(partner, product, userId);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * @param partner
   * @param product
   * @param user
   * @param quotaId
   * @return Quota if exists, null otherwise
   */
  protected Quota getQuota(Partner partner, Product product, User user, String quotaId) {
    try {
      // TODO: Put Quota Server path in config and in here
      HttpResponse<String> response = Unirest.get("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}/quota/{quotaId}").routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quotaId).asString();
      if (response.getStatus() == 200) {
        JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();
        return new Quota(partner, product, user, quotaId, new BigInteger(json.get("max").getAsString()), new BigInteger(json.get("value").getAsString()));
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * @param partner
   * @param product
   * @param user
   * @param quota
   * @return true if Quota incremented successfully, false otherwise
   */
  protected IncrementQuotaStatus incrementQuota(Partner partner, Product product, User user, Quota quota) {
    HttpResponse<String> response;
    try {
      // TODO: Put Quota Server path in config and in here
      response = Unirest
          // POST with no body means increment by default amount
          .post("http://quota.csse.rose-hulman.edu:8080/partner/{partnerId}/product/{productId}/user/{userId}/quota/{quotaId}").routeParam("partnerId", partner.getPartnerId()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quota.getQuotaId()).asString();
    } catch (Exception e) {
      return OTHER_ERROR;
    }
    if (response.getStatus() == 200) {
      return SUCCESS;
    } else if (response.getStatus() == 403) {
      IncrementQuotaStatus limit = LIMIT_REACHED_FAILURE;
      limit.setExtra(response.getBody());
      return limit;
    } else {
      return OTHER_ERROR;
    }
  }

  /**
   * @param apiKey
   * @return Partner if exists, null otherwise
   */
  protected static Partner getPartner(String apiKey) {
    HttpResponse<String> response;
    try {
      // TODO: Put Quota Server path in config and in here
      response = Unirest.get("http://quota.csse.rose-hulman.edu:8080/partnerApi/{apiKey}").routeParam("apiKey", apiKey).asString();
      if (response.getStatus() == 200) {
        return new Partner(response.getBody());
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }
}
