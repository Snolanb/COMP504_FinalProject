package ys54_yj25.commands.user;

import common.DataPacketUserAlgoCmd;
import common.datatype.user.IUserInstallCmdType;

public class UserInstallCmdType implements IUserInstallCmdType {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8145235792434838246L;

	private DataPacketUserAlgoCmd<?> cmd;
	private Class<?> index;

	public UserInstallCmdType(DataPacketUserAlgoCmd<?> cmd, Class<?> index) {
		this.cmd = cmd;
		this.index = index;
	}

	@Override
	public DataPacketUserAlgoCmd<?> getCmd() {
		return cmd;
	}

	@Override
	public Class<?> getCmdId() {
		return index;
	}

}
