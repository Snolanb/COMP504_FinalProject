package ys54_yj25.object.user;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import common.DataPacketUser;
import common.DataPacketUserAlgoCmd;
import common.IChatRoom;
import common.IUser;
import common.IUserCmd2ModelAdapter;
import common.IUserMessageType;
import common.datatype.IRequestCmdType;
import common.datatype.user.IInvitationType;
import common.datatype.user.IUserInstallCmdType;
import provided.datapacket.DataPacketAlgo;
import ys54_yj25.client.mainApp.mainModel.IMainModelToMainViewAdapter;
import ys54_yj25.commands.chatRoom.RequestCmdType;
import ys54_yj25.commands.user.InvitationType;
import ys54_yj25.commands.user.InviteCmd;
import ys54_yj25.commands.user.RequestCmd;
import ys54_yj25.commands.user.TeamMVCCmd;
import ys54_yj25.commands.user.TeamMVCFactoryType;
import ys54_yj25.server.selectTeam.selectTeamController.SelectTeamMVCFac;
import ys54_yj25.server.serverModel.MyUserCmd2ModelAdapter;

/**
 * User class
 * 
 * @author yuejiang
 *
 */
public class User implements IUser {

	/**
	 * The user name
	 */
	private String userName;

	/**
	 * The user ID
	 */
	private final UUID userId;

	/**
	 * The user IP address
	 */
	private final InetAddress ipAddress;

	/**
	 * The chat rooms associated with this user
	 */
	private Set<IChatRoom> chatRooms;
	
	private IChatRoom lobby;

	// set? list?
	/**
	 * The user stub of other connected remote users
	 */
	private Set<IUser> remoteUserStubs;
	
	private IUser userStub;
	
	private IMainModelToMainViewAdapter mainModelToMainViewAdapter;
	
	private MyUserCmd2ModelAdapter cmd2ModelAdpt;
	
	protected Map<Class<?>, List<DataPacketUser<?>>> messageCache;
	
	/**
	 * Default command for all the unknown data type.
	 */
	private DataPacketUserAlgoCmd<IUserMessageType> defaultCmd = new DataPacketUserAlgoCmd<IUserMessageType>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5743820914796095540L;

		@Override
		public String apply(Class<?> index, DataPacketUser<IUserMessageType> host, String... params) {
			// TODO Auto-generated method stub
			System.out.println("User level default command");
			System.out.println("Unknown data type is: " + index);
			List<DataPacketUser<?>> cache = messageCache.getOrDefault(index, new LinkedList<DataPacketUser<?>>());
			cache.add(host);
			messageCache.put(index, cache);
			RequestCmdType requestCmdType = new RequestCmdType(index);
			try {
				host.getSender().receive(new DataPacketUser<IRequestCmdType>(IRequestCmdType.class, requestCmdType, User.this.userStub));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}

		@Override
		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub
			
		}	
		
	};
	
	/**
	 * Command that install new commands to the user level.
	 */
	private DataPacketUserAlgoCmd<IUserInstallCmdType> installCmd = new DataPacketUserAlgoCmd<IUserInstallCmdType>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -788099250373663804L;

		@Override
		public String apply(Class<?> index, DataPacketUser<IUserInstallCmdType> host, String... params) {
			// TODO Auto-generated method stub
			//Class<?> cmdIndex = host.getData().getClass();
			Class<?> cmdIndex = host.getData().getCmdId();
			DataPacketUserAlgoCmd<?> cmd = host.getData().getCmd();
			cmd.setCmd2ModelAdpt(cmd2ModelAdpt);
			commands.setCmd(cmdIndex, cmd);
			for (DataPacketUser<?> packet : messageCache.get(cmdIndex)) {
				 packet.execute(commands);
				 messageCache.remove(cmdIndex);
				 return "install?????? ---> in install cmd";
			}
			return null;
		}

		@Override
		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
//	private DataPacketUserAlgoCmd<IInvitationType> inviteCmd = new DataPacketUserAlgoCmd<IInvitationType>() {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -629629648056607485L;
//		private MyUserCmd2ModelAdapter _cmd2ModelAdpt;
//
//		@Override
//		public String apply(Class<?> index, DataPacketUser<IInvitationType> host, String... params) {
//			// TODO Auto-generated method stub
//			System.out.println("Joining team...");
//			IChatRoom team = host.getData().getChatRoom();
//			//User.this.addChatRoom(team);
//			_cmd2ModelAdpt.joinChatRoom(team);
//			return null;
//		}
//
//		@Override
//		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
//			// TODO Auto-generated method stub
//			_cmd2ModelAdpt = (MyUserCmd2ModelAdapter) cmd2ModelAdpt;
//		}
//		
//	};
	

	
//	private DataPacketUserAlgoCmd<TeamMVCFactoryType> teamMVCCmd = new DataPacketUserAlgoCmd<TeamMVCFactoryType>() {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -4539653186795592730L;
//
//		@Override
//		public String apply(Class<?> index, DataPacketUser<TeamMVCFactoryType> host, String... params) {
//			// TODO Auto-generated method stub
//			System.out.println("Bringing up the select team MVC....");
//			host.getData().getFac().make();
//			return null;
//		}
//
//		@Override
//		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	};
	
