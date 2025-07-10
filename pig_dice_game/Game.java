package com.aurionpro.pig;

public class Game {

	public static void main(String[] args) {
		new Game().start();
	}

	private void start() {
		UserInput input = new UserInput();
		Die die = new Die();

		while (true) {
			int choice = input.readInt("Choose '1' to play or '0' to exit");

			if (choice == 0) {
				exitGame();
				return;
			}

			playGame(input, die);
		}
	}

	private void exitGame() {
		System.out.println("Thank you for playing!");
	}

	private void playGame(UserInput input, Die die) {
		Player player = new Player();
		Turn turn = new Turn();

		while (!turn.isGameOver() && !player.hasWon()) {
			System.out.println("Your current turn is: " + turn.getCurrentTurn());
			playTurn(input, die, player, turn);
		}

		if (!player.hasWon()) {
			System.out.println("Game over! Your final score is: " + player.getScore());
		}
	}

	private void playTurn(UserInput input, Die die, Player player, Turn turn) {
		while (true) {
			String choice = input.readString("Do you want to roll (r) or hold (h)?");

			if (choice.equalsIgnoreCase("r")) {
				int roll = die.roll();
				handleRoll(roll, player, turn);
				if (roll == 1)
					return;
			}

			if (choice.equalsIgnoreCase("h")) {
				handleHold(player, turn);
				return;
			}
		}
	}

	private void handleRoll(int roll, Player player, Turn turn) {
		if (roll == 1) {
			System.out.println("You rolled 1. Score reset to 0. Moving to next turn.");
			player.resetScore();
			turn.next();
			return;
		}

		player.addToScore(roll);
		System.out.println("Die rolled: " + roll + " | Score: " + player.getScore());
	}

	private void handleHold(Player player, Turn turn) {
		System.out.println("You chose to hold. Score: " + player.getScore());

		if (player.hasWon()) {
			System.out.println("You WON! Final score: " + player.getScore());
		}

		turn.next();
	}
}
