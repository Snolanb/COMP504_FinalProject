package ys54_yj25.commands.chatRoom;

import common.datatype.IRequestCmdType;

/**
 * Request command from sender
 * 
 * @author yuejiang
 *
 */
public class RequestCmdType implements IRequestCmdType {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -504409388937533532L;

	private Class<?> index;

	public RequestCmdType(Class<?> index) {
		this.index = index;
	}

	@Override
	public Class<?> getCmdId() {
		// TODO Auto-generated method stub
		return index;
	}

}
