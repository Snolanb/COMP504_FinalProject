package ys54_yj25.commands.chatRoom;

import common.ICRMessageType;

public class StringType implements ICRMessageType{

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 1299374893639986498L;
	
	String stringInfo;
	
	public StringType(String stringInfo) {
		this.stringInfo = stringInfo;
	}
	
	public String getStringInfo() {
		return stringInfo;
	}

}
