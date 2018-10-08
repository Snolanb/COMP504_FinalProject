package ys54_yj25.commands.user;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IUserCmd2ModelAdapter;
import common.datatype.user.IUserInstallCmdType;
import provided.datapacket.DataPacketAlgo;
import ys54_yj25.server.serverModel.MyUserCmd2ModelAdapter;

public class InstallCmd extends DataPacketUserAlgoCmd<IUserInstallCmdType>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7976976782734777550L;
	
	private MyUserCmd2ModelAdapter cmd2ModelAdpt;
	private DataPacketAlgo<String, String> commands;
	
	public InstallCmd(MyUserCmd2ModelAdapter cmd2ModelAdpt, DataPacketAlgo<String, String> commands) {
		this.cmd2ModelAdpt = cmd2ModelAdpt;
		this.commands = commands;
	}

	@Override
	public String apply(Class<?> index, DataPacketUser<IUserInstallCmdType> host, String... params) {
		// TODO Auto-generated method stub
		//Class<?> cmdIndex = host.getData().getClass();
		Class<?> cmdIndex = host.getData().getCmdId();
		DataPacketUserAlgoCmd<?> cmd = host.getData().getCmd();
		cmd.setCmd2ModelAdpt(cmd2ModelAdpt);
		commands.setCmd(cmdIndex, cmd);
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
