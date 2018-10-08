package ys54_yj25.client.mainApp.mainModel;

import java.util.Collection;

import common.IChatRoom;
import common.IUser;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;

/**
 * Interface IMainModelToMainViewAdapter that goes from the main model to the
 * main view that enables the main model to talk to the main view and ask the
 * main view to update.
 * 
 * @author yuejiang
 *
 */

public interface IMainModelToMainViewAdapter {

	// public Consumer<String> getDisplayMethod();

	/**
	 * Appends the string to the text display on the main view
	 * 
	 * @param textInfo the text message info
	 */
	public void appendInfo(String textInfo);

	/**
	 * Display the IP address
	 * 
	 * @param localAddress
	 *            the local IP address
	 */
	public void displayIPAddress(String localAddress);

	/**
	 * Create a mini chat room MVC
	 * 
	 * @param localUserStub
	 *            the local user to create the mini MVC
	 * @param chatRoom
	 *            the chat room used to create the mini MVC
	 * @return the main model to mini chat room MVC adapter
	 */
	// public IMainModelToChatRoomMiniMVCAdapter createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom);
	//public IReceiver createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom);
	public ChatRoomMiniModel createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom);
	
	/**
	 * Delete a mini chat room MVC
	 * 
	 * @param chatRoom
	 *            the chat room needed to be delete
	 */
	public void deleteChatRoomMVC(IChatRoom chatRoom);

	/**
	 * Add chat rooms to list
	 * 
	 * @param chatRooms
	 *            add all connected chatrooms to list
	 */
	public void pullChatRooms(Collection<IChatRoom> chatRooms);

	/**
	 * Add remote user to list
	 * 
	 * @param localUser
	 *            connected remote user
	 */
	public void showConnectUsers(IUser localUser);

	/**
	 * Choose an already added chatroom
	 * 
	 * @param chatRoom
	 *            the selected chat room
	 */
	public void chooseChatRoom(IChatRoom chatRoom);

}
