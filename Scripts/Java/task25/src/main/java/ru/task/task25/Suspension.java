package ru.task.task25;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Suspension {

	@Autowired
	private Hinge hinge;
	@Autowired
	private Differential differential;

	public Suspension(Hinge hinge, Differential differential) {
		this.hinge = hinge;
		this.differential = differential;
	}

	public Suspension() {
	}

	public Hinge getHinge() {
		return hinge;
	}

	public void setHinge(Hinge hinge) {
		this.hinge = hinge;
	}

	public Differential getDifferential() {
		return differential;
	}

	public void setDifferential(Differential differential) {
		this.differential = differential;
	}

	@Override
	public String toString() {
		return "Suspension{" +
				"hinge=" + hinge +
				", differential=" + differential +
				'}';
	}
}
