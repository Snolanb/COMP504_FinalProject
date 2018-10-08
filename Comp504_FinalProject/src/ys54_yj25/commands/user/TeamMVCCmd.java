package ys54_yj25.commands.user;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IUserCmd2ModelAdapter;

public class TeamMVCCmd extends DataPacketUserAlgoCmd<TeamMVCFactoryType>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5158678034963246530L;
	
	@Override
	public String apply(Class<?> index, DataPacketUser<TeamMVCFactoryType> host, String... params) {
		// TODO Auto-generated method stub
		System.out.println("Bringing up the select team MVC....");
		host.getData().getFac().make();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
