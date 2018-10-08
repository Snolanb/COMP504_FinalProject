package ys54_yj25.commands.user;

import common.IUserMessageType;
import ys54_yj25.server.selectTeam.selectTeamController.SelectTeamMVCFac;

public class TeamMVCFactoryType implements IUserMessageType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2032475463380964952L;
	
	SelectTeamMVCFac fac;
	
	public TeamMVCFactoryType(SelectTeamMVCFac fac) {
		this.fac = fac;
	}
	
	public SelectTeamMVCFac getFac() {
		return this.fac;
	}
	

}
