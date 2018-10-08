package ys54_yj25.client.team.teamModel;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import common.DataPacketCR;
import common.IChatRoom;
import common.IReceiver;
import common.datatype.chatroom.IAddReceiverType;
import common.datatype.chatroom.IRemoveReceiverType;
import ys54_yj25.commands.chatRoom.AddReceiverStubType;
import ys54_yj25.commands.chatRoom.ImageIconType;
import ys54_yj25.commands.chatRoom.RemoveReceiverStubType;
import ys54_yj25.commands.chatRoom.StringType;

/**
 * Chat room mini model
 * 
 * @author yuejiang
 *
 */
public class ChatRoomMiniModel {

	private IChatRoom chatRoom;
	private IReceiver receiver;

	private IMiniModelToMiniViewAdapter miniModelToMiniViewAdapter;
	private IMiniModeToMainViewAdapter miniModeToMainViewAdapter;

	private IMiniModeToMainModelAdapter miniModeToMainModelAdapter;

	public ChatRoomMiniModel(IChatRoom chatRoom, IReceiver receiver,
			IMiniModelToMiniViewAdapter miniModelToMiniViewAdapter,
			IMiniModeToMainModelAdapter miniModeToMainViewAdapter) {
		super();
		this.chatRoom = chatRoom;
		this.receiver = receiver;
		this.miniModelToMiniViewAdapter = miniModelToMiniViewAdapter;
		this.miniModeToMainModelAdapter = miniModeToMainViewAdapter;
	}

	public void start() {
		showUsers();
	}

	public void showUsers() {
		// TODO show current user first
		// List<IUser> userList = new ArrayList<>();
		// try {
		//// userList.add(receiver.getUserStub());
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// miniModelToMiniViewAdapter.showUserList(userList);
		List<IReceiver> receiversList = new ArrayList<>();
		for (IReceiver receiver : chatRoom.getIReceiverStubs()) {
			receiversList.add(receiver);
		}
		miniModelToMiniViewAdapter.showUserList(receiversList);
	}

	/**
	 * Send text/string
	 * 
	 * @param msg
	 *            the string to send
	 */
	public void setMessage(String msg) {
		StringType newMsg = new StringType(msg);

		DataPacketCR<StringType> data = new DataPacketCR<StringType>(StringType.class, newMsg, receiver);
		chatRoom.sendPacket(data);
		System.out.println("Send data");

		System.out.println("The current chatroom: " + chatRoom.getName());
		for (IReceiver receiver : chatRoom.getIReceiverStubs()) {
			try {
				System.out.println(receiver.getUserStub().getName() + "--->");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send Image
	 * 
	 * @param image
	 *            the image to send
	 */
	public void sendImage(ImageIcon image) {
		ImageIconType newImage = new ImageIconType(image);
		DataPacketCR<ImageIconType> data = new DataPacketCR<ImageIconType>(ImageIconType.class, newImage, receiver);
		chatRoom.sendPacket(data);
		System.out.println("Send Image == ");
	}

	/**
	 * Send Files
	 * 
	 * @param file
	 *            the file to send
	 */
	// public void sendFile(File file) {
	// DataPacketCR<File> data = new DataPacketCR<File>(File.class, file, receiver);
	// chatRoom.sendPacket(data);
	// System.out.println("Send Image == ");
	// }

	/**
	 * Add receiver to the chat room
	 * 
	 * @param receiver
	 *            the receiver to add
	 */
	public void addReceiver(IReceiver receiver) {
		AddReceiverStubType a = new AddReceiverStubType(receiver);
		DataPacketCR<IAddReceiverType> data = new DataPacketCR<IAddReceiverType>(IAddReceiverType.class, a, receiver);
		chatRoom.sendPacket(data);
		// chatRoom.addIReceiverStubLocally(receiver);
		System.out.println("add receiver success in local");
	}

	/**
	 * Remove receiver to the chat room
	 * 
	 * @param receiver
	 *            the receiver to remove
	 */
	public void removeReceiver(IReceiver receiver) {
		RemoveReceiverStubType a = new RemoveReceiverStubType(receiver);
		DataPacketCR<IRemoveReceiverType> data = new DataPacketCR<IRemoveReceiverType>(IRemoveReceiverType.class, a,
				receiver);
		chatRoom.sendPacket(data);
		System.out.println("remove receiver success in local");
	}

	public void exitChatRoom(IChatRoom chatRoom) {
		removeReceiver(receiver);
		miniModeToMainModelAdapter.exitChatRoom(chatRoom);
		// miniModeToMainViewAdapter.exitChatRoom(chatRoom);
	}

	public IReceiver getRecevier() {
		return receiver;
	}

	public IChatRoom getChatRoom() {
		return chatRoom;
	}
}
