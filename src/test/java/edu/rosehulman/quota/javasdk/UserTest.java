package edu.rosehulman.quota.javasdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({QuotaClient.class, Partner.class, Product.class, Quota.class})
public class UserTest {

  @Test
  public void getQuotaTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);
    Product product = Mockito.mock(Product.class);
    Quota quota = Mockito.mock(Quota.class);

    // Create object we are testing
    User user = new User(partner, product, "the_user_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.getQuota(partner, product, user, "the_quota_id")).thenReturn(quota);

    // Assert
    assertEquals(quota, user.getQuota("the_quota_id"));
  }
}
