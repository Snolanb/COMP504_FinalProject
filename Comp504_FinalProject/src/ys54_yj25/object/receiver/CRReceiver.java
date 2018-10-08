package ys54_yj25.object.receiver;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.DataPacketCR;
import common.DataPacketCRAlgoCmd;
import common.ICRCmd2ModelAdapter;
import common.ICRMessageType;
import common.IChatRoom;
import common.IReceiver;
import common.IUser;
import common.datatype.IRequestCmdType;
import common.datatype.chatroom.IAddReceiverType;
import common.datatype.chatroom.ICRFailureStatusType;
import common.datatype.chatroom.ICRInstallCmdType;
import common.datatype.chatroom.IRemoveReceiverType;
import provided.datapacket.DataPacketAlgo;
import ys54_yj25.client.mainApp.mainModel.ICRCmdToChatRoomMiniModel;
import ys54_yj25.commands.chatRoom.ImageIconType;
import ys54_yj25.commands.chatRoom.RequestCmdType;
import ys54_yj25.commands.chatRoom.StringType;

public class CRReceiver implements IReceiver {

	private final IUser user;

	private IReceiver receiverStub;

	private final UUID uuId;

	private ICRCmdToChatRoomMiniModel cmdToChatRoom;

	protected Map<Class<?>, List<DataPacketCR<?>>> messageCache = new HashMap<Class<?>, List<DataPacketCR<?>>>();;

