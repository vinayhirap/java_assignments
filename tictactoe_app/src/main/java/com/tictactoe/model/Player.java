package com.tictactoe.model;

public class Player {
	private final String name;
	private final char mark;

	public Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}

	public String getName() {
		return name;
	}

	public char getMark() {
		return mark;
	}
}
