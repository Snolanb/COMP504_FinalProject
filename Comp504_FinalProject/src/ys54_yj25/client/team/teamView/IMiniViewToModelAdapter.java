package ys54_yj25.client.team.teamView;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * The view to model adapter
 * 
 * @author yuejiang
 *
 */
public interface IMiniViewToModelAdapter {

	/**
	 * Send message
	 * 
	 * @param msg
	 *            the message
	 */
	public void sendMessage(String msg);

	/**
	 * Exit the current chat room
	 */
	public void exitRoom();

	/**
	 * Send file
	 * 
	 * @param file
	 *            the file need to send
	 */
	public void sendFile(File file);

	/**
	 * Send image
	 * 
	 * @param image
	 *            the image need to send
	 */
	public void sendImage(ImageIcon image);

}
