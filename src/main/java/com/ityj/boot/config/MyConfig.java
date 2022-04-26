package com.ityj.boot.config;

import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.ityj.boot.constant.CommonConstant;
import com.ityj.boot.converter.MyPersonMessageConverter;
import com.ityj.boot.entity.Car;
import com.ityj.boot.entity.Pet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  1、配置类里面使用标注在方法上给容器注册组件，默认是单实例的
 *  2、配置类MyConfig本身也是一个组件
 *  3、在spring5.2后@Configuration里有一个proxyBeanMethods属性，可以控制方法是否通过代理生成，
 *      默认为true，Full通过代理生成com.ityj.boot.config.MyConfig$$EnhancerBySpringCGLIB$$146ac44c@aca3c85
 *      false -> Lite: 每次new一个对象
 *  4、@Import({Pet.class, ResourceUtil.class})
 *          --> beanName = com.ityj.boot.entity.Pet
 *              bean = com.github.xiaoymin.knife4j.core.io.ResourceUtil@621624b1
 *      给容器中自动创建出这两个类型的组件，默认组件名称就是类全名
 *
  */
@ImportResource("classpath:bean-pet.xml")
@Import({Pet.class, ResourceUtil.class})
@Configuration   // 告诉SpringBoot这是一个配置类 == 配置文件的作用
@EnableConfigurationProperties(Car.class)  // 第二种注入容器中的方式（ConfigurationProperties）
// 作用 1: 开启Car配置绑定功能  2: 把Car组件自动注入到容器中
public class MyConfig {

    // 默认单实例
    @Bean  // 给容器中添加组件，以方法名作为组件的id；返回类型就是组件类型；返回的值就是组件在容器中的实例
    public Pet myPet() {
        return new Pet("tomcat");
    }

    @Bean
    public Pet dog() {
        return new Pet("dog");
    }

    @ConditionalOnBean(name = "cat")   // 当有组件cat加载时，才会注入elephant组件
    @Bean
    public Pet elephant() {
        return new Pet("elephant");
    }

    @Bean
    public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter() {
        OrderedHiddenHttpMethodFilter orderedHiddenHttpMethodFilter = new OrderedHiddenHttpMethodFilter();
        orderedHiddenHttpMethodFilter.setMethodParam("_hide_method");
        return orderedHiddenHttpMethodFilter;
    }


    // WebMvcConfigurer定制化SpringMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyPersonMessageConverter());
            }

            /*
            *
            *   我们新增这个配置后，有可能会覆盖掉默认的一些功能，所以必须保证原有的ContentNegotiation都是已经添加到这个配置中
            *
            * */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                Map<String, MediaType> mediaTypes = new HashMap<>();
                mediaTypes.put("json", MediaType.APPLICATION_JSON);
                mediaTypes.put("xml", MediaType.APPLICATION_XML);
                // 为了满足浏览器实现format=yj 来返回MyPersonMessageConverter对应的数据类型，需要添加如下MediaType
                mediaTypes.put("yj", MediaType.parseMediaType(CommonConstant.MEDIA_TYPE_YJ));

                ParameterContentNegotiationStrategy parameterContentNegotiationStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
                //parameterContentNegotiationStrategy.setParameterName("yyy");   // 默认format作为key，可以修改
                ContentNegotiationStrategy headerContentNegotiationStrategy = new HeaderContentNegotiationStrategy();
                configurer.strategies(Stream.of(parameterContentNegotiationStrategy, headerContentNegotiationStrategy).collect(Collectors.toList()));
            }
        };
    }
}
