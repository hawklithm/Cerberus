package com.hawklithm.utils;

public class Pair<T,V> {
	private T first;
	private V last;
	
	public Pair(T first,V last) {
		this.first = first;
		this.last = last;
	}
	
	public void setFirst(T first) {
		this.first = first;
	}
	
	public void setLast(V last) {
		this.last = last;
	}
	
	public T getFirst() {
		return first;
	}
	
	public V getLast() {
		return last;
	}
}
