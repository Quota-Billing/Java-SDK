[![Build Status](https://travis-ci.org/Quota-Billing/Java-SDK.svg?branch=master)](https://travis-ci.org/Quota-Billing/Java-SDK)

# Quota/Billing Java SDK

## Quickstart

### Register
Visit our [registration page](http://quota.csse.rose-hulman.edu:8084/upload) to register yourself with our service. You will find an example configuration file below. An API Key will be generated for you. Save this Key.

#### sampleConfig.json
```json
{
  "name" : "thisIsThePartnerName123",
  "password" : "workaroundPasswordForNow",
  "products" : [
    {
      "id" : "theProductId",
      "name" : "theProductName",
      "quotas" : [
        {
          "id" : "theQuotaId",
          "name" : "theQuotaName",
          "type" : "numerical",
          "tiers" : [
            {
              "id" : "theTierId",
              "name" : "theTierName",
              "max" : "5",
              "price" : "10"
            }
          ]
        }
      ]
    }
  ]
}
```

### Maven Setup
Add the following to your pom.xml to download our dependencies

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.Quota-Billing</groupId>
      <artifactId>Java-SDK</artifactId>
      <version>0.0.7</version>
    </dependency>
</dependencies>
```

### Use the service

```java
// QuotaService exposes your products configured in the configuration json you have uploaded
QuotaService quotaService = QuotaService.getReference("4721d305-ebe1-465b-9828-ea5b7533eabf");

// Get a product by id
Product product = quotaService.getProductById("theProductId");

// Add a new user to a product
// Returns true if user is successfully added
product.addUser("thisIsAUserId");

// Get a reference to the newly added user
User user = product.getUser("thisIsAUserId");

// Get a quota assigned to the user
// Quotas are configured in the configuration json
Quota quota = user.getQuota("theQuotaId");

// The limit was set to 5 in the configuration json
System.out.println(quota.increment()); // SUCCESS
System.out.println(quota.increment()); // SUCCESS
System.out.println(quota.increment()); // SUCCESS
System.out.println(quota.increment()); // SUCCESS
System.out.println(quota.increment()); // SUCCESS

// The sixth with cause an error
IncrementQuotaStatus incrementQuotaStatusFailure = quota.increment();
System.out.println(incrementQuotaStatusFailure); // LIMIT_REACHED_FAILURE

// Billing info is provided in this temporary manner
System.out.println(incrementQuotaStatusFailure.getExtra()); // Contains billing info
```
