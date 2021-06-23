package com.pfg.utils.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfg.utils.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(PfgUserController.class)
// @WebMvcTest本身就是JUnit5，不需要添加@RunWith(SpringJUnit4ClassRunner.class)注解
// @SpringBootTest将启动完整的应用程序上下文。
// @WebMvcTest只会扫描您定义的控制器。这个速度要快得多，因为我们只加载你应用的一小部分。
public class PfgUserControllerTest {
  private static final String BASE_URI = "/api/pfg/user";
  private ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mvc;

  @MockBean
  private UserService userService;

  // Pull in the application context created by @ContextConfiguration
  // 使用了 @WebMvcTest，会自动加载默认配置，方便单元测试
  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  public void init() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void whenGetMobileNumber_thenCorrect() throws Exception {
    Mockito.when(userService.getMobileNumber(1L))
        .thenReturn("getMobileNumber in Mock");


    MvcResult mvcResult =
        mvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/getMobileNumber"))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals("getMobileNumber in Mock", content);
  }

  @Test
  public void whenLogin_thenCorrect() throws Exception {
    UserLoginRequest userLoginRequest = new UserLoginRequest();
    userLoginRequest.mobileNumber = "18610000000";
    userLoginRequest.password = "141212";

    Mockito.when(userService.login(userLoginRequest.mobileNumber, userLoginRequest.password))
        .thenReturn("login in Mock");

    System.out.println(objectMapper.writeValueAsString(userLoginRequest));

    MvcResult mvcResult =
        mvc.perform(
            MockMvcRequestBuilders
                .post(BASE_URI + "/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequest)) // 指定客户端能够接收的内容类型
        ).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
    String content = mvcResult.getResponse().getContentAsString();
    assertEquals("login in Mock", content);
  }
}