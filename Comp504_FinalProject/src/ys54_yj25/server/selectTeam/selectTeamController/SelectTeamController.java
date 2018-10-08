package ys54_yj25.server.selectTeam.selectTeamController;

import ys54_yj25.server.selectTeam.selectTeamView.SelectTeamView;

public class SelectTeamController {
	/**
	 * testing
	 */
	private SelectTeamView view; 
	
	public SelectTeamController() {
		this.view = new SelectTeamView();
	}
	
	public void start() {
		view.start();
	}
	
	public static void main(String[] args) {
		SelectTeamController controller = new SelectTeamController();
		controller.start();
	}
}
