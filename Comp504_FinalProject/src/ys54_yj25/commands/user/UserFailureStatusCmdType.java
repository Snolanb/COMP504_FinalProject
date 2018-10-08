package ys54_yj25.commands.user;

import common.DataPacketUser;
import common.IUserMessageType;
import common.datatype.user.IUserFailureStatusType;

public class UserFailureStatusCmdType<I> implements IUserFailureStatusType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 6030938362103001113L;

	private DataPacketUser<?> initalData;
	private I info;

	public UserFailureStatusCmdType(DataPacketUser<?> initalData, I info) {
		this.initalData = initalData;
		this.info = info;
	}

	@Override
	public DataPacketUser<? extends IUserMessageType> getOriginalData() {
		return initalData;

	}

	@Override
	public String getFailureInfo() {
		return info.toString();
	}

}
