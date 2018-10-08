package ys54_yj25.client.team.teamModel;

import common.IChatRoom;

/**
 * The mini model to main model adapter
 * 
 * @author yuejiang
 *
 */
public interface IMiniModeToMainModelAdapter {

	/**
	 * Exit the current chat room
	 * 
	 * @param chatRoom
	 *            the current chat room
	 */
	public void exitChatRoom(IChatRoom chatRoom);

}
