package ys54_yj25.server.serverController;

import java.awt.EventQueue;
import java.util.Collection;

import common.IChatRoom;
import common.IUser;
import ys54_yj25.client.team.teamController.MiniControllerServer;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;
import ys54_yj25.server.serverModel.IServerModel2ViewAdapter;
import ys54_yj25.server.serverModel.ServerModel;
import ys54_yj25.server.view.IServerView2ModelAdapter;
import ys54_yj25.server.view.ServerView;



public class serverController {
	
	/**
	 * Import the server GUI
	 */
	private ServerView<IChatRoom> serverView;
	
	/**
	 * Import the server Model
	 */
	private ServerModel serverModel;

	public serverController() {
		
		/**
		 * View of this server
		 */
		serverView = new ServerView<>(new IServerView2ModelAdapter() {
			
			@Override
			public void createChatRoom(String chatRoomName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectToRemote(String remoteIpAddress) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String broadcastMsg(String msg) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void startGame() {
				// TODO Auto-generated method stub
				System.out.println("Start installing game...");
				serverModel.installGame();
			}
		});
		/**
		 *  Main model of the server 
		 */
		serverModel = new ServerModel(new IServerModel2ViewAdapter() {
			
			@Override
			public void showConnectUsers(IUser localUser) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void pullChatRooms(Collection<IChatRoom> chatRooms) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void displayIPAddress(String localAddress) {
				//System.out.println("是否打出ip address？");
				serverView.displayIPAddress(localAddress);
				
			}
			
			@Override
			public void deleteChatRoomMVC(IChatRoom chatRoom) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public ChatRoomMiniModel createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				MiniControllerServer miniControllerServer = new MiniControllerServer(chatRoom, localUserStub, serverView, serverModel);
				miniControllerServer.start();
				return miniControllerServer.getChatRoomMiniModel();
			}
			
			@Override
			public void chooseChatRoom(IChatRoom chatRoom) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void appendInfo(String textInfo) {
				serverView.appendInfo(textInfo);
			}
		});
		

	}

	
	/**
	 * Start the server
	 */
	public void start() {
		serverModel.start();
		serverView.start();
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
					serverController controller = new serverController();
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
