package ys54_yj25.server.game.gameView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Earth;
import map.IRightClickAction;
import map.MapLayer;
import map.MapPanel;
import ys54_yj25.server.game.gameModel.Question;
import javax.swing.BoxLayout;

public class AppFrame<CBType> extends JFrame {
	private static final long serialVersionUID = -1046744381305932964L;
	private JPanel _ctrlPanel;
	private MapPanel _mapPanel;
	private JComboBox<CBType> _places;
	private JTextField _latField;
	private JButton _goLatLong;
	private JTextField _longField;
	private JButton _gotoBtn;
	private IView2ModelAdapter<CBType> _adpt;

	private JPanel questionPanel;
	private JPanel playerInfoPanel;
	private JLabel LabelQuestion;
	private JTextField textFieldAnswer;
	private JPanel panel;
	private JButton btnNewButtonQuestion;
	private JButton btnNewButtonAnswer;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	// private IRightClickAction _rightClick;

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public AppFrame(IView2ModelAdapter<CBType> adpt, IRightClickAction rightClick) {
		super();
		_adpt = adpt;
		// _rightClick = rightClick;
		initGUI(rightClick);
	}

	private void initGUI(IRightClickAction rightClick) {
		setTitle("GameApp - Welcome");
		getContentPane().setForeground(SystemColor.textHighlight);
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			_ctrlPanel = new JPanel();
			FlowLayout jPanel1Layout = new FlowLayout();
			_ctrlPanel.setLayout(jPanel1Layout);
			getContentPane().add(_ctrlPanel, BorderLayout.SOUTH);
			_ctrlPanel.setPreferredSize(new java.awt.Dimension(390, 43));
			_ctrlPanel.setBackground(SystemColor.textHighlight);

			_latField = new JTextField();
			_ctrlPanel.add(_latField);
			// _latField.setPreferredSize(new java.awt.Dimension(390, 43));

			_latField.setPreferredSize(new java.awt.Dimension(88, 22));

			_longField = new JTextField();
			_ctrlPanel.add(_longField);
			_longField.setPreferredSize(new java.awt.Dimension(88, 22));

			_goLatLong = new JButton();
			_ctrlPanel.add(_goLatLong);
			_goLatLong.setText("Go!");
			_goLatLong.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					_adpt.goLatLong(_latField.getText(), _longField.getText());
					_mapPanel.repaint();
				}
			});

			_places = new JComboBox<CBType>();
			_ctrlPanel.add(_places);

			_gotoBtn = new JButton();
			_ctrlPanel.add(_gotoBtn);
			_gotoBtn.setText("Go!");
			_gotoBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					_adpt.goPlace(_places.getItemAt(_places.getSelectedIndex()));
					_mapPanel.repaint();
				}
			});

			_mapPanel = new MapPanel(Earth.class);
			getContentPane().add(_mapPanel, BorderLayout.CENTER);
			_mapPanel.setPreferredSize(new java.awt.Dimension(800, 600));
			_mapPanel.addRightClickAction(rightClick);

			questionPanel = new JPanel();
			questionPanel.setBackground(SystemColor.textHighlight);
			questionPanel
					.setBorder(new TitledBorder(null, "Q ZONE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			questionPanel.setPreferredSize(new java.awt.Dimension(400, 400));
			getContentPane().add(questionPanel, BorderLayout.EAST);
			questionPanel.setLayout(new GridLayout(5, 1, 0, 0));
			
			lblNewLabel = new JLabel("Answer Questions will help your team win quickly");
			lblNewLabel.setForeground(SystemColor.controlHighlight);
			lblNewLabel.setFont(new Font("American Typewriter", Font.PLAIN, 15));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			questionPanel.add(lblNewLabel);

			LabelQuestion = new JLabel("");
			LabelQuestion.setToolTipText("The label to show the question");
			LabelQuestion.setFont(new Font("American Typewriter", Font.BOLD, 22));
			LabelQuestion.setHorizontalAlignment(SwingConstants.CENTER);
			questionPanel.add(LabelQuestion);

			textFieldAnswer = new JTextField();
			textFieldAnswer.setToolTipText("Please enter your answer here");
			textFieldAnswer.setFont(new Font("American Typewriter", Font.PLAIN, 22));
			questionPanel.add(textFieldAnswer);
			textFieldAnswer.setColumns(10);
			
			panel = new JPanel();
			panel.setBackground(SystemColor.textHighlight);
			panel.setBorder(new TitledBorder(null, "Have fun with teammate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			questionPanel.add(panel);
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			
			btnNewButtonQuestion = new JButton("Challenge Me");
			btnNewButtonQuestion.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					_adpt.askQuestion();
					btnNewButtonQuestion.setEnabled(false);;
				}
			});
			btnNewButtonQuestion.setFont(new Font("American Typewriter", Font.PLAIN, 16));
			panel.add(btnNewButtonQuestion);
			
			btnNewButtonAnswer = new JButton("Answer the Question and Rescue Friend");
			btnNewButtonAnswer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					_adpt.checkAnswer(textFieldAnswer.getText());
				}
			});
			btnNewButtonAnswer.setFont(new Font("American Typewriter", Font.PLAIN, 16));
			panel.add(btnNewButtonAnswer);
			
			panel_1 = new JPanel();
			panel_1.setBackground(SystemColor.textHighlight);
			questionPanel.add(panel_1);
			panel_1.setLayout(new GridLayout(0, 1, 0, 0));
			
			lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setFont(new Font("American Typewriter", Font.PLAIN, 15));
			panel_1.add(lblNewLabel_1);
			
			lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setFont(new Font("American Typewriter", Font.PLAIN, 15));
			panel_1.add(lblNewLabel_2);

			playerInfoPanel = new JPanel();
			playerInfoPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			playerInfoPanel.setBackground(SystemColor.textHighlight);
			FlowLayout playerInfoPanelLayout = new FlowLayout();
			playerInfoPanel.setLayout(playerInfoPanelLayout);
			playerInfoPanel.setPreferredSize(new Dimension(200, 70));

			getContentPane().add(playerInfoPanel, BorderLayout.NORTH);

			getContentPane().add(playerInfoPanel, BorderLayout.NORTH);
			
			lblNewLabel_3 = new JLabel("Welcome to Scavenger Hunt");
			lblNewLabel_3.setForeground(SystemColor.controlHighlight);
			lblNewLabel_3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			playerInfoPanel.add(lblNewLabel_3);

			// questionField = new JTextField();
			// questionPanel.add(questionField);
			// questionField.setText("QuestionHere");
			// questionField.setPreferredSize(new java.awt.Dimension(88, 22));

			pack();
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public void start() {
		_mapPanel.start();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setPosition(Position pos) {
		_mapPanel.setPosition(pos, true);
	}

	public void addMapLayer(MapLayer layer) {
		_mapPanel.addLayer(layer);
	}

	public void removeMapLayer(MapLayer layer) {
		_mapPanel.removeLayer(layer);
	}

	public void addPlace(CBType p) {
		_places.insertItemAt(p, 0);
		_places.setSelectedIndex(0);
	}
	
	public void displayQuestion(Question question) {
		LabelQuestion.setText(question.showQuestion());
	}

	public void displayNextFiled(LatLon nextFiled) {
		double help = 57.2958;
		DecimalFormat df4 = new DecimalFormat("##.0000");
		String latitue = df4.format(nextFiled.getLatitude().getDegrees() / help);
		String longitude = df4.format(nextFiled.getLongitude().getDegrees() / help);
		lblNewLabel_1.setText("The next point's latitude is : " + latitue);
		lblNewLabel_2.setText("The next point's longitude is: " + longitude);
		btnNewButtonQuestion.setEnabled(true);
//		lblNewLabel_1.setText(
//				"The next point's latitude is : " + latitue + "/n" + "The next point's longitude is: " + longitude);
	}

	public void displayErrorIn() {
		// TODO Auto-generated method stub
		lblNewLabel_1.setText("The answer is wrong, please try again!");
		
	}

	public void displayHitInfo() {
		// TODO Auto-generated method stub
		lblNewLabel_1.setText("CONGRATULATION!!! You hit the target!");
		lblNewLabel_2.setText("");
		
	}

	public void displayMissInfo() {
		// TODO Auto-generated method stub
		lblNewLabel_1.setText("Sorry ~ You need to try again!");
		lblNewLabel_2.setText("");
	}

}
