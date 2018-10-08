package util;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import common.IChatRoom;

/**
 * Cell Renderer for chat room
 * 
 * @author yuejiang
 *
 */
public class MyChatRoomListCellRenderer extends DefaultListCellRenderer {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 8255439099231738409L;

	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (value instanceof IChatRoom) {
			value = ((IChatRoom) value).getName();
		}
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		return this;
	}
}
