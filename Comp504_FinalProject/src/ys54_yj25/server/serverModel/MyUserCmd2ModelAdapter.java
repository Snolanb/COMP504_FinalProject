package ys54_yj25.server.serverModel;

import common.IChatRoom;
import common.IUserCmd2ModelAdapter;

public interface MyUserCmd2ModelAdapter extends IUserCmd2ModelAdapter {

	public void joinChatRoom(IChatRoom chatRoom);
}
