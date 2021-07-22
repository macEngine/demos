1. 内容：
    简单展示，如果通过 WebMvcConfigurer 对 cors 进行配置。
    cors-test1-8080，端口是8080，里面对 cors 进行了配置，允许跨域访问.
    cors-test1-8081，端口是8081，里面未配置 cors。

2. 测试：
    1. 启动8080服务；
    2. 启动8081服务；
    3. 在浏览器中运行如下脚本，验证可以跨域访问8080：
       var token= "test";
       var xhr = new XMLHttpRequest();
       xhr.open('GET', 'http://127.0.0.1:8080/api/pfg/user/getMobileNumber');
       xhr.setRequestHeader("x-access-token",token);
       xhr.send(null);
       xhr.onload = function(e) {
           var xhr = e.target;
           console.log(xhr.responseText);
       }

    运行结果截图：http://blogimg.v2sky.top/blogimg/4880806927458.png

    4. 注释掉 cors 相关配置，重新测试，验证跨域访问失败。


