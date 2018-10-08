package ys54_yj25.server.game.gameModel;

import java.util.Random;

public class Question {

	private String question;
	private Integer answers;
	

	public Question(String question, Integer answers) {
		this.question = question;
		this.answers = answers;
	}
	
	public boolean isCorrectAnswer(Integer answers) {
		return this.answers.equals(answers);
	}
	
	public String showQuestion() {
		return this.question;
	}
	
	public int showAns() {
		return this.answers;
	}

	public static Question getRandomQuestion() {
		Random rand = new Random();
		String question = "";
		Integer ans = -99999;
		int a = rand.nextInt(99);
		int b = rand.nextInt(99);
		int op = rand.nextInt(3) + 1;

		if (op == 1) {
			ans = a + b;
			question = a + " + " + b;
		}
		if (op == 2) {
			ans = a - b;
			question = a + " - " + b;
		}
		if (op == 3) {
			ans = a * b;
			question = a + " x " + b;
		}

		return new Question(question, ans);
	}
}

