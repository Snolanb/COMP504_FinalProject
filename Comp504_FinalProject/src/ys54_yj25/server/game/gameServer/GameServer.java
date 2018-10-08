package ys54_yj25.server.game.gameServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ys54_yj25.server.game.gameModel.IGameModel;
import ys54_yj25.server.game.gameModel.TargetPlace;

public class GameServer implements IGameServer{

	private List<Player> players;
	private Map<Player, Team> teams;
	int teamNum;

	public GameServer(List<Player> players) {
		this.players = players;
		this.teamNum = players.size() / 2 + 1;
		this.teams = new HashMap<Player, Team>();
	}

	public Map<Player, Team> assignPalyerToTeams() {
		// TODO
		System.out.println("start assigning teams...");
		for (int i = 0; i < players.size(); i += 2) {
			for (int j = 0; j < teamNum; j++) {
				if (i % teamNum == j) {
					Team team = null;
					players.get(i).setRole(Role.QUESTION_SOLVER);
					if((i+1)<players.size()) {
						players.get(i).setMateStub(players.get(i + 1).getUserStub());
						players.get(i + 1).setRole(Role.MAP_FINDER);
						players.get(i + 1).setMateStub(players.get(i).getUserStub());
						team = new Team(players.get(i), players.get(i + 1), j);
						System.out.println("the team is: " + team);
//						team.joinTeam();
					}
					else {
						team = new Team(players.get(i), null, j);
						System.out.println("the team is: " + team);
//						team.joinTeam();
					}
					System.out.println("team is: " + team);
					System.out.println("player is: " + players.get(i));
					System.out.println("player is: " + players.get(i+1));
					teams.put(players.get(i), team);
					teams.put(players.get(i + 1), team);
				}
			}
		}
		return teams;
	}

	public void startPlaying() {
		for (Team team : teams.values()) {
			// Play game for each team
		}
	}

	@Override
	public void sendWinningMsg(Player player) {
		// TODO Auto-generated method stub
		teams.get(player);
	}



}
