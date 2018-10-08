package ys54_yj25.server.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import common.IChatRoom;
import common.IUser;
import util.MyUserListCellRenderer;
import ys54_yj25.client.team.teamView.ChatRoomMiniFrame1;
import ys54_yj25.object.user.User;

/**
 * 
 * The main view of the chat app
 * 
 * @author yuejiang
 *
 * @param <TUser>
 *            The user droplist item
 * @param <TChatRoom>
 *            The chatroom droplist item
 */
public class ServerView<TChatRoom> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4985606470109018511L;
	/**
	 * The serialVersionUID of this GUI
	 */

	private JPanel contentPane;
	private final JPanel controlPanel = new JPanel();

	// private IMainViewToMainModelAdapter<TUser, TChatRoom>
	// mainViewToMainModelAdapter;
	private IServerView2ModelAdapter serverView2ModelAdapter;
	private final JPanel panelUserInfo = new JPanel();
	private final JPanel panelMakeRoom = new JPanel();
	private final JPanel panelConnectToRemote = new JPanel();
	private final JPanel panelConnectedHosts = new JPanel();
	private final JTextField textFieldIPAddress = new JTextField();
	private final JLabel lblUsername = new JLabel("UserName:");
	private final JLabel labelIPAddress = new JLabel("IP Address");
	//private final JButton buttonStartUser = new JButton("Login");
	private final JLabel lableFormat1 = new JLabel("");
	private final JLabel labelCreateChatRoom = new JLabel("Team Name :");
	private final JTextField textFieldChatRoomName = new JTextField();
	private final JButton buttonCreateRoom = new JButton("Create Team");
	private final JLabel labelConnectTo = new JLabel("Connect To :");
	private final JTextField textFieldConnectToRemote = new JTextField();
	private final JButton buttonConnectToRemote = new JButton("Connect");
	private final JLabel lableConnectedUserList = new JLabel("Connected Team List:");
	//private final JComboBox<TUser> comboBoxConnectedUsers = new JComboBox<>();
	private final JComboBox<TChatRoom> comboBoxConnectedUsers = new JComboBox<>();
	//private final JButton buttonRequestRoom = new JButton("Request Teams");
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelDefaultInfo = new JPanel();
	private final JScrollPane scrollPaneInfo = new JScrollPane();
	private final JTextArea textAreaInfo = new JTextArea();
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JButton btnStartGame = new JButton("Start Game");

	// private final JPanel panelTest1 = new JPanel();

	/**
	 * Create the frame.
	 * 
	 * @param viewToModelAdapter
	 *            the view to model adapter
	 */
	public ServerView(final IServerView2ModelAdapter serverView2ModelAdapter) {
		this.serverView2ModelAdapter = serverView2ModelAdapter;
		initGUI();
	}

	/**
	 * Initial the GUI
	 */
	private void initGUI() {
		setTitle("Game Server");
		textFieldChatRoomName.setToolTipText("Enter the room name");
		textFieldChatRoomName.setColumns(10);
		textFieldIPAddress.setToolTipText("Show the IP Address");
		textFieldIPAddress.setEnabled(false);
		textFieldIPAddress.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(controlPanel, BorderLayout.NORTH);
		controlPanel.setLayout(new GridLayout(0, 4, 0, 0));
		panelUserInfo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "ServerInfo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		controlPanel.add(panelUserInfo);
		panelUserInfo.setLayout(new GridLayout(0, 1, 0, 0));
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		labelIPAddress.setFont(new Font("American Typewriter", Font.PLAIN, 15));
		labelIPAddress.setHorizontalAlignment(SwingConstants.CENTER);

		panelUserInfo.add(labelIPAddress);

		panelUserInfo.add(textFieldIPAddress);

		panelUserInfo.add(lableFormat1);
//		buttonStartUser.setToolTipText("Click to create a new user");
//
//		// Start Button
//		buttonStartUser.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				mainViewToMainModelAdapter.startAndCreateUser(textFieldUserName.getText());
//				buttonStartUser.setEnabled(false);
//				buttonConnectToRemote.setEnabled(true);
//				buttonCreateRoom.setEnabled(true);
//				buttonJoinChatRoom.setEnabled(true);
//				buttonRequestRoom.setEnabled(true);
//			}
//		});

		//panelUserInfo.add(buttonStartUser);
		textFieldConnectToRemote.setToolTipText("Enter a remote IP address");
		textFieldConnectToRemote.setColumns(10);
		panelConnectToRemote.setBorder(
				new TitledBorder(null, "ConnectToRemote", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controlPanel.add(panelConnectToRemote);
		panelConnectToRemote.setLayout(new GridLayout(0, 2, 0, 0));
		labelConnectTo.setFont(new Font("American Typewriter", Font.PLAIN, 15));
		labelConnectTo.setHorizontalAlignment(SwingConstants.CENTER);

		panelConnectToRemote.add(labelConnectTo);

		panelConnectToRemote.add(textFieldConnectToRemote);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverView2ModelAdapter.startGame();
			}
		});
		
		panelConnectToRemote.add(btnStartGame);
		buttonConnectToRemote.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		buttonConnectToRemote.setEnabled(false);
		buttonConnectToRemote.setToolTipText("Connect to the remote user");
