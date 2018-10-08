package ys54_yj25.client.mainApp.mainModel;

import javax.swing.ImageIcon;

import common.ICRCmd2ModelAdapter;
import common.IChatRoom;
import common.IReceiver;



/**
 * Command to Mini Chatroom Model
 * 
 * @author yuejiang
 *
 */
public interface ICRCmdToChatRoomMiniModel extends ICRCmd2ModelAdapter {

	/**
	 * Display image
	 * 
	 * @param img
	 *            the image needed to display
	 */
	public void displayImage(ImageIcon img);

	/**
	 * Add a new receiver to chat room
	 * 
	 * @param receiver
	 *            the receiver needed to add to chat room
	 */
	public void addReceiver(IReceiver receiver);

	/**
	 * Delete a receiver from chat room
	 * 
	 * @param receiver
	 *            the receiver needed to delete from the chat room
	 */
	public void deleteReceiver(IReceiver receiver);

	public IChatRoom getChatRoom();

	public void updateUserList();

}
