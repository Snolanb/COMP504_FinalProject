package ys54_yj25.server.game.gameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ys54_yj25.server.game.gameModel.IGameModel;
import ys54_yj25.server.game.gameModel.TargetPlace;

public interface IGameServer extends Remote {
	
	public void sendWinningMsg(Player player) throws RemoteException;
	
	
}
