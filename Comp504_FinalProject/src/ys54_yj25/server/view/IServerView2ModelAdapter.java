package ys54_yj25.server.view;

public interface IServerView2ModelAdapter {

	public String broadcastMsg (String msg);
	
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
	
	public void startGame();
}
