package ys54_yj25.server.selectTeam.selectTeamController;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.Serializable;

public class SelectTeamMVCFac implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6770374696861066510L;

	public Component make() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("client!!!!!!!!");
					SelectTeamController controller = new SelectTeamController();
					System.out.println("client!!!!!!!!client!!!!!!!!!");
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//		SelectTeamController controller = new SelectTeamController();
//		controller.start();
		return null;
	}

}
