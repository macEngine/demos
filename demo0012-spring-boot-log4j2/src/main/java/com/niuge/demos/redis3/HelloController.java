package com.niuge.demos.redis3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
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

  @RequestMapping(value = "/bookings/{id}/{version}/abc{shippingGroupId}", method = RequestMethod.GET)
  @ResponseBody
  public String search(@PathVariable("id") String id,
                       @PathVariable("version") String version,
                       @RequestParam(value = "shippingGroupId", required = false) String shippingGroupId,
                       HttpServletRequest request) {
    String lookupPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

    System.out.println();
    System.out.println(lookupPath);
    System.out.println(id);
    System.out.println(version);
    System.out.println(shippingGroupId);
    System.out.println();
    System.out.println();
    return id; //view
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
