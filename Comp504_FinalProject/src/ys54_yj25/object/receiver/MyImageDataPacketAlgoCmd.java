package ys54_yj25.object.receiver;

import java.awt.Component;
import java.awt.Image;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import common.DataPacketCR;
import common.DataPacketCRAlgoCmd;
import common.ICRCmd2ModelAdapter;
import common.IComponentFactory;
import ys54_yj25.client.mainApp.mainModel.ICRCmdToChatRoomMiniModel;
import ys54_yj25.commands.chatRoom.ImageIconType;

public class MyImageDataPacketAlgoCmd extends DataPacketCRAlgoCmd<ImageIconType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1909840944020665021L;

	private transient ICRCmd2ModelAdapter cmdToChatRoom;
	
	public MyImageDataPacketAlgoCmd(ICRCmdToChatRoomMiniModel cmdToChatRoom) {
		this.cmdToChatRoom = cmdToChatRoom;
	}
	
	@Override
	public String apply(Class<?> index, DataPacketCR<ImageIconType> host, String... params) {
		try {
			cmdToChatRoom.appendMsg(host.getSender().getUserStub().getName() + ": ",
					host.getSender().getUserStub().getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon imageIcon = host.getData().getImageIconInfo();
		cmdToChatRoom.buildNonScrollableComponent(new IComponentFactory() {

			@Override
			public Component makeComponent() {
				int height = Math.min(imageIcon.getIconHeight(), 200);
				double ratio = (double) imageIcon.getIconWidth() / imageIcon.getIconHeight();
				int width = (int) (height * ratio);
				Image image = imageIcon.getImage();
				ImageIcon resizedImage = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
				return new JLabel(resizedImage);
			}
		}, imageIcon.toString());
		return imageIcon.toString();
	}

	@Override
	public void setCmd2ModelAdpt(ICRCmd2ModelAdapter cmd2ModelAdpt) {
		this.cmdToChatRoom = cmd2ModelAdpt;		
	}

}
