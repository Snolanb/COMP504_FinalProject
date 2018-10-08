package ys54_yj25.object.receiver;

import java.rmi.RemoteException;

import common.DataPacketCR;
import common.DataPacketCRAlgoCmd;
import common.ICRCmd2ModelAdapter;
import common.IReceiver;
import common.datatype.IRequestCmdType;
import common.datatype.chatroom.ICRFailureStatusType;
import common.datatype.chatroom.ICRInstallCmdType;
import provided.datapacket.ADataPacket;
import provided.datapacket.DataPacketAlgo;
import provided.extvisitor.IExtVisitorCmd;
import ys54_yj25.commands.chatRoom.FailureStatusCmdType;
import ys54_yj25.commands.chatRoom.InstallCmdType;

public class MyRequestDataPacketAlgoCmd extends DataPacketCRAlgoCmd<IRequestCmdType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3929126593766413309L;

	private IReceiver receiverStub;
	private DataPacketAlgo<String, String> algoCommands;

	public MyRequestDataPacketAlgoCmd(IReceiver receiverStub, DataPacketAlgo<String, String> algoCommands) {
		this.receiverStub = receiverStub;
		this.algoCommands = algoCommands;
	}

	@Override
	public String apply(Class<?> index, DataPacketCR<IRequestCmdType> host, String... params) {
		System.out.println("Request? success?");

		Class<?> cmdIndex = host.getData().getCmdId();
		System.out.println("The cmdIndex is " + cmdIndex);
//		a<?> cmd = (DataPacketCRAlgoCmd<?>) commands.getCmd(cmdIndex);
//		DataPacketCRAlgoCmd<?> cmd = displayImage;
		
		IExtVisitorCmd<String, Class<?>, String, ADataPacket> extVisitorCmd = algoCommands.getCmd(cmdIndex);
		
		System.out.println("The cmd is " + extVisitorCmd);

		if (extVisitorCmd == null) {
			System.out.println("没有? success?");
			try {
				host.getSender().receive(new DataPacketCR<ICRFailureStatusType>(ICRFailureStatusType.class,
						new FailureStatusCmdType<>(host, host.getData()), receiverStub));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
//			// DataPacketAlgoCmd<?> cmd = (DataPacketAlgoCmd<?>) commands.getCmd(cmdIndex);
//			System.out.println("start install? success?");
//			System.out.println("ReceiverStub: " + receiverStub);
//			try {
////				DataPacketCR<ICRInstallCmdType> dataPacketCR = new DataPacketCR<>(ICRInstallCmdType.class,
////						new InstallCmdType(cmd, cmdIndex), receiverStub);
//
//				IReceiver sender = host.getSender();
//				System.out.println("Sender is " + sender);
////				DataPacketCR<StringType> dataPacketCR= new DataPacketCR<>(StringType.class, new StringType("HHHHHHHHHHH"), receiverStub);
//				DataPacketCR<InstallCmdType> dataPacketCR = new DataPacketCR<>(InstallCmdType.class,
//						new InstallCmdType(displayImage, cmdIndex), receiverStub);
//				
//				sender.receive(dataPacketCR);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			DataPacketCRAlgoCmd<?> cmd = (DataPacketCRAlgoCmd<?>)extVisitorCmd;
			//ICRInstallCmdType installCmdType = new InstallCmdType(cmd, cmdIndex);
			ICRInstallCmdType installCmdType = new InstallCmdType(cmd, cmdIndex);
			System.out.println("待传输的安装cmd is " + cmd);
			System.out.println("待传输的安装cmd 的id是" + cmdIndex);
			
			System.out.println("待传输的安装数据是 The installCmdTYPE is :" + installCmdType);
			
			try {
				DataPacketCR<ICRInstallCmdType> dataPacketCR = new DataPacketCR<ICRInstallCmdType>(ICRInstallCmdType.class, installCmdType, receiverStub);
				System.out.println("dataPacketCR is " + dataPacketCR);
				System.out.println("dataPacketCR data" + dataPacketCR.getData());
				System.out.println("sender is " + receiverStub);
				System.out.println("host.sender is " + host.getSender());
				
				
//				StringType data = new StringType("看看能不能发别的");
//				DataPacketCR<StringType> dataPacketCR = new DataPacketCR<StringType>(StringType.class, data, receiverStub);		
				host.getSender().receive(dataPacketCR);
				
				//host.getSender().receive(dataPacketCR);
				System.out.println("！！发送安装数据成功！！");
			} catch (RemoteException e) {
				e.printStackTrace();
			}				
		}

		return "Finish Install";
	}

	@Override
	public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