//	private DataPacketUserAlgoCmd<RequestCmdType> requestCmd = new DataPacketUserAlgoCmd<RequestCmdType>() {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -4539653186795592730L;
//
//		@Override
//		public String apply(Class<?> index, DataPacketUser<RequestCmdType> host, String... params) {
//			// TODO Auto-generated method stub
//			System.out.println("Command requested!");
//			Class<?> cmdIndex = host.getData().getCmdId();
//			System.out.println("cmdIndex = " + cmdIndex + "是否为空？？？？");
//			
//			DataPacketUserAlgoCmd<?> cmd = (DataPacketUserAlgoCmd<?>) commands.getCmd(cmdIndex);
//			System.out.println("cmd =" + cmd + "是否为空？？？？");
//			
//			if (cmd == null) {
////				try {
////					host.getSender().receive(new DataPacketUser<IUserFailureStatusType>(IUserFailureStatusType.class, new UserFailureStatusCmdType<>(host, host.getData()), User.this.userStub));
////				} catch (RemoteException e) {
////					// TODO Auto-generated catch block
////					System.out.println("Fail to send failure status!");
////					e.printStackTrace();
////				}
//			System.out.println("cmd == ??????????????????");
//				
//			} else {
//				System.out.println("Client: Installing command...");
//				try {
//					//UserInstallCmdType installCmdType = new UserInstallCmdType(cmd, cmdIndex);
//					UserInstallCmdType installCmdType = new UserInstallCmdType(cmd, cmdIndex);
//					//host.getSender().receive(new DataPacketUser<IUserInstallCmdType>(IUserInstallCmdType.class, installCmdType, User.this.userStub));
//					host.getSender().receive(new DataPacketUser<IUserInstallCmdType>(IUserInstallCmdType.class, installCmdType, User.this));
//				} catch (RemoteException e) {
//					// TODO Auto-generated catch block
//					System.out.println("Fail to install command to remote!");
//					e.printStackTrace();
//				}
//			}
//			return null;
//		}
//		
//
//		@Override
//		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	};
	
	/**
	 * Request Command
	 */
//	private DataPacketUserAlgoCmd<RequestCmdType> requestCmd = new DataPacketUserAlgoCmd<RequestCmdType>() {
//
//		/**
//		 * SerialVersionUID
//		 */
//		private static final long serialVersionUID = -1305473253934150856L;
//
//		@Override
//		public String apply(Class<?> index, DataPacketUser<RequestCmdType> host, String... params) {
//			System.out.println("Request? success?");
//			System.out.println("Command requested!");
//			
//			Class<?> cmdIndex = host.getData().getCmdId();
//			System.out.println("The cmdIndex is " + cmdIndex);
//			DataPacketUserAlgoCmd<?> cmd = (DataPacketUserAlgoCmd<?>) commands.getCmd(cmdIndex);
//			System.out.println("The cmd is " + cmd);
//
//			if (cmd == null) {
//				System.out.println("loser? success?");
//				try {
//					host.getSender().receive(new DataPacketUser<IUserFailureStatusType>(IUserFailureStatusType.class, 
//							new UserFailureStatusCmdType<>(host, host.getData()), User.this.userStub));
//				} catch (RemoteException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			} else {
//				// DataPacketAlgoCmd<?> cmd = (DataPacketAlgoCmd<?>) commands.getCmd(cmdIndex);
//				System.out.println("start install? success?");
//				try {
//					host.getSender().receive(new DataPacketUser<IUserInstallCmdType>(IUserInstallCmdType.class,
//							new UserInstallCmdType(cmd, cmdIndex), User.this.userStub));
//				} catch (RemoteException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			return null;
//		}
//
//
//		@Override
//		public void setCmd2ModelAdpt(IUserCmd2ModelAdapter cmd2ModelAdpt) {
//			// TODO Auto-generated method stub
//			
//		}
//	};
	
	
	
	
	
	
    /**
	 * The commands used by the data
	 */
	private DataPacketAlgo<String, String> commands = new DataPacketAlgo<String, String>(defaultCmd);
	
	private DataPacketUserAlgoCmd<TeamMVCFactoryType> teamMVCCmd = new TeamMVCCmd();
	
	private DataPacketUserAlgoCmd<IRequestCmdType> requestCmd = new RequestCmd(userStub, commands);
	
	private DataPacketUserAlgoCmd<IInvitationType> inviteCmd = new InviteCmd(cmd2ModelAdpt);
	
