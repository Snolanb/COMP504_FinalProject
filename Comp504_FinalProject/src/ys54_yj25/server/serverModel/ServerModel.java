package ys54_yj25.server.serverModel;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import common.DataPacketCR;
import common.DataPacketUser;
import common.ICRCmd2ModelAdapter;
import common.IChatRoom;
import common.IComponentFactory;
import common.IReceiver;
import common.IUser;
import common.IUserMessageType;
import common.datatype.user.IInvitationType;
import provided.mixedData.MixedDataKey;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;
import ys54_yj25.commands.chatRoom.StringType;
import ys54_yj25.commands.user.InvitationType;
import ys54_yj25.object.receiver.CRReceiver;
import ys54_yj25.object.team.ChatRoom;
import ys54_yj25.object.user.User;
import ys54_yj25.server.game.gameServer.Player;
import ys54_yj25.server.game.gameServer.Team;

public class ServerModel {

	public static final int BOUND_PORT = 2100;
	
	public static final int SERVER_PORT = 2106;
	
	public static final UUID uuid = UUID.randomUUID();
	
	private IUser user;
	
	private IUser localUserStub;
	
	private IChatRoom lobby;
	
	private IReceiver lobbyReceiver;
	
	private Map<Player, Team> teams;
	
	private ChatRoomMiniModel chatRoomMiniModel;
	
	private Registry registry;
	
	private String ipAddress;
	
	private IServerModel2ViewAdapter _model2ViewAdpt = IServerModel2ViewAdapter.NULL_OBJECT;
	
