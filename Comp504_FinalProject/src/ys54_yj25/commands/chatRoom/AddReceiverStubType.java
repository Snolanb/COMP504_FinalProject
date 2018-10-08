package ys54_yj25.commands.chatRoom;

import common.IReceiver;
import common.datatype.chatroom.IAddReceiverType;

/**
 * 
 * Add Receiver stub to remote chatroom
 * 
 * @author yuejiang
 *
 */
public class AddReceiverStubType implements IAddReceiverType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 726107469368655029L;

	/**
	 * The receiver's stub
	 */
	IReceiver receiverStub;

	/**
	 * Constructor
	 * 
	 * @param receiverStub
	 *            the receiverStub needed to be add
	 */
	public AddReceiverStubType(IReceiver receiverStub) {
		this.receiverStub = receiverStub;
	}

	@Override
	public IReceiver getReceiverStub() {
		return receiverStub;
	}

}
