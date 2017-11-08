[![Build Status](https://travis-ci.org/Quota-Billing/Java-SDK.svg?branch=master)](https://travis-ci.org/Quota-Billing/Java-SDK)

# Quota/Billing Java SDK

To start using our service visit [our quota-billing service](https://url_to_quota_billing_README.com) to generate an API key and upload your configuration file. Then add to your pom.xml or build.gradle file:

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
      <version>0.0.3</version>
    </dependency>
</dependencies>
```

## Example Usage
### Add a User to a Product
```java
QuotaService quotaService = QuotaService.getReference("API_KEY");
Product product = quotaService.getProductById("PRODUCT_ID");
product.addUser("USER_ID");
User user = product.getUser("USER_ID");
```
### Remove a User from a Product
```java
QuotaService quotaService = QuotaService.getReference("API_KEY");
Product product = quotaService.getProductById("PRODUCT_ID");
product.removeUser("USER_ID");
```
### Increment a User's Quota
```java
QuotaService quotaService = QuotaService.getReference("API_KEY");
Product product = quotaService.getProductById("PRODUCT_ID");
User user = product.getUser("USER_ID");
Quota quota = user.getQuota("QUOTA_ID");
IncrementQuotaStatus status = quota.increment();
boolean success = (status == SUCCESS);
```

