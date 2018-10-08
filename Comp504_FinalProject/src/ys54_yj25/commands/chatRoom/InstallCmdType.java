package ys54_yj25.commands.chatRoom;

import common.DataPacketCRAlgoCmd;
import common.datatype.chatroom.ICRInstallCmdType;

public class InstallCmdType implements ICRInstallCmdType {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1878831492770601112L;

	private DataPacketCRAlgoCmd<?> cmd;
	private Class<?> index;

	public InstallCmdType(DataPacketCRAlgoCmd<?> cmd, Class<?> index) {
		this.cmd = cmd;
		this.index = index;
	}

	@Override
	public DataPacketCRAlgoCmd<?> getCmd() {
		return cmd;
	}

	@Override
	public Class<?> getCmdId() {
		return index;
	}

}
