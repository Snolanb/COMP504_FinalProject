package ys54_yj25.server.game.gameModel;

public class Choice {

	private String context;
	private boolean isCorrectAnswer;
	
	public Choice(String context, boolean isCorrectAnswer) {
		this.context = context;
		this.isCorrectAnswer = isCorrectAnswer;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public boolean isCorrectAnswer() {
		return isCorrectAnswer;
	}

	public void setCorrectAnswer(boolean isCorrectAnswer) {
		this.isCorrectAnswer = isCorrectAnswer;
	}
}