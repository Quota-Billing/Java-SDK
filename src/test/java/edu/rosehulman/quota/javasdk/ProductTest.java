package edu.rosehulman.quota.javasdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ QuotaClient.class, Partner.class })
public class ProductTest {

  @Test
  public void addUserTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);

    // Create object we are testing
    Product product = new Product(partner, "the_product_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.addUser(partner, product, "user_id")).thenReturn(true);

    // Assert
    assertEquals(true, product.addUser("user_id"));
  }

  @Test
  public void removeUserTest() throws Exception {
    // Mock non-static classes
    Partner partner = Mockito.mock(Partner.class);

    // Create object we are testing
    Product product = new Product(partner, "the_product_id");

    // Statically mock the QuotaClient because it is a singleton
    mockStatic(QuotaClient.class);
    QuotaClient quotaClient = Mockito.mock(QuotaClient.class);
    when(QuotaClient.getInstance()).thenReturn(quotaClient);
    when(quotaClient.removeUser(partner, product, "user_id")).thenReturn(true);

    // Assert
    assertEquals(true, product.removeUser("user_id"));
  }
}
