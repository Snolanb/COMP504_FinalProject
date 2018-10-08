package ys54_yj25.object.receiver;

import java.rmi.RemoteException;

import common.DataPacketCR;
import common.DataPacketCRAlgoCmd;
import common.ICRCmd2ModelAdapter;
import ys54_yj25.client.mainApp.mainModel.ICRCmdToChatRoomMiniModel;
import ys54_yj25.commands.chatRoom.StringType;

public class MyStringDataPacketAlgoCmd extends DataPacketCRAlgoCmd<StringType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -688113172161259756L;

   private transient ICRCmd2ModelAdapter cmdToChatRoom;
	
	public MyStringDataPacketAlgoCmd(ICRCmdToChatRoomMiniModel cmdToChatRoom) {
		this.cmdToChatRoom = cmdToChatRoom;
	}
	
	@Override
	public String apply(Class<?> index, DataPacketCR<StringType> host, String... params) {
		String text = host.getData().getStringInfo();
		try {
			System.out.println(cmdToChatRoom);
			cmdToChatRoom.appendMsg(host.getSender().getUserStub().getName() + ": " + text,
					host.getSender().getUserStub().getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	@Override
	public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
		this.cmdToChatRoom = cmd2ModelAdpt;	
	}

}
