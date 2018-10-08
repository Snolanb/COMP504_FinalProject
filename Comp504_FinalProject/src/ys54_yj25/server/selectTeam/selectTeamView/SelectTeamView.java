package ys54_yj25.server.selectTeam.selectTeamView;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SelectTeamView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4136055866110037847L;
	private JPanel contentPane;
	private final JPanel SelectPanel = new JPanel();
	private final JPanel CreatPanel = new JPanel();
	private final JPanel LablePanel = new JPanel();
	private final JLabel welcomeLable = new JLabel("WELCOME");
	private final JLabel explainLabel = new JLabel("Please Join Team");
	private final JLabel chooseTeamLabel = new JLabel("Choose a team: ");
	private final JComboBox comboBox = new JComboBox();
	private final JLabel CreateTeamLabel = new JLabel("Create a team: ");
	private final JTextField textField = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectTeamView frame = new SelectTeamView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelectTeamView() {
		textField.setColumns(10);
		initGUI();
	}
	
	public void start() {
		setVisible(true);
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		contentPane.add(LablePanel);
		LablePanel.setLayout(new GridLayout(2, 1, 0, 0));
		welcomeLable.setFont(new Font("American Typewriter", Font.PLAIN, 16));
		welcomeLable.setHorizontalAlignment(SwingConstants.CENTER);

		LablePanel.add(welcomeLable);
		explainLabel.setFont(new Font("American Typewriter", Font.PLAIN, 12));
		explainLabel.setHorizontalAlignment(SwingConstants.CENTER);

		LablePanel.add(explainLabel);
		SelectPanel.setBorder(new TitledBorder(null, "ChooseTeam", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(SelectPanel);
		SelectPanel.setLayout(new GridLayout(1, 2, 0, 0));
		chooseTeamLabel.setFont(new Font("American Typewriter", Font.PLAIN, 16));
		chooseTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);

		SelectPanel.add(chooseTeamLabel);

		SelectPanel.add(comboBox);
		CreatPanel.setBorder(new TitledBorder(null, "CreateTeam", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(CreatPanel);
		CreatPanel.setLayout(new GridLayout(1, 2, 0, 0));
		CreateTeamLabel.setFont(new Font("American Typewriter", Font.PLAIN, 16));
		CreateTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);

		CreatPanel.add(CreateTeamLabel);

		CreatPanel.add(textField);
	}

}
