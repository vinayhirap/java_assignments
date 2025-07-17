package com.tictactoe.model;

public class Board {
	private final char[][] grid;
	private final int size = 3;
	private final char EMPTY = '-';

	public Board() {
		grid = new char[size][size];
		reset();
	}

	public boolean placeMark(int row, int col, char mark) {
		if (row < 0 || row >= size || col < 0 || col >= size || grid[row][col] != EMPTY) {
			return false;
		}
		grid[row][col] = mark;
		return true;
	}

	public boolean isFull() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (grid[i][j] == EMPTY)
					return false;
		return true;
	}

	public boolean checkWin(char mark) {
		for (int i = 0; i < size; i++) {
			if ((grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark)
					|| (grid[0][i] == mark && grid[1][i] == mark && grid[2][i] == mark)) {
				return true;
			}
		}

		if ((grid[0][0] == mark && grid[1][1] == mark && grid[2][2] == mark)
				|| (grid[0][2] == mark && grid[1][1] == mark && grid[2][0] == mark)) {
			return true;
		}
		return false;
	}

	public void reset() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				grid[i][j] = EMPTY;
	}

	public void display() {
		System.out.println("Current Board:");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
}
