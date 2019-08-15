package cn.orange.web.controller;

import cn.orange.utils.ToJson;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * @author : kz
 * @date : 2019/7/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WireMockTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8062));

    private RestTemplate restTemplate = new RestTemplate();

    public String access(String url){
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        System.out.println("<<<<<<<<<<<<<<<<<" + result);
        return result.getBody();
    }

    @Test
    public void wireMock1() {
        String url = "/api/add";

        wireMockRule.stubFor(
            get(urlEqualTo(url)).willReturn(aResponse().withStatus(200)
                    .withBody(ToJson.of("username username, password password")))
        );
        access("http://127.0.0.1:8062/" + url);
    }



}
