运行：
    1. 本地启动redis-server。
    2. 在 IDEA 里面跑 TestRedis 即可。

内容：
    1. 十分简单，可以往redis保存数据，可以获取，可以删除。

注意：
    1. 通过继承CrudRepository接口，spring-data-redis 就可以操作 redis 库中的数据。
    2. 在 spring-boot 2.x版本中，默认是使用lettuce；1.x版本的时候，默认使用的就是Jedis；关于两个的区别：
      　　Jedis和Lettuce都是Redis Client
      　　Jedis 是直连模式，在多个线程间共享一个 Jedis 实例时是线程不安全的。
      　　如果想要在多线程环境下使用 Jedis，需要使用连接池。
      　　每个线程都去拿Jedis 实例，当连接数量增多时，物理连接成本就较高了。
      　　Lettuce 是基于 netty 的，netty 是一个多线程、事件驱动的 I/O 框架，连接实例可以在多个线程间共享，通过异步的方式可以让我们更好的利用系统资源，而不用浪费线程等待网络或磁盘I/O。
        在这个demo中，使用的spring boot版本是2.3.3.RELEASE，所以使用Jedis时，需要显式引入redis.clients 3.3.0。