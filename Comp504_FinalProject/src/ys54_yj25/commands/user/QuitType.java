package ys54_yj25.commands.user;

import common.IUser;
import common.datatype.user.IQuitType;

public class QuitType implements IQuitType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = -652856251777180253L;

	/**
	 * The user who quit
	 */
	IUser user;

	/**
	 * Constructor
	 * 
	 * @param user
	 *            the user who quit
	 */
	public QuitType(IUser user) {
		this.user = user;
	}

	@Override
	public IUser getUserStub() {
		return user;
	}

}