	/**
	 * Default command
	 */
	private DataPacketCRAlgoCmd<ICRMessageType> cmdDefault = new DataPacketCRAlgoCmd<ICRMessageType>() {

		/**
		 * SerialVersionUID
		 */
		private static final long serialVersionUID = 4830273799283274132L;

		@Override
		public String apply(Class<?> index, DataPacketCR<ICRMessageType> host, String... params) {
			try {
				System.out.println("Default? success?");

				System.out.println("host.getData().getClass()" + host.getData().getClass());

				Object hostData = host.getData();
				String remoteName = "unknown remote host name";
				remoteName = host.getSender().getUserStub().getName();
				List<DataPacketCR<?>> cache = messageCache.getOrDefault(index, new LinkedList<DataPacketCR<?>>());
				cache.add(host);
				messageCache.put(index, cache);
				System.out.print(messageCache);

				IRequestCmdType requestCmdType = new RequestCmdType(index);

				// host.getSender().receive(new
				// DataPacketCR<IRequestCmdType>(IRequestCmdType.class, new RequestCmdType(id),
				// receiverStub));
				// host.getSender().receive(
				// new DataPacketCR<IRequestCmdType>(IRequestCmdType.class, requestCmd,
				// host.getSender()));

				host.getSender().receive(
						new DataPacketCR<IRequestCmdType>(IRequestCmdType.class, requestCmdType, receiverStub));

				// host.getSender().receive(new
				// DataPacketCR<IRequestCmdType>(IRequestCmdType.class, requestCmd,
				// receiverStub));

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub

		}

	};

	private DataPacketAlgo<String, String> algoCommands = new DataPacketAlgo<String, String>(cmdDefault);

	/**
	 * Request commands
	 */
	private DataPacketCRAlgoCmd<IRequestCmdType> requestCmd;
	private DataPacketCRAlgoCmd<StringType> displayTextCmd;
	/**
	 * Display Image
	 */
	private DataPacketCRAlgoCmd<ImageIconType> displayImage;

	private DataPacketCRAlgoCmd<ICRInstallCmdType> installCmd;


	public CRReceiver(final IUser user, ICRCmdToChatRoomMiniModel cmdToChatRoom) {
		this.user = user;
		this.cmdToChatRoom = cmdToChatRoom;
		uuId = UUID.randomUUID();
		// messageCache = new HashMap<Class<?>, List<DataPacketCR<?>>>();

		//requestCmd = new MyRequestDataPacketAlgoCmd(receiverStub, algoCommands);
		/**
		 * 测试 将receiverStub改成receiver测试
		 */
		requestCmd = new MyRequestDataPacketAlgoCmd(CRReceiver.this, algoCommands);
		
		
		displayTextCmd = new MyStringDataPacketAlgoCmd(cmdToChatRoom);
		displayImage = new MyImageDataPacketAlgoCmd(cmdToChatRoom);
		// displayImage = new MyGameMainFrameDataPacketAlgoCmd(cmdToChatRoom);

		installCmd = new MyInstallDataPacketAlgoCmd(algoCommands, cmdToChatRoom, messageCache);

		setCmd();
	}

	/**
	 * Add Receiver
	 */
	private DataPacketCRAlgoCmd<IAddReceiverType> addReceiverStub = new DataPacketCRAlgoCmd<IAddReceiverType>() {

		/**
		 * SerialVersionUID
		 */
		private static final long serialVersionUID = -8861327494609903002L;

		@Override
		public String apply(Class<?> index, DataPacketCR<IAddReceiverType> host, String... params) {
			IReceiver receiver = host.getData().getReceiverStub();
			// Receiver.this.cmdToChatRoom.addReceiver(receiver);
			CRReceiver.this.cmdToChatRoom.getChatRoom().addIReceiverStub(receiver);

			// update user list
			CRReceiver.this.cmdToChatRoom.updateUserList();

			// Receiver.this.cmdToChatRoom.addReceiver(host.getData().getReceiverStub());
			return "The new receiver: " + host.getData().getReceiverStub() + " enter the chat room!" + "\n";
		}

		@Override
		public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * Remove Receiver
	 */
	private DataPacketCRAlgoCmd<IRemoveReceiverType> removeReceiverStub = new DataPacketCRAlgoCmd<IRemoveReceiverType>() {

		/**
		 * SerialVersionUID
		 */
		private static final long serialVersionUID = -7327569260163438316L;

		@Override
		public String apply(Class<?> index, DataPacketCR<IRemoveReceiverType> host, String... params) {
			IReceiver receiver = host.getSender();
			try {
				System.out.println("Remove Receiver ==> the reciver is : " + receiver.getUserStub().getName());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CRReceiver.this.cmdToChatRoom.getChatRoom().removeIReceiverStub(receiver);
			// update user list
			CRReceiver.this.cmdToChatRoom.updateUserList();

			return "The recevier: " + host.getSender() + " leaving" + "\n";
		}

		@Override
		public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub
		}
	};

	// private DataPacketCRAlgoCmd<IRequestCmdType> requestCmd = new
	// DataPacketCRAlgoCmd<IRequestCmdType>() {
	//
	// /**
	// * SerialVersionUID
	// */
	// private static final long serialVersionUID = -1305473253934150856L;
	//
	// @Override
	// public String apply(Class<?> index, DataPacketCR<IRequestCmdType> host,
	// String... params) {
	// System.out.println("Request? success?");
	//
	// Class<?> cmdIndex = host.getData().getCmdId();
	// System.out.println("The cmdIndex is " + cmdIndex);
	//// a<?> cmd = (DataPacketCRAlgoCmd<?>) commands.getCmd(cmdIndex);
	//// DataPacketCRAlgoCmd<?> cmd = displayImage;
	//
	// IExtVisitorCmd<String, Class<?>, String, ADataPacket> extVisitorCmd =
	// algoCommands.getCmd(cmdIndex);
	//
	// System.out.println("The cmd is " + extVisitorCmd);
	//
	// if (extVisitorCmd == null) {
	// System.out.println("没有? success?");
	// try {
	// host.getSender().receive(new
	// DataPacketCR<ICRFailureStatusType>(ICRFailureStatusType.class,
	// new FailureStatusCmdType<>(host, host.getData()), receiverStub));
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	//// // DataPacketAlgoCmd<?> cmd = (DataPacketAlgoCmd<?>)
	// commands.getCmd(cmdIndex);
	//// System.out.println("start install? success?");
	//// System.out.println("ReceiverStub: " + receiverStub);
	//// try {
	////// DataPacketCR<ICRInstallCmdType> dataPacketCR = new
	// DataPacketCR<>(ICRInstallCmdType.class,
	////// new InstallCmdType(cmd, cmdIndex), receiverStub);
	////
	//// IReceiver sender = host.getSender();
	//// System.out.println("Sender is " + sender);
	////// DataPacketCR<StringType> dataPacketCR= new
	// DataPacketCR<>(StringType.class, new StringType("HHHHHHHHHHH"),
	// receiverStub);
	//// DataPacketCR<InstallCmdType> dataPacketCR = new
	// DataPacketCR<>(InstallCmdType.class,
	//// new InstallCmdType(displayImage, cmdIndex), receiverStub);
	////
	//// sender.receive(dataPacketCR);
	//// } catch (RemoteException e) {
	//// // TODO Auto-generated catch block
	//// e.printStackTrace();
	//// }
	// DataPacketCRAlgoCmd<?> cmd = (DataPacketCRAlgoCmd<?>)extVisitorCmd;
	// try {
	// host.getSender().receive(new
	// DataPacketCR<ICRInstallCmdType>(ICRInstallCmdType.class, new
	// InstallCmdType(cmd, cmdIndex), receiverStub));
	// } catch (RemoteException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// return null;
	// }
	//
	// @Override
	// public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
	// // TODO Auto-generated method stub
	//
	// }
	// };

	/**
	 * Failure Status Command
	 */
	private DataPacketCRAlgoCmd<ICRFailureStatusType> cmdFailureStatus = new DataPacketCRAlgoCmd<ICRFailureStatusType>() {

		/**
		 * SerialVersionUID
		 */
		private static final long serialVersionUID = 3854114455705660335L;

		@Override
		public String apply(Class<?> index, DataPacketCR<ICRFailureStatusType> host, String... params) {
			try {
				CRReceiver.this.cmdToChatRoom.appendMsg(
						"Failure Status: " + host.getData().getFailureInfo() + "\n" + "Please try again.",
						host.getSender().getUserStub().getName());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return "Failure Status: " + host.getData().getFailureInfo() + "\n";
			return "";
		}

		@Override
		public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * Install Command
	 */

	// private DataPacketCRAlgoCmd<ICRInstallCmdType> InstallCmd = new
	// MyInstallDataPacketAlgoCmd(algoCommands,
	// cmdToChatRoom, messageCache);
	// private DataPacketCRAlgoCmd<ICRInstallCmdType> InstallCmd = new
	// DataPacketCRAlgoCmd<ICRInstallCmdType>() {
	//
	// /**
	// * SerialVersionUID
	// */
	// private static final long serialVersionUID = -2129526352857134432L;
	//
	// @Override
	// public String apply(Class<?> index, DataPacketCR<ICRInstallCmdType> host,
	// String... params) {
	// // Class<?> cmdIndex = host.getData().getClass();
	// Class<?> cmdIndex = host.getData().getCmdId();
	// DataPacketCRAlgoCmd<?> cmd = host.getData().getCmd();
	// // if (algoCommands.getCmd(cmdIndex) != null) {
	// //
	// // }
	// System.out.println("Installing cmd...");
	// System.out.println("The adapter is: " + cmdToChatRoom);
	// cmd.setCmd2ModelAdpt(cmdToChatRoom);
	// algoCommands.setCmd(cmdIndex, cmd);
	// System.out.println(messageCache);
	// for (DataPacketCR<?> packet : messageCache.get(cmdIndex))
	// packet.execute(algoCommands);
	// messageCache.remove(cmdIndex);
	// return "install?????? ---> in install cmd";
	// }
	//
	// @Override
	// public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
	// // TODO Auto-generated method stub
	// }
	// };

	/**
	 * Display Text
	 */
	// private DataPacketCRAlgoCmd<StringType> displayTextCmd = new
	// DataPacketCRAlgoCmd<StringType>() {
	//
	// /**
	// * SerialVersionUID
	// */
	// private static final long serialVersionUID = 4624100548397054255L;
	//
	// @Override
	// public String apply(Class<?> index, DataPacketCR<StringType> host, String...
	// params) {
	// String text = host.getData().getStringInfo();
	// try {
	// cmdToChatRoom.appendMsg(host.getSender().getUserStub().getName() + ": " +
	// text,
	// host.getSender().getUserStub().getName());
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return text;
	// }
	//
	// @Override
	// public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
	// // TODO Auto-generated method stub
	//
	// }
	// };

	// private DataPacketCRAlgoCmd<ImageIconType> displayImage = new
	// DataPacketCRAlgoCmd<ImageIconType>() {
	//
	// /**
	// * SerialVersionUID
	// */
	// private static final long serialVersionUID = 7069587843916710321L;
	//
	// @Override
	// public String apply(Class<?> index, DataPacketCR<ImageIconType> host,
	// String... params) {
	// try {
	// cmdToChatRoom.appendMsg(host.getSender().getUserStub().getName() + ": ",
	// host.getSender().getUserStub().getName());
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// ImageIcon imageIcon = host.getData().getImageIconInfo();
	// cmdToChatRoom.buildNonScrollableComponent(new IComponentFactory() {
	//
	// @Override
	// public Component makeComponent() {
	// int height = Math.min(imageIcon.getIconHeight(), 200);
	// double ratio = (double) imageIcon.getIconWidth() / imageIcon.getIconHeight();
	// int width = (int) (height * ratio);
	// Image image = imageIcon.getImage();
	// ImageIcon resizedImage = new ImageIcon(image.getScaledInstance(width, height,
	// Image.SCALE_DEFAULT));
	// return new JLabel(resizedImage);
	// }
	// }, imageIcon.toString());
	// return imageIcon.toString();
	// }
	//
	// @Override
	// public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
	// // TODO Auto-generated method stub
	//
	// }
	// };

	public void setCmd() {
		algoCommands.setCmd(ICRMessageType.class, cmdDefault);
		algoCommands.setCmd(IAddReceiverType.class, addReceiverStub);
		algoCommands.setCmd(IRemoveReceiverType.class, removeReceiverStub);
		algoCommands.setCmd(ICRFailureStatusType.class, cmdFailureStatus);
		// commands.setCmd(IRequestCmdType.class, requestCmd);
		algoCommands.setCmd(IRequestCmdType.class, requestCmd);
		algoCommands.setCmd(ICRInstallCmdType.class, installCmd);
		algoCommands.setCmd(StringType.class, displayTextCmd);
		algoCommands.setCmd(ImageIconType.class, displayImage);
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuId;
	}

	@Override
	public <T extends ICRMessageType> void receive(DataPacketCR<T> data) throws RemoteException {
		System.out.println("Inside receive");
		data.execute(algoCommands);

	}

	@Override
	public IUser getUserStub() throws RemoteException {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IChatRoom))
			return false;
		IChatRoom that = (IChatRoom) obj;
		try {
			return this.getUUID().equals(that.getUUID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return uuId.hashCode();
	}

	public void setStub(IReceiver receiverStub) {
		this.receiverStub = receiverStub;
	}

	public <T extends ICRMessageType> void sendPacket(DataPacketCR<T> data, IChatRoom room) {
		room.sendPacket(data);
	}

}
