package ys54_yj25.client.team.teamView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import common.IComponentFactory;
import ys54_yj25.map.mapController.MapController;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

/**
 * The mini frame of the chat app
 * 
 * @author yuejiang
 *
 * @param <TUser>
 *            The user item
 */
public class ChatRoomMiniFrame1<TUser> extends JPanel {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -2277811802445936277L;
	// private final JScrollPane scrollPane = new JScrollPane();
	private final JSplitPane splitPane = new JSplitPane();
	private final JSplitPane splitPaneDisplayArea = new JSplitPane();
	private final JScrollPane scrollPaneUserList = new JScrollPane();
	private final JScrollPane scrollPaneDisplay = new JScrollPane();
	private final JPanel panel = new JPanel();
	private final JTextArea textAreaSendMsg = new JTextArea();
	private final JButton buttonSendText = new JButton("Send Text");
	private final JButton buttonSendImage = new JButton("Send Image");
	private final JButton buttonSendFile = new JButton("Start Game");
	private final JButton buttonExit = new JButton("Exit Room");
	private final JList<String> jlistUserList = new JList<>();
	private final JTextPane textPaneDisplay = new JTextPane();

	private String chatRoomName;
	private IMiniViewToModelAdapter miniViewToModelAdapter;
	
//	private IAppStart2Controller controller;

	public ChatRoomMiniFrame1(String name, IMiniViewToModelAdapter miniViewToModelAdapter) {
		this.chatRoomName = name;
		this.miniViewToModelAdapter = miniViewToModelAdapter;
		initGUI();
	}

	public String getChatRoomName() {
		return this.chatRoomName;
	}

	/**
	 * start the GUI.
	 */
	public void start() {
		setVisible(true);
	}

	private void initGUI() {
		setPreferredSize(new Dimension(1300, 500));

		// add(scrollPane);

		setLayout(new BorderLayout());
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.9);

		add(splitPane, BorderLayout.CENTER);
		splitPaneDisplayArea.setResizeWeight(0.05);

		splitPane.setLeftComponent(splitPaneDisplayArea);
		scrollPaneUserList.setViewportBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Team Members", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		splitPaneDisplayArea.setLeftComponent(scrollPaneUserList);
		jlistUserList.setToolTipText("The user list for this chatroom");

		scrollPaneUserList.setViewportView(jlistUserList);
		scrollPaneDisplay.setViewportBorder(
				new TitledBorder(null, "Display", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPaneDisplayArea.setRightComponent(scrollPaneDisplay);
		textPaneDisplay.setToolTipText("Display area");

		scrollPaneDisplay.setViewportView(textPaneDisplay);
		panel.setBorder(new TitledBorder(null, "Send Msg", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		splitPane.setRightComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		textAreaSendMsg.setToolTipText("Please enter the msg here");

		panel.add(textAreaSendMsg);
		buttonSendText.setToolTipText("Click to send text");
		buttonSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miniViewToModelAdapter.sendMessage(textAreaSendMsg.getText());
				textAreaSendMsg.setText(null);
				scrollPaneDisplay.revalidate();
				scrollPaneDisplay.repaint();
			}
		});

		panel.add(buttonSendText);
		buttonSendImage.setToolTipText("Click to send image");
		buttonSendImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "png", "bmp", "gif"));
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ImageIcon image = new ImageIcon(file.getAbsolutePath());
					miniViewToModelAdapter.sendImage(image);
				}
			}
		});

		panel.add(buttonSendImage);
		buttonSendFile.setToolTipText("Click to send files");
		buttonSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MapController controller = new MapController();
				controller.makeMapMVC();
				controller.startMap();
			}
		});

		panel.add(buttonSendFile);
		buttonExit.setToolTipText("Exit the current room");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miniViewToModelAdapter.exitRoom();
				
			}
		});

		panel.add(buttonExit);
	}
	
	public void showUserList(String[] userNames) {
		jlistUserList.setListData(userNames);
	}

	public void appendMessage(String text, String name) {
//		StringBuilder msgSB = new StringBuilder();
//		// msgSB.append(name).append(" send message: ").append(text);
//		msgSB.append(text);
//		textAreaDisplay.append(msgSB.toString() + "\n");
		StyledDocument doc = textPaneDisplay.getStyledDocument();		
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setFontSize(keyWord, 15);
		
		try {
			doc.insertString(doc.getLength(), text + "\n", keyWord);
		} catch(Exception e) {
			System.out.println("failed to append message to chat room");
			e.printStackTrace();
		}	
		this.textPaneDisplay.setCaretPosition(this.textPaneDisplay.getDocument().getLength());
	}
	
	public void buildComponent(IComponentFactory fac, String label) {
		Component comp = fac.makeComponent();
		StyledDocument doc = textPaneDisplay.getStyledDocument();
	    Style style = doc.addStyle(label, null);
	    StyleConstants.setComponent(style, comp);
	    try {
			doc.insertString(doc.getLength(), "\n", style);
		} catch(Exception e) {
			System.out.println("failed to append image to chat room");
			e.printStackTrace();
		}
	    this.textPaneDisplay.setCaretPosition(this.textPaneDisplay.getDocument().getLength());
	}
}
