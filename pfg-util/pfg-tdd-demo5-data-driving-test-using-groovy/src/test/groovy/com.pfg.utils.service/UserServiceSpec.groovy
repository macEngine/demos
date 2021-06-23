package com.pfg.utils.service

import com.pfg.utils.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = UserService)
class UserServiceSpec extends Specification {

    @Autowired(required = false)
    private UserService userService;

    def "测试 userService 不能为空"() {
        expect: "userService 被正确初始化"
        userService
    }

    def "测试 login 接口, 这里主要是想 demo 使用 groovy 进行条件测试的灵活性，数据驱动，简化代码"() {
        given:
        String result

        when:
        result = userService.login(mobileNumber, password)

        then:
        result == expectResult

        where:
        mobileNumber  | password || expectResult
        "18600000001" | "111111" || "18600000001_111111"
        "18600000002" | "111112" || "18600000002_111112"
        "18600000003" | "111113" || "18600000003_111113"
        "18600000004" | "111114" || "18600000004_111114"
        "18600000005" | "111115" || "18600000005_111115"

    }
}