package ys54_yj25.client.team.teamController;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;

import common.ICRMessageType;
import common.IChatRoom;
import common.IComponentFactory;
import common.IReceiver;
import common.IUser;
import provided.mixedData.MixedDataKey;
import ys54_yj25.client.mainApp.mainModel.ICRCmdToChatRoomMiniModel;
import ys54_yj25.client.mainApp.mainModel.MainModel;
import ys54_yj25.client.mainApp.mainView.ChatRoomMainFrame;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;
import ys54_yj25.client.team.teamModel.IMiniModeToMainModelAdapter;
import ys54_yj25.client.team.teamModel.IMiniModelToMiniViewAdapter;
import ys54_yj25.client.team.teamView.ChatRoomMiniFrame1;
import ys54_yj25.client.team.teamView.IMiniViewToModelAdapter;
import ys54_yj25.object.receiver.CRReceiver;

/**
 * 
 * The mini controller
 * 
 * @author yuejiang
 *
 * @param <TUser>
 *            user item
 * @param <TChatRoom>
 *            chatroom item
 */
public class MiniController<TUser, TChatRoom> {

	private ChatRoomMiniFrame1<IUser> crMiniView;
	private ChatRoomMiniModel crMiniModel;
	private ChatRoomMainFrame<TUser, TChatRoom> mainView;
	private MainModel mainModel;
	private IReceiver receiver;

	public IReceiver getReceiver() {
		return receiver;
	}

	public ChatRoomMiniModel getChatRoomMiniModel() {
		return crMiniModel;
	}

	/**
	 * Start the system
	 */
	public void start() {
		crMiniModel.start(); // better to start the model first but not always
		crMiniView.start();
	}

	public MiniController(IChatRoom chatRoom, IUser user, ChatRoomMainFrame<TUser, TChatRoom> mainView,
			MainModel mainModel) {

		this.mainModel = mainModel;
		this.mainView = mainView;

		receiver = new CRReceiver(user, new ICRCmdToChatRoomMiniModel() {

			@Override
			public void buildScrollableComponent(IComponentFactory fac, String label) {
				// TODO Auto-generated method stub

			}

			@Override
			public void buildNonScrollableComponent(IComponentFactory fac, String label) {
				// TODO Auto-generated method stub
				crMiniView.buildComponent(fac, label);

			}

			@Override
			public void appendMsg(String text, String name) {
				crMiniView.appendMessage(text, name);
			}

			@Override
			public void displayImage(ImageIcon img) {
				// TODO Auto-generated method stub

			}

			@Override
			public void deleteReceiver(IReceiver receiver) {
				// TODO Auto-generated method stub

			}

			@Override
			public void addReceiver(IReceiver receiver) {
				crMiniModel.addReceiver(receiver);

			}

			@Override
			public IChatRoom getChatRoom() {
				return crMiniModel.getChatRoom();
			}

			@Override
			public void updateUserList() {
				crMiniModel.showUsers();

			}

			@Override
			public Object put(MixedDataKey key, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object get(MixedDataKey key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T extends ICRMessageType> void sendToChatRoom(Class<T> id, T data) {
				// TODO Auto-generated method stub

			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T extends ICRMessageType> void sendTo(IReceiver target, Class<T> id, T data) {
				// TODO Auto-generated method stub

			}
		});

		System.out.println("&&&&&&&---: " + receiver);

		crMiniView = new ChatRoomMiniFrame1<IUser>(chatRoom.getName(), new IMiniViewToModelAdapter() {

			@Override
			public void sendMessage(String msg) {
				crMiniModel.setMessage(msg);

			}

			// @Override
			// public void sendFile(File file) {
			// crMiniModel.sendFile(file);
			//
			// }

			@Override
			public void exitRoom() {
				// crMiniModel.exitChatRoom(chatRoom);
				// mainModel.exitChatRoom(chatRoom);
				crMiniModel.exitChatRoom(chatRoom);
			}

			@Override
			public void sendImage(ImageIcon image) {
				crMiniModel.sendImage(image);

			}

			@Override
			public void sendFile(File file) {
				// TODO Auto-generated method stub

			}
		});

		crMiniModel = new ChatRoomMiniModel(chatRoom, receiver, new IMiniModelToMiniViewAdapter() {

			@Override
			public void showUserList(List<IReceiver> receiversList) {
				String[] receiverNames = new String[receiversList.size()];
				int i = 0;
				for (IReceiver receiver : receiversList) {
					try {
						receiverNames[i++] = receiver.getUserStub().getName();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				crMiniView.showUserList(receiverNames);
			}

		}, new IMiniModeToMainModelAdapter() {

			@Override
			public void exitChatRoom(IChatRoom chatRoom) {
				// TODO Auto-generated method stub
				mainModel.exitChatRoom(chatRoom);

			}
		});

		System.out.println("crMiniView : " + crMiniView);
		System.out.println(crMiniView == null);
		mainView.addChatRoomTab(crMiniView);

	}

}
