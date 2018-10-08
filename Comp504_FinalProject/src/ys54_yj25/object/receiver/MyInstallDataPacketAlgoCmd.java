package ys54_yj25.object.receiver;

import java.util.List;
import java.util.Map;

import common.DataPacketCR;
import common.DataPacketCRAlgoCmd;
import common.ICRCmd2ModelAdapter;
import common.datatype.chatroom.ICRInstallCmdType;
import provided.datapacket.DataPacketAlgo;
import ys54_yj25.client.mainApp.mainModel.ICRCmdToChatRoomMiniModel;

public class MyInstallDataPacketAlgoCmd extends DataPacketCRAlgoCmd<ICRInstallCmdType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7201634971264264452L;

	private DataPacketAlgo<String, String> algoCommands;
	private transient ICRCmdToChatRoomMiniModel cmdToChatRoom;
	protected Map<Class<?>, List<DataPacketCR<?>>> messageCache;
	
	public MyInstallDataPacketAlgoCmd(DataPacketAlgo<String, String> algoCommands, 
											ICRCmdToChatRoomMiniModel cmdToChatRoom,
											Map<Class<?>, List<DataPacketCR<?>>> messageCache
											) {
		this.algoCommands = algoCommands;
		this.cmdToChatRoom = cmdToChatRoom;
		this.messageCache = messageCache;
		System.out.println(messageCache);
	}
	
	@Override
	public String apply(Class<?> index, DataPacketCR<ICRInstallCmdType> host, String... params) {
		//Class<?> cmdIndex = host.getData().getClass();
		Class<?> cmdIndex = host.getData().getCmdId();
		DataPacketCRAlgoCmd<?> cmd = host.getData().getCmd();
//		if (algoCommands.getCmd(cmdIndex) != null) {
//			
//		}
		System.out.println("Installing cmd...");
		System.out.println("The adapter is: " + cmdToChatRoom);					
		cmd.setCmd2ModelAdpt(cmdToChatRoom);
		algoCommands.setCmd(cmdIndex, cmd);
		System.out.println(messageCache);
		for (DataPacketCR<?> packet : messageCache.get(cmdIndex)) {
			System.out.println(packet);
			packet.execute(algoCommands);
		}
			
		messageCache.remove(cmdIndex);			
		return "install?????? ---> in install cmd";
	}

	@Override
	public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
