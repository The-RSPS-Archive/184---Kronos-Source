package io.ruin.api.utils;

public class Tuple<F, S> {

	private F first;
	private S second;

	public Tuple(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F first() {
		return first;
	}

	public S second() {
		return second;
	}
}
