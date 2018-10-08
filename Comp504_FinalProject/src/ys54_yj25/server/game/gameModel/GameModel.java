package ys54_yj25.server.game.gameModel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.GlobeAnnotation;
import map.MapLayer;
import ys54_yj25.server.game.gameServer.IGameServer;
import ys54_yj25.server.game.gameServer.Player;

public class GameModel {

	private IModel2ViewAdapter _adpt;
	private MapLayer _layer = new MapLayer();

	private ArrayList<TargetPlace> targetPlaces = new ArrayList<>();

	TargetPlace target1 = new TargetPlace(LatLon.fromRadians(29.718550, -95.399068), 30);
	TargetPlace target2 = new TargetPlace(LatLon.fromRadians(51.477222, 0.0), 30);
	TargetPlace target3 = new TargetPlace(LatLon.fromRadians(48.860930, 2.336461), 30);
	TargetPlace target4 = new TargetPlace(LatLon.fromRadians(37.971458, 23.726647), 30);
	TargetPlace target5 = new TargetPlace(LatLon.fromRadians(41.890306, 12.492354), 30);
	TargetPlace target6 = new TargetPlace(LatLon.fromRadians(27.174932, 78.042144), 30);
	TargetPlace target7 = new TargetPlace(LatLon.fromRadians(29.976788, 31.134001), 30);
	TargetPlace target8 = new TargetPlace(LatLon.fromRadians(40.68925, -74.044493), 30);
	TargetPlace target9 = new TargetPlace(LatLon.fromRadians(36.095568, -115.176033), 30);
	TargetPlace target10 = new TargetPlace(LatLon.fromRadians(29.71724, -95.40150), 30);

	private List<Question> questions = new ArrayList<>();
	private TargetPlace target;
	private Question question;

	private Player player;

	int successCount;

	int placeCount = 0;

	int questionNum = 0;

	int targetIndex = 0;

	int ans = Integer.MIN_VALUE;

	IGameServer gameServerStub;

	// public GameModel(IModel2ViewAdapter adpt, Player player) {
	// this._adpt = adpt;
	// this.player = player;
	// // targetPlaces = new ArrayList<>();;
	//
	// target = new TargetPlace(LatLon.fromRadians(0.0, 0.0), 15);
	//
	// // question = createSampleQ();
	// }

	public GameModel(IModel2ViewAdapter adpt, Player player, IGameServer gameServerStub) {
		this._adpt = adpt;
		this.player = player;
		this.gameServerStub = gameServerStub;
		successCount = 0;
		targetPlaces.add(target1);
		targetPlaces.add(target2);
		targetPlaces.add(target3);
		targetPlaces.add(target4);
		targetPlaces.add(target5);
		targetPlaces.add(target6);
		targetPlaces.add(target7);
		targetPlaces.add(target8);
		targetPlaces.add(target9);
		targetPlaces.add(target10);
		for (int i = 0; i < 10; i++) {
			questions.add(Question.getRandomQuestion());
		}

	}

