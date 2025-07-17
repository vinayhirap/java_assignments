package com.tictactoe.test;

import com.tictactoe.facade.TicTacToeFacade;
import com.tictactoe.model.Player;

public class TicTacToeAppMain {
	public static void main(String[] args) {
		Player p1 = new Player("Player 1", 'X');
		Player p2 = new Player("Player 2", 'O');

		TicTacToeFacade game = new TicTacToeFacade(p1, p2);
		game.startGame();
	}
}
