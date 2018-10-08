package ys54_yj25.commands.user;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IUserCmd2ModelAdapter;

public class GameMVCCmd extends DataPacketUserAlgoCmd<GameMVCFactoryType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2914820127246638756L;

	@Override
	public String apply(Class<?> index, DataPacketUser<GameMVCFactoryType> host, String... params) {
		// TODO Auto-generated method stub
		System.out.println("Bringing up the game MVC...");
		host.getData().getFac().make();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
