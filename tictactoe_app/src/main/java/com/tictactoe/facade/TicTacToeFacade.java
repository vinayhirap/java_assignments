package com.tictactoe.facade;

import com.tictactoe.model.Board;
import com.tictactoe.model.Player;
import java.util.Scanner;

public class TicTacToeFacade {
	private final Board board;
	private final Player player1;
	private final Player player2;
	private Player currentPlayer;
	private final Scanner sc;

	public TicTacToeFacade(Player player1, Player player2) {
		this.board = new Board();
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1;
		this.sc = new Scanner(System.in);
	}

	public void startGame() {
		System.out.println("=== Tic-Tac-Toe ===");
		board.display();

		while (true) {
			System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getMark() + ")");
			int row = getValidInput("Enter row (0-2): ");
			int col = getValidInput("Enter column (0-2): ");

			if (!board.placeMark(row, col, currentPlayer.getMark())) {
				System.out.println("Invalid move. Cell occupied or out of range. Try again.");
				continue;
			}

			board.display();

			if (board.checkWin(currentPlayer.getMark())) {
				System.out.println(currentPlayer.getName() + " wins!");
				break;
			}

			if (board.isFull()) {
				System.out.println("Game is a draw!");
				break;
			}

			switchPlayer();
		}
	}

	private void switchPlayer() {
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
	}

	private int getValidInput(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				int value = Integer.parseInt(sc.nextLine());
				if (value >= 0 && value <= 2) {
					return value;
				} else {
					System.out.println("Input must be between 0 and 2.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Enter a number between 0 and 2.");
			}
		}
	}

	public void resetGame() {
		board.reset();
		currentPlayer = player1;
		System.out.println("Game reset.");
	}
}
