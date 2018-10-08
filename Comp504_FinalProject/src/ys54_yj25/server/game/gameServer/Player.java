package ys54_yj25.server.game.gameServer;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.UUID;

import common.DataPacketUser;
import common.IChatRoom;
import common.IUser;
import common.IUserMessageType;

public class Player implements IUser, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8458826468377967357L;
	private IUser userStub;
	private IUser mateStub;
	private Role role;
	
	public Player(IUser userStub) {
		this.userStub = userStub;
	}

	public IUser getUserStub() {
		return userStub;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setMateStub(IUser mateStub) {
		this.mateStub = mateStub;
	}
	
	public IUser getMateStub() {
		return this.mateStub;
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IChatRoom> getChatRooms() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect(IUser userStub) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends IUserMessageType> void receive(DataPacketUser<T> data) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}