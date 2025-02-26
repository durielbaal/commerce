package com.app.api.price.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.app.api.price.domain.model.Price;
import com.app.api.price.domain.model.PriceRequest;
import com.app.api.user.domain.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = AutowireMode.ALL)
@AutoConfigureWebTestClient
public class PriceControllerTest {

  private final WebTestClient webTestClient;

  public PriceControllerTest(WebTestClient webTestClient) {
    this.webTestClient = webTestClient;
  }

  @Test
  void testGetPriceByFilter_Without_token() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2024 10:00:00");
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isForbidden()
        .expectBody(Price.class);
  }

  @Test
  void testGetPriceByFilter_With_Bad_Credentials() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 10:00:00");
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","badCredentials");
    String token = getToken_with_bad_credentials(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isForbidden()
        .expectBody(Price.class);
  }

  @Test
  void shouldNotFindPriceByFilter() {
    LocalDateTime dateTimeNotWork = parseDateTime("14-06-2024 10:00:00");
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeNotWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> assertThat(price).isNull());
  }

  @Test
  void shouldFindPriceByFilter1() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 10:00:00");
    LocalDateTime startDate = parseDateTime("14-06-2020 00:00:00");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59");
    Price expectedPrice = new Price(1, 1, 35455, new BigDecimal("35.50"), startDate, endDate);
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> {
          assertThat(price).isNotNull();
          assertThat(price.getBrandId()).isEqualTo(expectedPrice.getBrandId());
          assertThat(price.getProductId()).isEqualTo(expectedPrice.getProductId());
          assertThat(price.getPrice()).isEqualByComparingTo(expectedPrice.getPrice());
        });
  }

  @Test
  void shouldFindPriceByFilter2() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 16:00:00");
    LocalDateTime startDate = parseDateTime("14-06-2020 15:00:00");
    LocalDateTime endDate = parseDateTime("14-06-2020 18:30:00");
    Price expectedPrice = new Price(1, 2, 35455, new BigDecimal("25.45"), startDate, endDate);
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> {
          assertThat(price).isNotNull();
          assertThat(price.getBrandId()).isEqualTo(expectedPrice.getBrandId());
          assertThat(price.getProductId()).isEqualTo(expectedPrice.getProductId());
          assertThat(price.getPrice()).isEqualByComparingTo(expectedPrice.getPrice());
        });
  }

  @Test
  void shouldFindPriceByFilter3() {
    LocalDateTime dateTimeWork = parseDateTime("14-06-2020 21:00:00");
    LocalDateTime startDate = parseDateTime("14-06-2020 00:00:00");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59");
    Price expectedPrice = new Price(1, 1, 35455, new BigDecimal("35.50"), startDate, endDate);
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> {
          assertThat(price).isNotNull();
          assertThat(price.getBrandId()).isEqualTo(expectedPrice.getBrandId());
          assertThat(price.getProductId()).isEqualTo(expectedPrice.getProductId());
          assertThat(price.getPrice()).isEqualByComparingTo(expectedPrice.getPrice());
        });
  }

  @Test
  void shouldFindPriceByFilter4() {
    LocalDateTime dateTimeWork = parseDateTime("15-06-2020 10:00:00");
    LocalDateTime startDate = parseDateTime("15-06-2020 00:00:00");
    LocalDateTime endDate = parseDateTime("15-06-2020 11:00:00");
    Price expectedPrice = new Price(1, 3, 35455, new BigDecimal("30.50"), startDate, endDate);
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> {
          assertThat(price).isNotNull();
          assertThat(price.getBrandId()).isEqualTo(expectedPrice.getBrandId());
          assertThat(price.getProductId()).isEqualTo(expectedPrice.getProductId());
          assertThat(price.getPrice()).isEqualByComparingTo(expectedPrice.getPrice());
        });
  }
  @Test
  void shouldFindPriceByFilter5() {
    LocalDateTime dateTimeWork = parseDateTime("16-06-2020 21:00:00");
    LocalDateTime startDate = parseDateTime("15-06-2020 16:00:00");
    LocalDateTime endDate = parseDateTime("31-12-2020 23:59:59");
    Price expectedPrice = new Price(1, 4, 35455, new BigDecimal("38.95"), startDate, endDate);
    PriceRequest priceRequest = new PriceRequest(1, 35455, dateTimeWork);
    User user = new User("admin","admin");
    String token = getToken(user);
    webTestClient.post()
        .uri("/price/get/bargains")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + token)
        .bodyValue(priceRequest)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Price.class)
        .value(price -> {
          assertThat(price).isNotNull();
          assertThat(price.getBrandId()).isEqualTo(expectedPrice.getBrandId());
          assertThat(price.getProductId()).isEqualTo(expectedPrice.getProductId());
          assertThat(price.getPrice()).isEqualByComparingTo(expectedPrice.getPrice());
        });
  }

  private LocalDateTime parseDateTime(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return LocalDateTime.parse(dateString, formatter);
  }

  private String getToken(User user){
    return webTestClient.post()
        .uri("/auth/security/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(user)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .returnResult()
        .getResponseBody();
  }

  private String getToken_with_bad_credentials(User user){
    return webTestClient.post()
        .uri("/auth/security/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(user)
        .exchange()
        .expectStatus().isForbidden()
        .expectBody(String.class)
        .returnResult()
        .getResponseBody();
  }

}