	public void click(Position p) {
		System.out.println("Mouse clicked at: " + p + " altitude = " + p.getAltitude());

		// if (Role.MAP_FINDER.equals(player.getRole())) {
		// if (target.hitTarget(p)) {
		// System.out.println("Hit...");
		// _adpt.hitTargetPlace();
		// successCount++;
		// if (successCount == 10) {
		// // communicate back to server, show won game and stop other teams.
		// try {
		// gameServerStub.sendWinningMsg(player);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// // TODO Won game, communicate back to Game server
		// } else {
		// System.out.println("Miss...");
		// _adpt.missedTargetPlace();
		// }
		// }
		target = targetPlaces.get(targetIndex);
		System.out.println(target);
		if (target.hitTarget(p)) {
			System.out.println("Hit...");
			_adpt.hitTargetPlace();
			_adpt.displayHitInfo();
			successCount++;
			targetIndex++;
			if (successCount == 10) {
				// communicate back to server, show won game and stop other teams.
				try {
					// append info on view
					_adpt.displayHitInfo();

					gameServerStub.sendWinningMsg(player);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// TODO Won game, communicate back to Game server
		} else {
			System.out.println("Miss...");
			_adpt.displayMissInfo();
			_adpt.missedTargetPlace();
		}

	}

	public void showQuestion() {
		Question curQuestion = questions.get(questionNum);
		ans = curQuestion.showAns();
		questionNum++;
		_adpt.displayQuestion(curQuestion);
	}

	public void answerQuestion(Integer answers) {
		// if (Role.QUESTION_SOLVER.equals(player.getRole()))
		// if (true) {
		// if (this.question.isCorrectAnswer(answers)) {
		// TargetPlace showTarget = targetPlaces.get(placeCount);
		// placeCount++;
		// _adpt.showTargetPlaces(showTarget.getPos());
		// } else {
		// _adpt.wrongAnswerToQuestion();
		// }
		// }
		if (answers.equals(ans)) {
			TargetPlace showTarget = targetPlaces.get(placeCount);
			placeCount++;
			System.out.println(showTarget.getPos().getLatitude());
			System.out.println(showTarget.getPos().getLongitude());
			_adpt.showTargetPlaces(showTarget.getPos());
		} else {
			_adpt.showErrorInfo();
		}

	}

	public void start() {
		Position willy = Position.fromDegrees(29.718550, -95.399068, 150);
		Position epcot = Position.fromDegrees(28.374454, -81.549363, 1000);
		Position nyc = Position.fromDegrees(40.748974, -73.990288, 10000);

		_adpt.addPlace(new Place("Greenwich", Position.fromDegrees(51.477222, 0.0, 1000)));
		_adpt.addPlace(new Place("Louvre", Position.fromDegrees(48.860930, 2.336461, 1000)));
		_adpt.addPlace(new Place("London Eye", Position.fromDegrees(51.503367, -0.119968, 1000)));
		_adpt.addPlace(new Place("Acropolis", Position.fromDegrees(37.971458, 23.726647, 800)));
		_adpt.addPlace(new Place("Colosseum", Position.fromDegrees(41.890306, 12.492354, 500)));
		_adpt.addPlace(new Place("Taj Mahal", Position.fromDegrees(27.174932, 78.042144, 1000)));
		_adpt.addPlace(new Place("Pyramids", Position.fromDegrees(29.976788, 31.134001, 1500)));
		_adpt.addPlace(new Place("Statue of Liberty", Position.fromDegrees(40.68925, -74.044493, 500)));
		_adpt.addPlace(new Place("NYC", nyc));
		_adpt.addPlace(new Place("Luxor", Position.fromDegrees(36.095568, -115.176033, 1500)));
		_adpt.addPlace(new Place("Grand Canyon", Position.fromDegrees(36.108091, -113.214912, 90000)));
		_adpt.addPlace(new Place("Golden Gate", Position.fromDegrees(37.82035, -122.4778804, 5000)));
		_adpt.addPlace(new Place("Epcot Center", epcot));
		_adpt.addPlace(new Place("Willy", willy));
		_adpt.addPlace(new Place("Rice", Position.fromDegrees(29.71724, -95.40150, 1000)));

		// _layer.addToggleAnnotation("Willy", "Willy Selected!", willy);
		// _layer.addToggleAnnotation("Epcot Center", "Epcot Selected!", epcot, 5000,
		// 1000000);
		_layer.addAnnotation(new GlobeAnnotation("NYC", nyc));
		_adpt.show(_layer);
	}

	// private Question createSampleQ() {
	// Map<Character, String> choices = new HashMap<>();
	// choices.put('A', "Yes");
	// choices.put('B', "No");
	// choices.put('C', "Mixed Feelings");
	// choices.put('D', "Not Sure");
	//
	// Set<Character> ans = new HashSet<>();
	// ans.add('A');
	//
	// return new Question("Do you love Wong and 504?", choices, ans);
	// }

}
