package ys54_yj25.commands.chatRoom;

import common.DataPacketCR;
import common.datatype.chatroom.ICRFailureStatusType;

public class FailureStatusCmdType<I> implements ICRFailureStatusType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 6982232272028916032L;

	private DataPacketCR<?> initalData;
	private I info;

	public FailureStatusCmdType(DataPacketCR<?> initalData, I info) {
		this.initalData = initalData;
		this.info = info;
	}

	@Override
	public DataPacketCR<?> getOriginalData() {
		return initalData;
	}

	@Override
	public String getFailureInfo() {
		// return (String) info;
		return info.toString();
	}

}
