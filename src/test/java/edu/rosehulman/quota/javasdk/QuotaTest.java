package edu.rosehulman.quota.javasdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static edu.rosehulman.quota.javasdk.IncrementQuotaStatus.LIMIT_REACHED_FAILURE;
import static edu.rosehulman.quota.javasdk.IncrementQuotaStatus.OTHER_ERROR;
import static edu.rosehulman.quota.javasdk.IncrementQuotaStatus.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({QuotaClient.class, Partner.class, Product.class, Quota.class})
public class QuotaTest {

  @Test
  public void incrementQuotaSuccessTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);
    Product product = Mockito.mock(Product.class);
    User user = Mockito.mock(User.class);

    // Create object we are testing
    Quota quota = new Quota(partner, product, user, "the_quota_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.incrementQuota(partner, product, user, quota)).thenReturn(SUCCESS);

    // Assert
    assertEquals(SUCCESS, quota.increment());
  }

  @Test
  public void incrementQuotaOtherErrorTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);
    Product product = Mockito.mock(Product.class);
    User user = Mockito.mock(User.class);

    // Create object we are testing
    Quota quota = new Quota(partner, product, user, "the_quota_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.incrementQuota(partner, product, user, quota)).thenReturn(OTHER_ERROR);

    // Assert
    assertEquals(OTHER_ERROR, quota.increment());
  }

  @Test
  public void incrementQuotaLimitReachedTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);
    Product product = Mockito.mock(Product.class);
    User user = Mockito.mock(User.class);

    // Create object we are testing
    Quota quota = new Quota(partner, product, user, "the_quota_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.incrementQuota(partner, product, user, quota)).thenReturn(LIMIT_REACHED_FAILURE);

    // Assert
    assertEquals(LIMIT_REACHED_FAILURE, quota.increment());
  }
}
