package ys54_yj25.commands.user;

import common.IChatRoom;
import common.datatype.user.IInvitationType;

public class InvitationType implements IInvitationType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 5610824961744869679L;

	/**
	 * The chatroom which clients will enter
	 */
	IChatRoom chatroom;

	/**
	 * Constructor
	 * 
	 * @param chatRoom
	 *            the chatroom waiting to be join
	 */
	public InvitationType(IChatRoom chatRoom) {
		this.chatroom = chatRoom;
	}

	@Override
	public IChatRoom getChatRoom() {
		return chatroom;
	}

}
