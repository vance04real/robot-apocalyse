package za.ioc.robotapocalypse.integration.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;
import za.ioc.robotapocalypse.dtos.response.RobotResponse;
import za.ioc.robotapocalypse.integration.api.RobotService;

/**
 * Created  02/2022 Project  robot-apocalypse
 * Author   echikuni
 **/

@Service
@RequiredArgsConstructor
public class RobotServiceImpl  implements RobotService {

  private final Environment environment;

  private final OkHttpClient client;

  @Override
  public RobotResponse[] fetchRobots()  throws IOException {

    String robotUrl = environment.getProperty("azure.robot.url");

    HttpUrl url =
        HttpUrl.parse(robotUrl).newBuilder().build();

    Request request =
        new Request.Builder()
            .url(url)
            .get()
            .build();

    try (okhttp3.Response response = client.newCall(request).execute()) {

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

      return objectMapper.readValue((response.body().string()), RobotResponse[].class);
    }

  }

}
