http://blog.csdn.net/webzhuce/article/details/54560496
https://github.com/lihengming/spring-boot-api-project-seed

1. @RestController是@Controller和@ResponseBody的结合体，两个标注合并起来的作用。

2. @GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。该注解将HTTP Get 映射到 特定的处理方法上。

3.
@ConfigurationProperties
Spring Boot 可以方便的将属性注入到一个配置对象中。例如：

my.name=Isea533
my.port=8080
my.servers[0]=dev.bar.com
my.servers[1]=foo.bar.com

对应对象：
@ConfigurationProperties(prefix="my")
public class Config {
    private String name;
    private Integer port;
    private List<String> servers = new ArrayList<String>();

    public String geName(){
        return this.name;
    }

    public Integer gePort(){
        return this.port;
    }
    public List<String> getServers() {
        return this.servers;
    }
}
Spring Boot 会自动将prefix="my"前缀为my的属性注入进来。

Spring Boot 会自动转换类型，当使用List的时候需要注意在配置中对List进行初始化！

Spring Boot 还支持嵌套属性注入，例如：

name=isea533
jdbc.username=root
jdbc.password=root

对应的配置类：

@ConfigurationProperties
public class Config {
    private String name;
    private Jdbc jdbc;
    class Jdbc {
        private String username;
        private String password;
        //getter...
    }

    public Integer gePort(){
        return this.port;
    }
    public Jdbc getJdbc() {
        return this.jdbc;
    }
}
jdbc开头的属性都会注入到Jdbc对象中。