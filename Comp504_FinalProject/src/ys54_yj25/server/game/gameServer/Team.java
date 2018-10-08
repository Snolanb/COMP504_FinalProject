package ys54_yj25.server.game.gameServer;

import java.rmi.RemoteException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import common.DataPacketUser;
import common.IChatRoom;
import common.IReceiver;
import common.datatype.user.IInvitationType;
import ys54_yj25.commands.user.InvitationType;
import ys54_yj25.object.receiver.CRReceiver;
import ys54_yj25.object.team.ChatRoom;
import ys54_yj25.server.game.gameController.GameController;

public class Team extends ChatRoom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6265412221838307013L;
	
	private Player questionPlayer;
	private Player mapPlayer;
	private int teamID;
	
	public Team(Player questionPlayer, Player mapPlayer, int teamID) {
		super("Team " + teamID, UUID.randomUUID());
		
		this.questionPlayer = questionPlayer;
		this.mapPlayer = mapPlayer;
		this.teamID = teamID;
	}
	
	public Player getQPlayer() {
		return this.questionPlayer;
	}
	
	public Player getMPlayer() {
		return this.mapPlayer;
	}
	
	public int getTeamID() {
		return this.teamID;
	}
	
	public String getTeamName() {
		return "Team " + teamID;
	}
	
	public void joinTeam() {
		IInvitationType inviMsg = new InvitationType(this);
		System.out.println("the invitation type is: " + inviMsg);
		DataPacketUser<IInvitationType> inviData = new DataPacketUser<IInvitationType>(IInvitationType.class, inviMsg, null);
		System.out.println("the invitation data is: " + inviData);
		try {
			System.out.println("the question player is: " + questionPlayer);
			System.out.println("Its user's stub is: " + questionPlayer.getUserStub());
			questionPlayer.getUserStub().receive(inviData);
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mapPlayer.getUserStub().receive(inviData);
			System.out.println("receivers in this team: " + this.getIReceiverStubs());
			
			Optional<IReceiver> questionRec = null;
			System.out.println("questionPlayer's chatroom is: " + questionPlayer.getUserStub().getChatRooms());
			for (IChatRoom cr : questionPlayer.getUserStub().getChatRooms()) {

				System.out.println("chat rooms are: " + cr.getName());
				if (!cr.getName().equals("Lobby")) {
					System.out.println(cr.getName());

					questionRec = cr.getIReceiverStubs().stream().findFirst();
					System.out.println(questionRec.get());
				}
			}

			Optional<IReceiver> mapRec = null;
			System.out.println("mapPlayer's chatroom is: " + mapPlayer.getUserStub().getChatRooms());
			for (IChatRoom cr : mapPlayer.getUserStub().getChatRooms()) {
				if (!cr.getName().equals("Lobby")) {
					mapRec = cr.getIReceiverStubs().stream().findFirst();
					System.out.println(mapRec.get());
				}
			}
			
			for (IChatRoom cr : questionPlayer.getUserStub().getChatRooms()) {
				if (!cr.getName().equals("Lobby")) {
					cr.addIReceiverStub(mapRec.get());
					System.out.println(cr.getIReceiverStubs());
				}
			}
			
			for (IChatRoom cr : mapPlayer.getUserStub().getChatRooms()) {
				if (!cr.getName().equals("Lobby")) {
					cr.addIReceiverStub(questionRec.get());
					System.out.println(cr.getIReceiverStubs());
				}
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
