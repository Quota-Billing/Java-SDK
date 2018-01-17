package edu.rosehulman.quota.javasdk;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SystemConfig {

  private static SystemConfig instance;
  private Config config;

  public SystemConfig() {
    Gson gson = new Gson();

    try (Reader reader = new FileReader("./src/main/resources/config.json")) { //TODO There is a bug where it is ALWAYS local
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
