package com.tictactoe.test;

import com.tictactoe.model.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

	@Test
	public void testPlaceMark() {
		Board board = new Board();
		assertTrue(board.placeMark(0, 0, 'X'));
		assertFalse(board.placeMark(0, 0, 'O'));
	}

	@Test
	public void testWinRows() {
		Board board = new Board();
		board.placeMark(0, 0, 'X');
		board.placeMark(0, 1, 'X');
		board.placeMark(0, 2, 'X');
		assertTrue(board.checkWin('X'));
	}

	@Test
	public void testWinColumns() {
		Board board = new Board();
		board.placeMark(0, 0, 'O');
		board.placeMark(1, 0, 'O');
		board.placeMark(2, 0, 'O');
		assertTrue(board.checkWin('O'));
	}

	@Test
	public void testWinDiagonals() {
		Board board = new Board();
		board.placeMark(0, 0, 'X');
		board.placeMark(1, 1, 'X');
		board.placeMark(2, 2, 'X');
		assertTrue(board.checkWin('X'));

		board.reset();
		board.placeMark(0, 2, 'O');
		board.placeMark(1, 1, 'O');
		board.placeMark(2, 0, 'O');
		assertTrue(board.checkWin('O'));
	}

	@Test
	public void testDraw() {
		Board board = new Board();
		board.placeMark(0, 0, 'X');
		board.placeMark(0, 1, 'O');
		board.placeMark(0, 2, 'X');
		board.placeMark(1, 0, 'X');
		board.placeMark(1, 1, 'O');
		board.placeMark(1, 2, 'O');
		board.placeMark(2, 0, 'O');
		board.placeMark(2, 1, 'X');
		board.placeMark(2, 2, 'X');

		assertTrue(board.isFull());
		assertFalse(board.checkWin('X'));
		assertFalse(board.checkWin('O'));
	}
}
