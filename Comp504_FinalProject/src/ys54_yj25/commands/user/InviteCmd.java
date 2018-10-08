package ys54_yj25.commands.user;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IChatRoom;
import common.IUserCmd2ModelAdapter;
import common.datatype.user.IInvitationType;
import ys54_yj25.server.serverModel.MyUserCmd2ModelAdapter;

public class InviteCmd extends DataPacketUserAlgoCmd<IInvitationType> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7305574023358615406L;
	
	private MyUserCmd2ModelAdapter _cmd2ModelAdpt;
	
	public InviteCmd(MyUserCmd2ModelAdapter adpt) {
		this._cmd2ModelAdpt = adpt;
	}

	@Override
	public String apply(Class<?> index, DataPacketUser<IInvitationType> host, String... params) {
		// TODO Auto-generated method stub
		System.out.println("Joining team...");
		IChatRoom team = host.getData().getChatRoom();
		//User.this.addChatRoom(team);
		_cmd2ModelAdpt.joinChatRoom(team);
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		_cmd2ModelAdpt = (MyUserCmd2ModelAdapter) cmd2ModelAdpt;
	}

}
