package ys54_yj25.commands.user;

import java.rmi.RemoteException;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IUser;
import common.IUserCmd2ModelAdapter;
import common.datatype.IRequestCmdType;
import common.datatype.user.IUserFailureStatusType;
import common.datatype.user.IUserInstallCmdType;
import provided.datapacket.DataPacketAlgo;


public class RequestCmd extends DataPacketUserAlgoCmd<IRequestCmdType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1250757243877427056L;

	private IUser userStub;
	private DataPacketAlgo<String, String> commands;
	
	public RequestCmd(IUser userStub, DataPacketAlgo<String, String> commands) {
		this.userStub = userStub;
		this.commands = commands;
	}
	
	@Override
	public String apply(Class<?> index, DataPacketUser<IRequestCmdType> host, String... params) {
		System.out.println("Request? success?");
		System.out.println("Command requested!" + "from " + host.getSender());
		
		Class<?> cmdIndex = host.getData().getCmdId();
		System.out.println("The cmdIndex is " + cmdIndex);
		DataPacketUserAlgoCmd<?> cmd = (DataPacketUserAlgoCmd<?>) commands.getCmd(cmdIndex);
		System.out.println("The cmd is " + cmd);

		if (cmd == null) {
			System.out.println("loser? success?");
			try {
				host.getSender().receive(new DataPacketUser<IUserFailureStatusType>(IUserFailureStatusType.class, 
						new UserFailureStatusCmdType<>(host, host.getData()), userStub));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// DataPacketAlgoCmd<?> cmd = (DataPacketAlgoCmd<?>) commands.getCmd(cmdIndex);
			System.out.println("start install? from user " + userStub);
			try {
				host.getSender().receive(new DataPacketUser<IUserInstallCmdType>(IUserInstallCmdType.class,
						new UserInstallCmdType(cmd, cmdIndex), userStub));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
