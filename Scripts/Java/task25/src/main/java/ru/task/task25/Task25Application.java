package ru.task.task25;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Task25Application {

	public static void main(String[] args) {

//		Необходимо создать объект класса Car, который состоит из следующих частей:
//		- Wheel
//		- Engine, который состоит из starter,  spark plug
//		- Accumulator
//		- Suspension, который состоит из hinge, differential
//
//		Выполнить внедрение зависимостей тремя способами:
//		- через xml
//	    - xml + java annotation
//	    -  java code
//
//		вывести на консоль объект класса Car
//		В каждом классе необходимо переопределить метод toString(), для информативности


//		1. Внедрение зависимостей через xml:
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Car car = context.getBean("myCar", Car.class);
		System.out.println("1. Внедрение зависимостей через xml:");
		System.out.println(car + "\n");
		context.close();


//		2. Внедрение зависимостей через xml + java annotation:
		ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext("applicationContext2.xml");
		Car car2 = context2.getBean("car", Car.class);
		System.out.println("2. Внедрение зависимостей через xml + java annotation:");
		System.out.println(car2 + "\n");
		context2.close();

//		3. Внедрение зависимостей через java code:
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(configurationClass.class);
		Car car3 = applicationContext.getBean("car", Car.class);
		System.out.println("3. Внедрение зависимостей через java code:");
		System.out.println(car3 + "\n");
		applicationContext.close();



	}

}