//	private DataPacketUserAlgoCmd<IUserInstallCmdType> installCmd = new InstallCmd(cmd2ModelAdpt, commands);

	/**
	 * Install all the known command type to the User.
	 */
	private void setCmd() {
		commands.setCmd(IUserMessageType.class, defaultCmd);
		commands.setCmd(IUserInstallCmdType.class, installCmd);
		commands.setCmd(IInvitationType.class, inviteCmd);
		commands.setCmd(IRequestCmdType.class, requestCmd);
//		commands.setCmd(TeamMVCFactoryType.class, teamMVCCmd);
	}
	
	private void setCmdAdapter() {
		defaultCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
		installCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
		inviteCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
		requestCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 *            the user name
	 * @param ipString
	 *            the user IP
	 * @param mainModelToMainViewAdapter
	 *            the model to view adapter for the main MVC
	 * @throws UnknownHostException
	 *             exception
	 * @throws java.net.UnknownHostException 
	 */
	public User(final String name, final String ipString, IMainModelToMainViewAdapter mainModelToMainViewAdapter, MyUserCmd2ModelAdapter cmd2ModelAdpt) throws UnknownHostException, java.net.UnknownHostException {
		this.userName = name;
		userId = UUID.randomUUID();
		ipAddress = InetAddress.getByName(ipString);
		chatRooms = new HashSet<>();
		remoteUserStubs = new HashSet<>();
		this.mainModelToMainViewAdapter = mainModelToMainViewAdapter;
		this.cmd2ModelAdpt = cmd2ModelAdpt;
		messageCache = new HashMap<Class<?>, List<DataPacketUser<?>>>();
		setCmdAdapter();
		setCmd();
	}

	/**
	 * Return the IP address of this user
	 * 
	 * @return the IP address of this user
	 */
	public InetAddress getIP() {
		return ipAddress;
	}

	@Override
	public String toString() {
		// return new StringBuilder().append(userName).append("
		// ").append(userId).append(" ").append(ipAddress).toString();
		return userName;
	}

	@Override
	public String getName() throws RemoteException {
		return userName;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return userId;
	}

	@Override
	public Collection<IChatRoom> getChatRooms() throws RemoteException {
		return chatRooms;
	}

	@Override
	public void connect(IUser userStub) throws RemoteException {
		// TODO Auto-generated method stub
		// ? auto - connect - back
		remoteUserStubs.add(userStub);
		updateUserList();
//		if (this.userName == "Server") {
//			System.out.println("Server: Inviting to lobby...");
//			InvitationType inviMsg = new InvitationType(lobby);
//			DataPacketUser<IInvitationType> inviData = new DataPacketUser<IInvitationType>(IInvitationType.class, inviMsg, this);
//			userStub.receive(inviData);
//			SelectTeamMVCFac fac = new SelectTeamMVCFac();
//			TeamMVCFactoryType facMsg = new TeamMVCFactoryType(fac);
//			DataPacketUser<TeamMVCFactoryType> facData = new DataPacketUser<TeamMVCFactoryType>(TeamMVCFactoryType.class, facMsg, this);
//			userStub.receive(facData);
//		}
	}
	
	public boolean setLobby(IChatRoom lobby) {
		this.lobby = lobby;
		return true;
	}

	public Collection<IUser> getRemoteUserStubs() {
		return remoteUserStubs;
	}

	public boolean addChatRoom(IChatRoom chatRoom) {
		return chatRooms.add(chatRoom);
	}

	public boolean removeChatRoom(IChatRoom chatRoom) {
		return chatRooms.remove(chatRoom);
	}
	
	public void updateUserList() {
		mainModelToMainViewAdapter.showConnectUsers(this);
	}
	
	
	public void disconnect(IUser userStub) {
		System.out.println("$$$: " + remoteUserStubs);
		System.out.println("###" + userStub);
		remoteUserStubs.remove(userStub);
		System.out.println("$$$: " + remoteUserStubs);
		updateUserList();
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
		return userId.hashCode();
	}

	@Override
	public <T extends IUserMessageType> void receive(DataPacketUser<T> data) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Received data: " + data.getData());
		data.execute(commands);
	}	
	
	public void installCmd(Class <?> idx, DataPacketUserAlgoCmd<?> cmd) {
		this.commands.setCmd(idx, cmd);
	}
	
	public void setStub(IUser userStub) {
		this.userStub = userStub;
	}
}
