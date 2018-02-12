package org.unibl.etf.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
	
	public void attachObserver(Observer observer) {
		observers.add(observer);
	}
	public void detachObserver(Observer observer) {
		observers.remove(observer);
	}
	public abstract void update();
	
	protected List<Observer> observers = new ArrayList<Observer>();
}
