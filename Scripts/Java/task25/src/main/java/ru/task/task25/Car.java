package ru.task.task25;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

	private Accumulator accumulator;
	private Engine engine;
	private Suspension suspension;
	private  Wheel wheel;

	@Autowired
	public Car(Wheel wheel, Suspension suspension, Engine engine, Accumulator accumulator) {
		this.wheel = wheel;
		this.suspension = suspension;
		this.engine = engine;
		this.accumulator = accumulator;
	}

	@Override
	public String toString() {
		return "Car{" +
				"accumulator=" + accumulator +
				", engine=" + engine +
				", suspension=" + suspension +
				", wheel=" + wheel +
				'}';
	}
}


