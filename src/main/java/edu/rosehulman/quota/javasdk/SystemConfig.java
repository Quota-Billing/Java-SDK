package edu.rosehulman.quota.javasdk;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;

public class SystemConfig {

  private static SystemConfig instance;
  private Config config;

  public SystemConfig() {
    Gson gson = new Gson();

    try (Reader reader = new FileReader("./src/main/resources/config.json")) {
      config = gson.fromJson(reader, Config.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized SystemConfig getInstance() throws Exception {
    if (instance == null) {
      instance = new SystemConfig();
    }
    return instance;
  }

  public String getQuotaUrl() {
    return config.getQuotaUrl();
  }
}
