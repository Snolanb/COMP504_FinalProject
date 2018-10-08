package ys54_yj25.object.team;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import common.DataPacketCR;
import common.ICRMessageType;
import common.IChatRoom;
import common.IReceiver;

/**
 * A concrete chatroom class
 * 
 * @author yuejiang
 *
 */
public class ChatRoom implements IChatRoom {

	/**
	 * The serialVersionUID of this chatroom
	 */
	private static final long serialVersionUID = 1484634572862684629L;

	/**
	 * The name of the chatroom
	 */
	private final String chatRoomName;

	/**
	 * The ID of the chatroom
	 */
	private final UUID chatRoomId;

	/**
	 * The creator(owner) of the chatroom
	 */
	private final UUID ownerID;

	/**
	 * The set of receivers in this chatroom
	 */
	private Set<IReceiver> receivers;

	/**
	 * Constructor of ChatRoom
	 * 
	 * @param charRoomName
	 *            the name of the chat room
	 * @param ownerID
	 *            the owner ID of the chat room
	 */
	public ChatRoom(String charRoomName, UUID ownerID) {
		this.chatRoomName = charRoomName;
		this.chatRoomId = UUID.randomUUID();
		this.ownerID = ownerID;
		receivers = new HashSet<IReceiver>();
	}

	/**
	 * To string method
	 */
	public String toString() {
		return chatRoomName + " + " + chatRoomId + " + " + ownerID;
	}

	@Override
	public String getName() {
		return chatRoomName;
	}

	@Override
	public UUID getUUID() {
		return chatRoomId;
	}

	@Override
	public Collection<IReceiver> getIReceiverStubs() {
		return receivers;
	}

//	@Override
//	public <T> void sendPacket(DataPacketCR<T> data) {
//
//		System.out.println("Sending data in ChatRoom: " + data + " Receivers " + receivers);
//		IReceiver sender = data.getSender();
//		for (IReceiver receiver : receivers) {
//			// don not send to self
//			if (!sender.equals(receiver)) {
//				// create a new thread to let the receiver to receive.
//				(new Thread() {
//					public void run() {
//						try {
//							System.out.println(data + " sending to receiver: " + receiver);
//							receiver.receive(data);
//						} catch (RemoteException e) {
//							System.out.println("remote receiver " + receiver + " receive data packet error");
//							e.printStackTrace();
//						}
//					}
//				}).start(); // start the new thread
//			}
//		}
//
//	}

	// @Override
	// public boolean addIReceiverStub(IReceiver receiver) {
	// System.out.println("Add a receiver remotely ===>");
	// sendPacket(new DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class,
	// new AddReceiverStub(receiver), receiver));
	//
	// }
	//
	// @Override
	// public boolean removeIReceiverStub(IReceiver receiver) {
	// System.out.println("Remove a receiver remotely ===>");
	// return false;
	// }

	// @Override
	// public boolean addIReceiverStubLocally(IReceiver receiver) {
	// System.out.println("Add a receiver locally ===>");
	// return receivers.add(receiver);
	// }
	//
	// @Override
	// public boolean removeIReceiverStubLocally(IReceiver receiver) {
	// System.out.println("Remove a receiver locally ===>");
	// return receivers.remove(receiver);
	// }

	@Override
	public boolean addIReceiverStub(IReceiver receiver) {
		System.out.println("Add a receiver locally  ===>");
		return receivers.add(receiver);
	}

	@Override
	public boolean removeIReceiverStub(IReceiver receiver) {
		System.out.println("Remove a receiver locally  ===>");
		return receivers.remove(receiver);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IChatRoom))
			return false;
		IChatRoom that = (IChatRoom) obj;
		return this.getUUID().equals(that.getUUID());
	}

	@Override
	public int hashCode() {
		return getUUID().hashCode();
	}

	@Override
	public <T extends ICRMessageType> void sendPacket(DataPacketCR<T> data) {
		System.out.println("Sending data in ChatRoom: " + data + " Receivers " + receivers);
		IReceiver sender = data.getSender();
		for (IReceiver receiver : receivers) {
			// don not send to self
			if (!sender.equals(receiver)) {
				// create a new thread to let the receiver to receive.
				(new Thread() {
					public void run() {
						try {
							System.out.println(data + " sending to receiver: " + receiver);
							receiver.receive(data);
						} catch (RemoteException e) {
							System.out.println("remote receiver " + receiver + " receive data packet error");
							e.printStackTrace();
						}
					}
				}).start(); // start the new thread
			}
		}
	}

}
