package com.ityj.boot.config;

import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.ityj.boot.entity.Car;
import com.ityj.boot.entity.Pet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

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

}
