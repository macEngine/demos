package com.niuge.demos.redis3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class HelloController {

  private static final Logger logger = LogManager.getLogger(HelloController.class);

  private List<Integer> num = Arrays.asList(1, 2, 3, 4, 5);

  @GetMapping("/")
  public String main(Model model) {

    // pre-java 8
    if (logger.isDebugEnabled()) {
      logger.debug("Hello from Log4j 2 - num : {}", num);
    }

    // java 8 lambda, no need to check log level
    logger.debug("Hello from Log4j 2 - num : {}", () -> num);

    model.addAttribute("tasks", num);
    return "welcome"; //view
  }

  private int getNum() {
    return 100;
  }

  @PostMapping(value =  "/translate")
  @ResponseBody
  public String translate() {
    String result = new Random().nextInt(100) + "";
    return "{\"code\": 200, \"data\": \"测试" + result + "\"}";
  }
}
