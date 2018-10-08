package ys54_yj25.commands.user;

import common.IUserMessageType;
import ys54_yj25.server.game.gameFactory.IGameFac;

public class GameMVCFactoryType implements IUserMessageType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 700004058305660697L;

	private IGameFac fac;
	
	public GameMVCFactoryType(IGameFac fac) {
		this.fac = fac;
	}
	
	public IGameFac getFac() {
		return this.fac;
	}
}
