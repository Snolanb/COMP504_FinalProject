package ys54_yj25.client.mainApp.mainModel;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import common.IChatRoom;
import common.IComponentFactory;
import common.IReceiver;
import common.IUser;
import common.IUserMessageType;
import provided.mixedData.MixedDataKey;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;
import ys54_yj25.object.receiver.CRReceiver;
import ys54_yj25.object.team.ChatRoom;
import ys54_yj25.object.user.User;
import ys54_yj25.server.serverModel.MyUserCmd2ModelAdapter;

/**
 * The main model of this chat app
 * 
 * @author yuejiang
 *
 */
public class MainModel {

	/**
	 * The regitry of this model
	 */
	private Registry registry;

	/**
	 * The local user
	 */
	private IUser localUser;

	/**
	 * Proxy object. The user's remote stub
	 */
	private IUser localUserStub;

	/**
	 * The mini model
	 */
	ChatRoomMiniModel chatRoomMiniModel;

	/**
	 * Do not allow user to create chatroom with same name
	 */
	Set<String> chatRoomNameVaildSet = new HashSet<>();

	// not sure?
	/**
	 * The bound port
	 */
	private final int BOUND_PORT = 2100;

	/**
	 * The port count
	 */
	private static int portCount = 0;

	public static final int SERVER_PORT = 2106;

	/**
	 * The adapter let main model communicate with the main view
	 */
	private IMainModelToMainViewAdapter mainModelToMainViewAdapter;
	
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
			// TODO Auto-generated method stub
			try {
				System.out.println("Being asked to join a chat room \n");
				MainModel.this.joinChatRoom(chatRoom);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};

	/**
	 * The out put commend to accept string
	 */
	private Consumer<String> outputCmd = new Consumer<String>() {

		@Override
		public void accept(String t) {
			MainModel.this.mainModelToMainViewAdapter.appendInfo(String.valueOf(t) + "\n");
		}
	};

	/**
	 * Factory for the Registry and other uses.
	 */
	IRMIUtils rmiUtils = new RMIUtils(outputCmd);

	/**
	 * The constructor of the main model
	 * 
	 * @param modelToViewAdapter
	 *            the main model to main view adapter
	 */
	public MainModel(IMainModelToMainViewAdapter modelToViewAdapter) {
		this.mainModelToMainViewAdapter = modelToViewAdapter;
	}