//		buttonConnectToRemote.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				mainViewToMainModelAdapter.connectToRemote(textFieldConnectToRemote.getText());
//			}
//		});

		panelConnectToRemote.add(buttonConnectToRemote);
		panelMakeRoom.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "MakeTeam",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		controlPanel.add(panelMakeRoom);
		panelMakeRoom.setLayout(new GridLayout(0, 2, 0, 0));
		labelCreateChatRoom.setFont(new Font("American Typewriter", Font.PLAIN, 15));
		labelCreateChatRoom.setHorizontalAlignment(SwingConstants.CENTER);

		panelMakeRoom.add(labelCreateChatRoom);

		panelMakeRoom.add(textFieldChatRoomName);
		buttonCreateRoom.setFont(new Font("American Typewriter", Font.PLAIN, 13));
		buttonCreateRoom.setEnabled(false);
		buttonCreateRoom.setToolTipText("Click to create a new team");
		buttonCreateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mainViewToMainModelAdapter.createChatRoom(textFieldChatRoomName.getText());
				textFieldChatRoomName.setText("");
			}
		});
		
		panelMakeRoom.add(lblNewLabel_1);

		panelMakeRoom.add(buttonCreateRoom);
		panelConnectedHosts.setBorder(
				new TitledBorder(null, "ConnectedHosts", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		controlPanel.add(panelConnectedHosts);
		panelConnectedHosts.setLayout(new GridLayout(0, 1, 0, 0));
		lableConnectedUserList.setFont(new Font("American Typewriter", Font.PLAIN, 15));

		panelConnectedHosts.add(lableConnectedUserList);
		comboBoxConnectedUsers.setToolTipText("The droplist of all connect users");

		panelConnectedHosts.add(comboBoxConnectedUsers);
//		buttonRequestRoom.setFont(new Font("American Typewriter", Font.PLAIN, 13));
//		buttonRequestRoom.setEnabled(false);
//		buttonRequestRoom.setToolTipText("Click to request chat rooms from the user");
//		buttonRequestRoom.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				mainViewToMainModelAdapter
//						.requestChatRooms(comboBoxConnectedUsers.getItemAt(comboBoxConnectedUsers.getSelectedIndex()));
//				validate();
//			}
//		});

//		panelConnectedHosts.add(buttonRequestRoom);

		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Info", null, panelDefaultInfo, null);

		panelDefaultInfo.add(scrollPaneInfo, BorderLayout.CENTER);

		scrollPaneInfo.setPreferredSize(new Dimension(1300, 500));
		textAreaInfo.setToolTipText("The default info");

		scrollPaneInfo.setViewportView(textAreaInfo);

	}

	/**
	 * start the GUI.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Append system default info
	 * 
	 * @param textInfo
	 *            the system info
	 */
	public void appendInfo(String textInfo) {
		textAreaInfo.append(textInfo);
		// this.textAreaDisplay.setCaretPosition(this.textAreaDisplay.getText().length());
		textAreaInfo.setCaretPosition(this.textAreaInfo.getText().length());

	}

	/**
	 * Display the IP address
	 * 
	 * @param iPString
	 *            local IP address
	 */
	public void displayIPAddress(String iPString) {
		textFieldIPAddress.setText(iPString);
	}

	/**
	 * Delete the mini chat room from the tab
	 * 
	 * @param chatRoom
	 *            the chat room need to be deleted
	 */
	public void deleteChatRoomMVC(IChatRoom chatRoom) {
		// TODO Auto-generated method stub
		int index = tabbedPane.indexOfTab(chatRoom.getName());
		System.out.println("The index is ===>" + index);
		tabbedPane.remove(index);
		// if (index > 0) {
		// tabbedPane.setSelectedIndex(index - 1);
		// }
	}

	/**
	 * Add the mini chat room to the tab
	 * 
	 * @param crMiniView
	 *            the new chat room
	 */
	public void addChatRoomTab(ChatRoomMiniFrame1<IUser> crMiniView) {
		tabbedPane.addTab(crMiniView.getChatRoomName(), null, crMiniView, null);
		tabbedPane.setSelectedComponent(crMiniView);
	}

	/**
	 * Select the desired chat room
	 * 
	 * @param chatRoom
	 *            the desired chat room
	 */
	public void chooseChatRoom(IChatRoom chatRoom) {
		// TODO Auto-generated method stub
		int index = tabbedPane.indexOfTab(chatRoom.getName());
		tabbedPane.setSelectedIndex(index);
	}

	// @SuppressWarnings("unchecked")
	// public void pullConnectUsers(IUser localUser) {
	// comboBoxConnectedUsers.removeAllItems();
	// for (IUser user : ((User) localUser).getRemoteUserStubs()) {
	// try {
	// user.getChatRooms();
	// comboBoxConnectedUsers.addItem((TUser) user);
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// }
	// }
	// if (comboBoxConnectedUsers.getItemCount() > 0) {
	// comboBoxConnectedUsers.setSelectedIndex(0);
	// }
	// comboBoxConnectedUsers.setRenderer(new MyUserListCellRenderer());
	// comboBoxConnectedUsers.repaint();
	// }

//	@SuppressWarnings("unchecked")
//	public void pullChatRooms(Collection<IChatRoom> chatRooms) {
//		comboBoxChatRoomLists.removeAllItems();
//		for (IChatRoom chatrooms : chatRooms) {
//			comboBoxChatRoomLists.addItem((TChatRoom) chatrooms);
//		}
//		if (comboBoxChatRoomLists.getItemCount() > 0) {
//			comboBoxChatRoomLists.setSelectedIndex(0);
//		}
//		comboBoxChatRoomLists.setRenderer(new MyChatRoomListCellRenderer());
//	}
	
	
}
