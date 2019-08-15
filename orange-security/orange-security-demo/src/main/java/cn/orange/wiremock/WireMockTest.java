package cn.orange.wiremock;

import cn.orange.utils.ToJson;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * 一般放在测试域里面 ?
 *
 * @author : kz
 * @date : 2019/7/25
 */
public class WireMockTest {

    public static void main(String[] args) {
        new WireMockServer(wireMockConfig().port(8062)).start();

        configureFor(8062);
        removeAllMappings();

        stubFor(get(urlEqualTo("/api/add"))
                .willReturn(aResponse().withStatus(200).withBody(
                        ToJson.of("username username, password password"))));


    }
}
