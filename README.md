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

<dependency>
  <dependency>
    <groupId>com.github.Quota-Billing</groupId>
      <artifactId>Java-SDK</artifactId>
      <version>e337259444a71fb97c8a27a61b5ddebc25e115f1</version>
    </dependency>
</dependency>
```

## Example Usage

As an example you can add a new user to a product to keep track of their quotas and billing.
```java
QuotaService quotaService = QuotaService.getReference("API_KEY");
Product product = quotaService.getProductById("PRODUCT_ID");  
product.addUser("USER_ID");
User user = product.getUser("USER_ID");
```
