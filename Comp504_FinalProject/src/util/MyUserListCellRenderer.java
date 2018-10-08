package util;

import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import common.IUser;


/**
 * CellRenderer for IUser
 * 
 * @author yuejiang
 *
 */
public class MyUserListCellRenderer extends DefaultListCellRenderer {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -5274866084527687092L;

	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (value instanceof IUser) {
			try {
				value = ((IUser) value).getName();
				//value = ((User) value).getName();
			} catch (RemoteException e) {
				e.printStackTrace();
//				throw new UserNotExistException(value, e);
			}
		}
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		return this;
	}
}
