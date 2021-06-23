In this module, we demo how to test controller layer.

Please note that:
    1. Run PfgUserControllerTest in IDEA or you can use maven command line.
    2. During the test, UserService is mocked, we only focus controller layer.
    3. @WebMvcTest is used instead of @SpringBootTest
       @WebMvcTest only load testing controller, which is fast.
       @SpringBootTest load all controllers.