	/**
	 * Start the MainModel and RMI
	 */
	public void start() {
		try {
			// Start the RMI system and get the local Registry, making it if necessary.
			// rmiUtils = new RMIUtils(modelToViewAdapter.getDisplayMethod()); // initialize
			// right after model starts

			// TODO add constant to interface
			// rmiUtils.startRMI(2000 + new Random().nextInt(100));
			// rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);
			rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER + new Random().nextInt(100));
			registry = rmiUtils.getLocalRegistry();
			mainModelToMainViewAdapter.displayIPAddress(rmiUtils.getLocalAddress());
		} catch (Exception e) {
			System.err.println("Exception while intializing RMI: \n" + e);
			e.printStackTrace();
			System.exit(-1); // error exit the program.
		}
	}

	// public void createUser(String userName) throws RemoteException {
	// // create User
	// try {
	// localUser = new User(userName, rmiUtils.getLocalAddress());
	// System.out.println("Create user: " + localUser);
	// // create the stub -- "Port" To-do
	// // localUserStub = (IUser) UnicastRemoteObject.exportObject(localUser,
	// // BOUND_PORT + portCount++);
	// localUserStub = (IUser) UnicastRemoteObject.exportObject(localUser,
	// BOUND_PORT + new Random().nextInt(100));
	// System.out.println("Create user stub =====>" + localUserStub);
	// // bind the stub to user
	// registry.rebind(IUser.BOUND_NAME + localUser.getUUID(), localUserStub);
	// System.out.println(
	// "Bind the userUUID + stub =====>" + IUser.BOUND_NAME + localUser.getUUID() +
	// localUserStub);
	// mainModelToMainViewAdapter.appendInfo("Create user: " + localUser +
	// "successfully" + "\n");
	// mainModelToMainViewAdapter
	// .appendInfo("Bind user " + localUserStub + " to port " + BOUND_PORT +
	// (portCount - 1) + "\n");
	// } catch (UnknownHostException | SocketException e) {
	// System.err.println("Fail to create user and its stub");
	// e.printStackTrace();
	// }
	// }

	public void createUser(String userName) throws RemoteException {
		// create User
		try {
			localUser = new User(userName, rmiUtils.getLocalAddress(), mainModelToMainViewAdapter, _userCmd2ModelAdpt);
			System.out.println("Create user: " + localUser);
			// create the stub -- "Port" To-do
			// localUserStub = (IUser) UnicastRemoteObject.exportObject(localUser,
			// BOUND_PORT + portCount++);
			localUserStub = (IUser) UnicastRemoteObject.exportObject(localUser, BOUND_PORT + new Random().nextInt(100));
			((User)localUser).setStub(localUserStub);
			System.out.println("Create user stub =====>" + localUserStub);
			// bind the stub to user
			registry.rebind(IUser.BOUND_NAME, localUserStub);
			System.out.println(
					"Bind the userUUID + stub =====>" + IUser.BOUND_NAME + localUserStub);
			mainModelToMainViewAdapter.appendInfo("Create user: " + localUser + "successfully" + "\n");
			mainModelToMainViewAdapter
					.appendInfo("Bind user " + localUserStub + " to port " + BOUND_PORT + (portCount - 1) + "\n");

		} catch (UnknownHostException | SocketException e) {
			System.err.println("Fail to create user and its stub");
			e.printStackTrace();
		}
	}

	public void createChatRoom(String chatRoomName) throws RemoteException {
		System.out.println("Start creating chatroom");
		System.out.println(chatRoomName);
		if (chatRoomName == null || chatRoomName == "" || chatRoomNameVaildSet.contains(chatRoomName))
			return;

		else {
			chatRoomNameVaildSet.add(chatRoomName);
			IChatRoom chatRoom = new ChatRoom(chatRoomName, localUser.getUUID());

			// IReceiver receiver = new Receiver(user);
			// chatroom.addIReceiverStubLocally(receiver);
			// chatroom.addConnection(new Connection(user, chatroom));
			// user.addRoom(chatroom);

			System.out.println("Created room ==> " + chatRoom);
			joinChatRoom(chatRoom);
		}

	}

	/**
	 * Join the chat room
	 * 
	 * @param chatRoom
	 *            the chat room which user want to join
	 * @throws RemoteException
	 *             remote exception
	 */
	public void joinChatRoom(IChatRoom chatRoom) throws RemoteException {
		System.out.println("Client: Start joining the chatRoom");
		
		//test
		System.out.println("是否进入client的joinchatroom?????????");
		for (IChatRoom rm: localUser.getChatRooms()) {
			System.out.println(rm.getName() + " ---" + rm.getUUID());
		}
		
		
		
		
		// if join a new chatroom
		if (/*((User) localUser).addChatRoom(chatRoom)*/ true) {
			System.out.println("Add to chat room? : " + ((User) localUser).addChatRoom(chatRoom));
			chatRoomMiniModel = mainModelToMainViewAdapter.createChatRoomMiniMVC(localUserStub, chatRoom);
			// ChatRoomMiniModel chatRoomMiniModel =
			// mainModelToMainViewAdapter.createChatRoomMiniMVC(localUserStub, chatRoom);
			IReceiver receiver = chatRoomMiniModel.getRecevier();
//			CRReceiver receiver = (CRReceiver) chatRoomMiniModel.getRecevier();
			System.out.println(receiver);
			IReceiver receiverStub = (IReceiver) UnicastRemoteObject.exportObject(receiver,
					SERVER_PORT + new Random().nextInt(100));
			((CRReceiver) receiver).setStub(receiverStub);
			chatRoom.addIReceiverStub(receiverStub);
			mainModelToMainViewAdapter.appendInfo("Join the chatroom " + chatRoom.getName() + " successfully!" + "\n");

			chatRoomMiniModel.addReceiver(receiverStub);
			System.out.println("Add mini model successful!" + "Send Add receiver cmd");

			chatRoomMiniModel.showUsers();

			// AddReceiverStub a = new AddReceiverStub(receiverStub);
			// DataPacketChatRoom<IAddReceiverType> data = new
			// DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class, a,
			// receiverStub);
			// chatRoom.sendPacket(data);
			// System.out.println("Join ==== add receiver success in local");

		} else {
			System.out.println("fail to add to chat room? : " + ((User) localUser).addChatRoom(chatRoom));
			mainModelToMainViewAdapter.chooseChatRoom(chatRoom);
			mainModelToMainViewAdapter.appendInfo("You already joined this chatroom: " + chatRoom + "\n");
		}

		// IReceiver receiver = new Receiver(localUser, chatRoom.ge);
		// IReceiver receiverStub = (IReceiver)
		// UnicastRemoteObject.exportObject(receiver, BOUND_PORT + portCount++);
		//
		// chatRoom.addIReceiverStub(receiverStub);
	}

	public void exitChatRoom(IChatRoom chatRoom) {
		System.out.println("Exiting the chatroom");
		((User) localUser).removeChatRoom(chatRoom);
		mainModelToMainViewAdapter.deleteChatRoomMVC(chatRoom);
		mainModelToMainViewAdapter.appendInfo("Delete the chatroom: " + chatRoom + " successfully!" + "\n");

		// exit chat room
		// chatRoomMiniModel.exitChatRoom(chatRoom);
		chatRoomMiniModel.showUsers();

	}

	/**
	 * Connect to remote IP address, get the remote user stub ? return list of
	 * connected user ?
	 * 
	 * 
	 * @param remoteIpAddress
	 *            the ip address of that remote address
	 */
	public void connectToIP(String remoteIpAddress) {
		try {
			// find remote register on that ip address
			Registry remoteRegister = rmiUtils.getRemoteRegistry(remoteIpAddress);
			System.out.println("Connect to remote register ====> " + remoteRegister);
			IUser remoteUserStub;
			for (String name : remoteRegister.list()) {
				remoteUserStub = (IUser) remoteRegister.lookup(name);
				localUser.connect(remoteUserStub);
				remoteUserStub.connect(localUserStub);
				mainModelToMainViewAdapter
						.appendInfo("Found remote user stub" + remoteUserStub + " successfully!" + "\n");
				mainModelToMainViewAdapter
						.appendInfo("Connect with " + remoteUserStub + " on IP: " + remoteIpAddress + "\n");
				System.out.println(localUser + " auto connect back with " + remoteUserStub);
				System.out.println("The local user's remote stub list: " + ((User) localUser).getRemoteUserStubs());

				// System.out.println("The remote user's remote stub list: " + ((User)
				// remoteUserStub).getRemoteUserStubs());
				// add remote user to list

				// mainModelToMainViewAdapter.pullConnectUsers(remoteUserStub);
				mainModelToMainViewAdapter.showConnectUsers(localUser);
				mainModelToMainViewAdapter.appendInfo("Add the remote user to connect list: " + remoteUserStub + "\n");

				// ((User)remoteUserStub).updateUserList();

			}
		} catch (RemoteException | NotBoundException e) {
			System.err.println("Exception while connect to an IP address: \n" + e);
			e.printStackTrace();
		}
	}

	public void requestChatRoom(IUser remoteUser) {
		try {
			mainModelToMainViewAdapter.pullChatRooms(remoteUser.getChatRooms());
			mainModelToMainViewAdapter.appendInfo("Get chat rooms from user: " + remoteUser + "\n");
		} catch (RemoteException e) {
			System.err.println("Exception while request the chat rooms: \n" + e);
			e.printStackTrace();
		}
	}

	// more function
	public void inviteOther(IUser remoteUser, IChatRoom chatRoom) {
		((User) remoteUser).addChatRoom(chatRoom);
	}

	/**
	 * Exit the program
	 */
	public void stop() {
		System.out.println("Start stop....");
		// to-do update IRecevier? Remove self?
		// Exit all rooms
		try {
			List<IChatRoom> list = new ArrayList<>();
			for (IChatRoom room : localUser.getChatRooms()) {
				System.out.println("The exit room is " + room.getName());
				list.add(room);
				// exitChatRoom(room);
			}
			for (int i = 0; i < list.size(); i++) {
				exitChatRoom(list.get(i));
				System.out.println("Remove the room: " + list.get(i));
			}

			// exitChatRoom(list.get(0));
			// System.out.println("Remove the room: " + list.get(0));
			// exitChatRoom(list.get(1));
			// System.out.println("Remove the room: " + list.get(1));

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// // remove stub
		// //List<IUser> remoteUsers = new ArrayList<>();
		// for (IUser remoteUser : ((User)localUser).getRemoteUserStubs()) {
		// ((User)remoteUser).getRemoteUserStubs().remove(localUserStub);
		// ((User)localUserStub).getRemoteUserStubs().remove(remoteUser);
		// }

		for (IUser remoteUser : ((User) localUser).getRemoteUserStubs()) {
			try {

				((User) remoteUser).disconnect(localUserStub);
				((User) localUserStub).disconnect(remoteUser);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception in removeing user");
			}

		}

		// Exit app
		try {
			rmiUtils.stopRMI();
			System.exit(0); // normal exit
		} catch (Exception e) {
			String errMsg = "Error stopping class server: ";
			System.err.println(errMsg + e);
			e.printStackTrace();
		}
		System.exit(-1); // error exit
	}

}
