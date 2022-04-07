package com.ityj.boot;

import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.ityj.boot.config.MyConfig;
import com.ityj.boot.entity.Pet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

	}

}
