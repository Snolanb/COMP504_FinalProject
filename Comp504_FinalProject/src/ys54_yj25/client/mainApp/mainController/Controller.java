package ys54_yj25.client.mainApp.mainController;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.Collection;

import common.IChatRoom;
import common.IUser;
import ys54_yj25.client.mainApp.mainModel.IMainModelToMainViewAdapter;
import ys54_yj25.client.mainApp.mainModel.MainModel;
import ys54_yj25.client.mainApp.mainView.ChatRoomMainFrame;
import ys54_yj25.client.mainApp.mainView.IMainViewToMainModelAdapter;
import ys54_yj25.client.team.teamController.MiniController;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;

/**
 * The main controller of the chat app
 * 
 * @author yuejiang
 *
 */
public class Controller {

	/**
	 * Import the client GUI
	 */
	private ChatRoomMainFrame<IUser, IChatRoom> view;

	/**
	 * Import the client model
	 */
	private MainModel model;

	/**
	 * Controller constructor builds the system
	 */
	public Controller() {
		/**
		 * Main model of the chat app
		 */
		model = new MainModel(new IMainModelToMainViewAdapter() {

			@Override
			public void appendInfo(String textInfo) {
				view.appendInfo(textInfo);
			}

			@Override
			public void displayIPAddress(String localAddress) {
				view.displayIPAddress(localAddress);
			}

			@SuppressWarnings("unchecked")
			@Override
			public ChatRoomMiniModel createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom) {
				@SuppressWarnings("rawtypes")
				MiniController miniController = new MiniController(chatRoom, localUserStub, view, model);
				miniController.start();
				return miniController.getChatRoomMiniModel();
			}

			@Override
			public void deleteChatRoomMVC(IChatRoom chatRoom) {
				// todo
				view.deleteChatRoomMVC(chatRoom);

			}

			@Override
			public void pullChatRooms(Collection<IChatRoom> chatRooms) {
				// todo
				view.pullChatRooms(chatRooms);

			}

			@Override
			public void showConnectUsers(IUser localUser) {
				// todo
				view.pullConnectUsers(localUser);

			}

			@Override
			public void chooseChatRoom(IChatRoom chatRoom) {
				view.chooseChatRoom(chatRoom);

			}

		});

		/**
		 * Main view of this chat app
		 */
		view = new ChatRoomMainFrame<>(new IMainViewToMainModelAdapter<IUser, IChatRoom>() {

			@Override
			public void startAndCreateUser(String userName) {
				model.start();
				try {
					model.createUser(userName);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void createChatRoom(String chatRoomName) {
				try {
					model.createChatRoom(chatRoomName);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void connectToRemote(String remoteIpAddress) {
				model.connectToIP(remoteIpAddress);
			}

			@Override
			public void exitAll() {
				model.stop();
			}

			@Override
			public void requestChatRooms(IUser user) {
				model.requestChatRoom(user);
			}

			@Override
			public void connectToChatRoom(IChatRoom chatRoom) {
				try {
					model.joinChatRoom(chatRoom);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Start the system
	 */
	public void start() {
		model.start(); // better to start the model first but not always
		view.start();
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            Arguments given by the system or command line.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { // Java specs say that the system must be constructed on the GUI event
												// thread.
			public void run() {
				try {
					Controller controller = new Controller();
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
