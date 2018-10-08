package ys54_yj25.client.mainApp.mainView;

/**
 * 
 * The main view to main model adapter
 * 
 * @author yuejiang
 *
 * @param <UserObj>
 *            the user item
 * @param <ChatRoomObj>
 *            the chat room item
 */
public interface IMainViewToMainModelAdapter<UserObj, ChatRoomObj> {

	/**
	 * Start and create the user
	 * 
	 * @param userName
	 *            the user name
	 */
	public void startAndCreateUser(String userName);

	/**
	 * create the chat room
	 * 
	 * @param chatRoomName
	 *            the chat room name
	 */
	public void createChatRoom(String chatRoomName);

	/**
	 * Connect to the remote user
	 * 
	 * @param remoteIpAddress
	 *            the ip address of the remote user
	 */
	public void connectToRemote(String remoteIpAddress);

	/**
	 * Request the chat room from the user
	 * 
	 * @param user
	 *            the remote user
	 */
	public void requestChatRooms(UserObj user);

	/**
	 * Connect to the chat room
	 * 
	 * @param chatRoom
	 *            the chat room 
	 */
	public void connectToChatRoom(ChatRoomObj chatRoom);

	/**
	 * Exit the application
	 */
	public void exitAll();

	// /**
	// *
	// * @param roomId
	// */
	// public void quitChatRoom(UUID roomId);
	//
	// public void sendText(String text);
}
