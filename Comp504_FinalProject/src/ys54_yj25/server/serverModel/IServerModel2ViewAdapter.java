package ys54_yj25.server.serverModel;

import java.util.Collection;

import common.IChatRoom;
import common.IUser;
import ys54_yj25.client.mainApp.mainModel.IMainModelToMainViewAdapter;
import ys54_yj25.client.team.teamModel.ChatRoomMiniModel;

public interface IServerModel2ViewAdapter extends IMainModelToMainViewAdapter{

	public static IServerModel2ViewAdapter NULL_OBJECT = new IServerModel2ViewAdapter() {

		@Override
		public void appendInfo(String textInfo) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void displayIPAddress(String localAddress) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ChatRoomMiniModel createChatRoomMiniMVC(IUser localUserStub, IChatRoom chatRoom) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void deleteChatRoomMVC(IChatRoom chatRoom) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pullChatRooms(Collection<IChatRoom> chatRooms) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void showConnectUsers(IUser localUser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void chooseChatRoom(IChatRoom chatRoom) {
			// TODO Auto-generated method stub
			
		}
		
	};
}
