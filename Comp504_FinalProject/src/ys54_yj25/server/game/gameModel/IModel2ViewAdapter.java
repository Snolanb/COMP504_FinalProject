package ys54_yj25.server.game.gameModel;

import gov.nasa.worldwind.geom.LatLon;
import map.MapLayer;

public interface IModel2ViewAdapter {
	public void addPlace(Place p);
	public void show(MapLayer layer);
	public void hide(MapLayer layer);
	
	public void wrongAnswerToQuestion();
	public void showTargetPlaces(LatLon pos);
	
	public void missedTargetPlace();
	public void hitTargetPlace();
	
	public void displayQuestion(Question question);
	public void showErrorInfo();
	public void displayHitInfo();
	public void displayMissInfo();
}
