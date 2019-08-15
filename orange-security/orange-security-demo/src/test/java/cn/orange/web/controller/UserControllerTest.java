package cn.orange.web.controller;

import cn.orange.utils.ToJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试环境不会真正的去启动内置的 tomcat, 所以测试起来会快点
 *
 * @author : kz
 * @date : 2019/7/20
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    /**
     * 模拟的 mvc 环境
     */
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public ResultActions perform(String url, String method, String content, String param) {
        try {
            MockHttpServletRequestBuilder mhsrb = null;
            if ("get".equalsIgnoreCase(method)) {
                mhsrb = get(url);
            } else if ("post".equalsIgnoreCase(method)) {
                mhsrb = post(url);
            } else if ("put".equalsIgnoreCase(method)) {
                mhsrb = put(url);
            } else if ("delete".equalsIgnoreCase(method)) {
                mhsrb = delete(url);
            }
            if (param != null) {
                MockHttpServletRequestBuilder finalMhsrb = mhsrb;
                Stream.of(param.split(",")).forEach((each) -> {
                    String[] params = each.trim().split(" ");
                    finalMhsrb.param(params[0], params[1]);
                });
            }
            if (content != null) {
                mhsrb.content(content);
            }
            return mockMvc.perform(mhsrb.contentType(MediaType.APPLICATION_JSON_UTF8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("error...");
    }

    /**
     * 当请求成功时
     */
    @Test
    public void whenQuerySuccess() throws Exception {
        perform("/user", "get", null, null);
    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
        mockMvc.perform(post("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tom"));
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(post("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        perform("/user", "post",
                ToJson.of("birthday " + new Date().getTime()), null)
                .andDo(print());
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        perform("/user", "put",
                ToJson.of("id 007, username update, birthday " + new Date().getTime()), null);
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello".getBytes("UTF-8"))))
                .andExpect(status().isOk());

    }
}












