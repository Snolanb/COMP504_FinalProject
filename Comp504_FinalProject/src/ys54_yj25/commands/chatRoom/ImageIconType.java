package ys54_yj25.commands.chatRoom;

import javax.swing.ImageIcon;

import common.ICRMessageType;

public class ImageIconType implements ICRMessageType {

	/**
	 * The serialVersionUID of this class
	 */
	private static final long serialVersionUID = 7773342670754975963L;

	ImageIcon imageIconInfo;

	public ImageIconType(ImageIcon imageIconInfo) {
		this.imageIconInfo = imageIconInfo;
	}

	public ImageIcon getImageIconInfo() {
		return imageIconInfo;
	}

}
