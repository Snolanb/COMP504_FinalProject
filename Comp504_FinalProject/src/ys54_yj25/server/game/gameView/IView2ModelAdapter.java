package ys54_yj25.server.game.gameView;

public interface IView2ModelAdapter<CBType> {
	public void goLatLong(String latitude, String longitude);
	public void goPlace(CBType o);
	
	public void checkAnswer(String ans);
	
	public void askQuestion();
}
