package ys54_yj25.server.game.gameFactory;

import java.awt.EventQueue;

import ys54_yj25.server.game.gameController.GameController;
import ys54_yj25.server.game.gameServer.IGameServer;
import ys54_yj25.server.game.gameServer.Player;

public class GameFactory implements IGameFac {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1036456571613692328L;
	
	private Player player;
	private IGameServer serverStub;
	
	public GameFactory(Player player, IGameServer serverStub) {
		this.player = player;
		this.serverStub = serverStub;
	}

	@Override
	public GameController make() {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Starting game controller...");
					GameController controller = new GameController(player, serverStub);
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return null;
		
	}

}
