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
      HttpResponse<String> response = Unirest.post(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).body("{\"id\":\"" + userId + "\"}").asString();
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
      HttpResponse<String> response = Unirest.delete(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", userId).asString();
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
      HttpResponse<String> response = Unirest.get(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", userId).asString();
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
      HttpResponse<String> response = Unirest.get(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}/quota/{quotaId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quotaId).asString();
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
      response = Unirest
          // POST with no body means increment by default amount
          .post(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}/quota/{quotaId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quota.getQuotaId()).asString();
    } catch (Exception e) {
      return OTHER_ERROR;
    }
    if (response.getStatus() == 200) {
      if (response.getBody().isEmpty()) {
        return SUCCESS;
      } else {
        JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();
        boolean isGrace = json.get("isGrace").getAsBoolean();
        if (isGrace) {
          return GRACE_PERIOD;
        }
        return SUCCESS;
      }
    } else if (response.getStatus() == 403) {
      if (response.getBody().isEmpty()) {
        IncrementQuotaStatus limit = LIMIT_REACHED_FAILURE;
        limit.setExtra(response.getBody());
        return limit;
      } else {
        try {
          JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();
          boolean tierNotSet = json.get("tierNotSet").getAsBoolean();
          if (tierNotSet) {
            return TIER_NOT_SET_ERROR;
          }
          IncrementQuotaStatus limit = LIMIT_REACHED_FAILURE;
          limit.setExtra(response.getBody());
          return limit;
        } catch (Exception e) {
          // catch when not json
          return LIMIT_REACHED_FAILURE;
        }
      }
    } else {
      return OTHER_ERROR;
    }
  }

  /**
   * @param partner
   * @param product
   * @param user
   * @param quota
   * @param count
   * @return true if Quota incremented successfully, false otherwise
   */
  protected IncrementQuotaStatus incrementQuota(Partner partner, Product product, User user, Quota quota, BigInteger count) {
    HttpResponse<String> response;
    try {
      JsonObject body = new JsonObject();
      body.addProperty("count", count.toString());
      response = Unirest.post(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}/quota/{quotaId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quota.getQuotaId()).body(body).asString();
    } catch (Exception e) {
      return OTHER_ERROR;
    }
    if (response.getStatus() == 200) {
      if (response.getBody().isEmpty()) {
        return SUCCESS;
      } else {
        JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();
        boolean isGrace = json.get("isGrace").getAsBoolean();
        if (isGrace) {
          return GRACE_PERIOD;
        }
        return SUCCESS;
      }
    } else if (response.getStatus() == 403) {
      if (response.getBody().isEmpty()) {
        IncrementQuotaStatus limit = LIMIT_REACHED_FAILURE;
        limit.setExtra(response.getBody());
        return limit;
      } else {
        JsonObject json = new JsonParser().parse(response.getBody()).getAsJsonObject();
        boolean tierNotSet = json.get("tierNotSet").getAsBoolean();
        if (tierNotSet) {
          return TIER_NOT_SET_ERROR;
        }
        IncrementQuotaStatus limit = LIMIT_REACHED_FAILURE;
        limit.setExtra(response.getBody());
        return limit;
      }
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
      response = Unirest.get(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}").routeParam("apiKey", apiKey).asString();
      if (response.getStatus() == 200) {
        return new Partner(apiKey);
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
   * @param tierId
   * @param rollover
   * @return true if UserTier successfully set, false otherwise
   */
  protected boolean setUserTier(Partner partner, Product product, User user, String quotaId, String tierId, boolean rollover) {
    HttpResponse<String> response;
    try {
      JsonObject body = new JsonObject();
      body.addProperty("rollover", rollover);
      response = Unirest.put(SystemConfig.getInstance().getQuotaUrl() + "/partnerApi/{apiKey}/product/{productId}/user/{userId}/quota/{quotaId}/tier/{tierId}").routeParam("apiKey", partner.getApiKey()).routeParam("productId", product.getProductId()).routeParam("userId", user.getUserId()).routeParam("quotaId", quotaId).routeParam("tierId", tierId).body(body.toString()).asString();
      return response.getStatus() == 200;
    } catch (Exception e) {
      return false;
    }
  }
}
