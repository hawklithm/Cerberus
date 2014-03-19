package org.hawklithm.BlueHawky.utils;

public class Switcher {
	private Switchable target;

	public Switcher(Switchable target) {
		this.target = target;
	}
	
	public void enable(Object... object){
		target.enable(object);
	}
	public void disable(Object... object){
		target.disable(object);
	}

	public Switchable getTarget() {
		return target;
	}

	public void setTarget(Switchable target) {
		this.target = target;
	}
}
