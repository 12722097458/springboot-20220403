package com.ityj.boot;

import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.ityj.boot.config.MyConfig;
import com.ityj.boot.entity.Pet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@MapperScan("com.ityj.boot.mapper")
//@ServletComponentScan(basePackages = {"com.ityj.boot.servlet"})
@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		// 返回IOC容器
		ConfigurableApplicationContext run = SpringApplication.run(BootApplication.class, args);
		// 查看容器里的所有组件
		String[] beanDefinitionNames = run.getBeanDefinitionNames();
		for (String name : beanDefinitionNames) {
			System.out.println(name);
		}

		MyConfig myConfig = run.getBean(MyConfig.class);
		System.out.println("myConfig = " + myConfig);

		Pet myPet = run.getBean("myPet", Pet.class);
		System.out.println("myPet = " + myPet);

		// 获取通过@Import导入的组件
		String[] beanNamesForType = run.getBeanNamesForType(Pet.class);
		for (String name : beanNamesForType) {
			System.out.println("beanName = " + name);
		}
		ResourceUtil bean = run.getBean(ResourceUtil.class);
		System.out.println("bean = " + bean);


		boolean dog = run.containsBean("dog");
		System.out.println("dog组件是否存在 = " + dog);
		boolean cat = run.containsBean("cat");
		System.out.println("cat组件是否存在 = " + cat);
		boolean elephant = run.containsBean("elephant");
		System.out.println("elephant组件是否存在 = " + elephant);

		boolean bean1 = run.containsBean("xml-pet");
		System.out.println("通过XML配置的xml-pet组件是否存在 = " + bean1);

		int beanDefinitionCount = run.getBeanDefinitionCount();
		System.out.println("共加载beanDefinitionCount = " + beanDefinitionCount);

		Map<String, Object> systemEnvironment = run.getEnvironment().getSystemEnvironment();
		System.out.println("systemEnvironment = " + systemEnvironment);

	}

}
