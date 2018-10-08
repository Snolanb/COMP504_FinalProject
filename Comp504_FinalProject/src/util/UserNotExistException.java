package util;

import common.IUser;

//@SuppressWarnings("serial")
public class UserNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6514219077975949234L;

	/**
	 * 
	 */
	
	
	private IUser errorUser;
	
	public UserNotExistException(IUser errorUser, Throwable t) {
		super(t);
		this.errorUser = errorUser;
	}

	public IUser getErrorUser() {
		return errorUser;
	}

	public void setErrorUser(IUser errorUser) {
		this.errorUser = errorUser;
	}
}
