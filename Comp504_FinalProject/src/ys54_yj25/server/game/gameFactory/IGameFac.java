package ys54_yj25.server.game.gameFactory;

import java.io.Serializable;

import ys54_yj25.server.game.gameController.GameController;


/**
 * A game factory interface that construct the controller for the game MVC at
 * the remote side.
 * 
 * @author snolanb
 *
 */
public interface IGameFac extends Serializable{

	/**
	 * Method that make the game controller.
	 * 
	 * @return the game controller
	 */
	public GameController make();
}
