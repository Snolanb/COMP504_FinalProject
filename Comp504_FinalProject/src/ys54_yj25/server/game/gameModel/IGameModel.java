package ys54_yj25.server.game.gameModel;

import java.rmi.Remote;
import java.util.ArrayList;

import gov.nasa.worldwind.geom.Position;
import ys54_yj25.server.game.gameServer.IGameServer;

public interface IGameModel extends Remote{

	/**
	 * How to communicate with the server?
	 * 		Through a cmd2ModelAdapter with its sendTo method. The message should be sent with the client user's stub to indicate the sender.
	 * 
	 * How to communicate with the client?
	 * 		Through the client user's stub. The GameModel should hold the client user's stub.
	 * 
	 * Where and how to use the MixDataDictionary?
	 * 		The MixDataDictionary is used to store different adapters(?).
	 * 
	 * How does the GameModel get the cmd2ModelAdapter corresponding to the server?
	 * 		The adapter is sent to the client side along with the game MVC. The server can be passed into the game MVC in the game factory when
	 * 		initiating the game controller.
	 * 
	 */

	void setLocationPoint(ArrayList<TargetPlace> targetPlaces);
	
	
}
