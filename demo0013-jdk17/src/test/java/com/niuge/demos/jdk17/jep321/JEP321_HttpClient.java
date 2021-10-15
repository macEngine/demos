package com.niuge.demos.jdk17.jep321;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class JEP321_HttpClient {
  @Test
  public void 同步get() throws Exception {
    HttpClient httpClient = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://www.baidu.com"))
        .timeout(Duration.ofSeconds(20))
        .header("Content-Type", "application/json")
        .build();
    HttpResponse<Path> response =
        httpClient.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get("abc.txt")));
    System.out.println("Response status code: " + response.statusCode());
    System.out.println("Response headers: " + response.headers());
    System.out.println("Response body: " + response.body());
  }

  @Test
  public void 异步get() throws Exception {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://www.baidu.com"))
        .build();

    CompletableFuture<String> strResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body);
    strResponse.whenComplete((res, t) -> {
      if (t != null) {
        System.out.println(t.getMessage());
      } else {
        System.out.println(res);
      }
    }).join();
  }
}
