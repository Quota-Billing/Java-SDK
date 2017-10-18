package edu.rosehulman.quota.javasdk;

class Partner {

  private String partnerId;

  Partner(String apiKey) {
    // TODO: Make a call to Quota server to get a Partner for the apiKey and set the
    // fields in this class
    partnerId = "partnerId";
  }

  String getPartnerId() {
    return partnerId;
  }
}