	private MyUserCmd2ModelAdapter _userCmd2ModelAdpt = new MyUserCmd2ModelAdapter() {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void appendMsg(String text, String name) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void buildScrollableComponent(IComponentFactory fac, String label) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void buildNonScrollableComponent(IComponentFactory fac, String label) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public <T> T put(MixedDataKey<T> key, T value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T get(MixedDataKey<T> key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends IUserMessageType> void sendTo(IUser target, Class<T> id, T data) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void joinChatRoom(IChatRoom chatRoom) {
//			ServerModel.this.joinChatRoom(chatRoom);
		}
		
	};

	
	private ICRCmd2ModelAdapter _crCmd2ModelAdpt;
	
	private Consumer<String> outputCmd;
	
	private RMIUtils rmiUtils;
	
	public ServerModel(IServerModel2ViewAdapter adpt) {
		this._model2ViewAdpt = adpt;
		this.outputCmd = new Consumer<String>() {

			@Override
			public void accept(String t) {
				ServerModel.this._model2ViewAdpt.appendInfo(String.valueOf(t) + "\n");
			}
		};
		this.rmiUtils = new RMIUtils(outputCmd);
		
		
		try {
			this.ipAddress = rmiUtils.getLocalAddress();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		/**
		 * Start server
		 */
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);
		registry = rmiUtils.getLocalRegistry();
		try {
			_model2ViewAdpt.displayIPAddress(rmiUtils.getLocalAddress());
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**
		 * Create server's user
		 */
		try {
			creatUser();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail to create server user.");
			e.printStackTrace();
		}
		
		/**
		 * Create the lobby room
		 */
		try {
			lobby = new ChatRoom("Lobby", user.getUUID());
			joinLobby();
			System.out.println("Created lobby ==> " + lobby);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail to create the lobby.");
			e.printStackTrace();
		}
		
	}
	
	private void creatUser() throws RemoteException{
		/**
		 * Create the "server user"
		 */
		try {
			this.user = new ServerUser("Server", this.ipAddress, _model2ViewAdpt, _userCmd2ModelAdpt);
			System.out.println("Creat user: " + user);
			localUserStub = (IUser) UnicastRemoteObject.exportObject(user, BOUND_PORT + new Random().nextInt(100));
			((ServerUser) this.user).setStub(localUserStub);
			System.out.println("Creat user's stub: " + localUserStub);
//			registry.rebind(IUser.BOUND_NAME + user.getUUID(), localUserStub);
			registry.rebind(IUser.BOUND_NAME, localUserStub);
			System.out.println(
					"Bind the userUUID + stub =====>" + IUser.BOUND_NAME + user.getUUID() + localUserStub);
			_model2ViewAdpt.appendInfo("Create server user: " + user + "successfully" + "\n");
			_model2ViewAdpt.appendInfo("Bind server " + localUserStub + " to port " + BOUND_PORT + (- 1) + "\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.err.println("Fail to create the server's user and its stub");
			e.printStackTrace();
		}
	}
	
	private void joinLobby() {
		/**
		 * Make and add receiver
		 */
		System.out.println("Server: Server joining the lobby \n");
		if (((ServerUser) user).setLobby(lobby)) {
			System.out.println("Add to chat room? : " + ((ServerUser) user).setLobby(lobby));
			chatRoomMiniModel = _model2ViewAdpt.createChatRoomMiniMVC(localUserStub, lobby);
			// ChatRoomMiniModel chatRoomMiniModel =
			// mainModelToMainViewAdapter.createChatRoomMiniMVC(localUserStub, chatRoom);
			lobbyReceiver = chatRoomMiniModel.getRecevier();
			System.out.println(lobbyReceiver);
			
			try {
				IReceiver receiverStub;
				receiverStub = (IReceiver) UnicastRemoteObject.exportObject(lobbyReceiver,
						SERVER_PORT + new Random().nextInt(100));
				((CRReceiver) lobbyReceiver).setStub(receiverStub);
				lobby.addIReceiverStub(receiverStub);
				_model2ViewAdpt.appendInfo("Join the chatroom " + lobby.getName() + " successfully!" + "\n");

				chatRoomMiniModel.addReceiver(receiverStub);
				System.out.println("Add mini model successful!" + "Send Add receiver cmd");
				
//				DataPacketCR<IComponentFactory> data;
//				receiverStub.receive(data);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			chatRoomMiniModel.showUsers();

			// AddReceiverStub a = new AddReceiverStub(receiverStub);
			// DataPacketChatRoom<IAddReceiverType> data = new
			// DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class, a,
			// receiverStub);
			// chatRoom.sendPacket(data);
			// System.out.println("Join ==== add receiver success in local");

		} else {
			System.out.println("fail to add to chat room? : " + ((ServerUser) user).addChatRoom(lobby));
			_model2ViewAdpt.chooseChatRoom(lobby);
			_model2ViewAdpt.appendInfo("You already joined this chatroom: " + lobby + "\n");
		}
	}
	

	private void joinChatRoom(IChatRoom chatRoom) {
		/**
		 * Make and add receiver
		 */
		if (((User) user).addChatRoom(chatRoom)) {
			System.out.println("Add to chat room? : " + ((User) user).setLobby(lobby));
			chatRoomMiniModel = _model2ViewAdpt.createChatRoomMiniMVC(localUserStub, lobby);
			// ChatRoomMiniModel chatRoomMiniModel =
			// mainModelToMainViewAdapter.createChatRoomMiniMVC(localUserStub, chatRoom);
			lobbyReceiver = chatRoomMiniModel.getRecevier();
			System.out.println(lobbyReceiver);
			
			try {
				IReceiver receiverStub;
				receiverStub = (IReceiver) UnicastRemoteObject.exportObject(lobbyReceiver,
						SERVER_PORT + new Random().nextInt(100));
				
				lobby.addIReceiverStub(receiverStub);
				_model2ViewAdpt.appendInfo("Join the chatroom " + lobby.getName() + " successfully!" + "\n");

				chatRoomMiniModel.addReceiver(receiverStub);
				
				System.out.println("Add mini model successful!" + "Send Add receiver cmd");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			chatRoomMiniModel.showUsers();

			// AddReceiverStub a = new AddReceiverStub(receiverStub);
			// DataPacketChatRoom<IAddReceiverType> data = new
			// DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class, a,
			// receiverStub);
			// chatRoom.sendPacket(data);
			// System.out.println("Join ==== add receiver success in local");

		} else {
			System.out.println("fail to add to chat room? : " + ((User) user).addChatRoom(lobby));
			_model2ViewAdpt.chooseChatRoom(lobby);
			_model2ViewAdpt.appendInfo("You already joined this chatroom: " + lobby + "\n");
		}
	}

	public void installGame() {
		System.out.println("Server Model installing game...");
		this.teams = ((ServerUser) user).installGame();
		for(Team t:teams.values()) {
			IInvitationType inviMsg = new InvitationType(t);
			System.out.println("the invitation type is: " + inviMsg);
			DataPacketUser<IInvitationType> inviData = new DataPacketUser<IInvitationType>(IInvitationType.class, inviMsg, this.localUserStub);
			try {
				t.getMPlayer().getUserStub().receive(inviData);
				t.getQPlayer().getUserStub().receive(inviData);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(String info) {
		StringType newMsg = new StringType(info);
		DataPacketCR<StringType> teamData = new DataPacketCR<StringType>(StringType.class, newMsg, lobbyReceiver); 
		lobby.sendPacket(teamData);
	}
}
