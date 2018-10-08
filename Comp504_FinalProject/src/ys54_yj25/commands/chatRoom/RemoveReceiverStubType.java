package ys54_yj25.commands.chatRoom;


import common.IReceiver;
import common.datatype.chatroom.IRemoveReceiverType;

public class RemoveReceiverStubType implements IRemoveReceiverType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = -1123219762779804800L;
	
	IReceiver receiverStub;

	public RemoveReceiverStubType(IReceiver receiverStub) {
		this.receiverStub = receiverStub;
	}
	
	@Override
	public IReceiver getReceiverStub() {
		// TODO Auto-generated method stub
		return receiverStub;
	}

}
