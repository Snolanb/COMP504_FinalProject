package ys54_yj25.server.game.gameController;

import java.awt.List;
import java.util.ArrayList;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import map.IRightClickAction;
import map.MapLayer;
import ys54_yj25.server.game.gameModel.GameModel;
import ys54_yj25.server.game.gameModel.IModel2ViewAdapter;
import ys54_yj25.server.game.gameModel.Place;
import ys54_yj25.server.game.gameModel.Question;
import ys54_yj25.server.game.gameModel.TargetPlace;
import ys54_yj25.server.game.gameServer.IGameServer;
import ys54_yj25.server.game.gameServer.Player;
import ys54_yj25.server.game.gameView.AppFrame;
import ys54_yj25.server.game.gameView.IView2ModelAdapter;

public class GameController {

	private AppFrame<Place> _view;
	private GameModel _model;
	
	private Player player;
	
	private ArrayList<TargetPlace> targetPlaces;
	
	public GameController(Player player, IGameServer serverStub) {
		_view  = new AppFrame<Place>(new IView2ModelAdapter<Place>() {
			public void goPlace(Place p) {
				_view.setPosition(p.getPosition());
			}
			public void goLatLong(String latitude, String longitude) {
				try {
					_view.setPosition(Position.fromDegrees(Double.parseDouble(latitude), Double.parseDouble(longitude), 4000));
				} catch (Exception e) {
					System.out.println("Improper latitude, longitude: " + latitude + ", " + longitude);
				}
			}
			@Override
			public void checkAnswer(String ans) {
				// TODO Auto-generated method stub
				_model.answerQuestion(Integer.valueOf(ans));
				
			}
			@Override
			public void askQuestion() {
				// TODO Auto-generated method stub
				_model.showQuestion();
				
			}
		}, new IRightClickAction() {
			public void apply(Position p) {
				_model.click(p);				
			}
		});
		_model = new GameModel(new IModel2ViewAdapter() {
			public void addPlace(Place p) {
				_view.addPlace(p);
			}
			public void show(MapLayer layer) {
				_view.addMapLayer(layer);
			}
			public void hide(MapLayer layer) {
				_view.removeMapLayer(layer);
			}
			@Override
			public void wrongAnswerToQuestion() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void showTargetPlaces(LatLon pos) {
				_view.displayNextFiled(pos);

				
			}
			@Override
			public void hitTargetPlace() {
				// TODO Auto-generated method stub
			}
			@Override
			public void missedTargetPlace() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void displayQuestion(Question question) {
				// TODO Auto-generated method stub
				_view.displayQuestion(question);
				
			}
			@Override
			public void showErrorInfo() {
				// TODO Auto-generated method stub
				_view.displayErrorIn();
				
			}
			@Override
			public void displayHitInfo() {
				// TODO Auto-generated method stub
				_view.displayHitInfo();
				
			}
			@Override
			public void displayMissInfo() {
				// TODO Auto-generated method stub
				_view.displayMissInfo();
				
			}
		}, player, serverStub);
	}
	
	public void start() {
		_view.start();
		_model.start();
	}
	
	public static void main(String[] args) {
		GameController gc = new GameController(null, null);
		gc.start();
	}
}
