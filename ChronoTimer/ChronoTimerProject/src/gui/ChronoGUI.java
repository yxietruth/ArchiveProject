package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import chronoTimerMain.simulator.hardwareHandler.ChronoHardwareHandler;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Insets;
import javax.swing.SwingConstants;
/**
 * Built with windowbuilderpro - official eclipse WYSIWYG plugin
 * @author Jason
 *
 */
public class ChronoGUI {
	private String keypadEntry = "";
	private int numberArgumentsRemaining;
	private JFrame frame;
	private ChronoHardwareHandler hardware;
	String[] args=new String[2];
	String timestamp="";
	String command;
	private Vector<JComboBox<String>> channelType = new Vector<JComboBox<String>>();
	private Vector<JRadioButton> channels = new Vector<JRadioButton>();
	private Vector<JRadioButton> enDiss = new Vector<JRadioButton>();
	private ScrollPane displayPane;
	private ScrollPane printerPane;
	private JTextField keypadText;
	private JTextArea displayTextArea;
	private ArrayList<JToggleButton> commands = new ArrayList<JToggleButton>();
	private int cmdIndex = 0;
	private JLabel raceType;
	private JButton funButton;
	private JButton cmdButton;
	private JTextField commandText;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChronoGUI window = new ChronoGUI();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public ChronoGUI() {
		hardware = new ChronoHardwareHandler();
		for(int i = 0; i < 9; i++) {
			channels.add(new JRadioButton("placeholder"));
			channelType.add(new JComboBox<String>());
			enDiss.add(new JRadioButton("placeholder"));
		}
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(245, 255, 250));
		frame.setBounds(100, 100, 800, 744);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel MainFramePanel = new JPanel();
		MainFramePanel.setBackground(new Color(211, 211, 211));
		frame.getContentPane().add(MainFramePanel, BorderLayout.CENTER);
		MainFramePanel.setLayout(null);
		
		JToggleButton btnPower = new JToggleButton("POWER");
		btnPower.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!btnPower.isSelected()) {//pressed does not change state before running
					btnPower.setBackground(new Color(152,251,152));
					funButton.setEnabled(true);
					cmdButton.setEnabled(true);
				}
				else {
					btnPower.setBackground(new Color(192,192,192));
					funButton.setEnabled(false);
					cmdButton.setEnabled(false);
				}
				sendToHardware("POWER", "", "");
			}
		});
		btnPower.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPower.setBackground(new Color(192, 192, 192));
		btnPower.setBounds(12, 26, 143, 67);
		MainFramePanel.add(btnPower);
		//btnFunction.addActionListener(new commandListener("Function"));
		
		JButton btnfunction = new JButton("FUNCTION");
		btnfunction.setEnabled(false);
		btnfunction.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnfunction.setBackground(new Color(192, 192, 192));
		btnfunction.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				updateCMDSelect();
				
			}

		});
		btnfunction.setBounds(12, 236, 143, 67);
		funButton = btnfunction;
		MainFramePanel.add(btnfunction);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBorder(null);
		panel.setBounds(274, 283, 219, 250);
		MainFramePanel.add(panel);
		panel.setLayout(null);
		//btnSwap.addActionListener(new commandListener("Swap"));
		
		ScrollPane displayScrollPane = new ScrollPane();
		displayScrollPane.setBounds(10, 10, 199, 230);
		panel.add(displayScrollPane);
		displayScrollPane.setBackground(new Color(245, 245, 245));
		displayPane = displayScrollPane;
		
		JTextArea displaytext = new JTextArea();
		displaytext.setMargin(new Insets(50, 50, 50, 50));
		displaytext.setBackground(new Color(245, 245, 245));
		displaytext.setTabSize(5);
		displaytext.setBounds(12, 13, 197, 224);
		panel.add(displaytext);
		displayTextArea = displaytext;
		
		JTextArea txtQueueRunning = new JTextArea();
		txtQueueRunning.setBackground(new Color(211, 211, 211));
		txtQueueRunning.setEditable(false);
		txtQueueRunning.setText("Queue / Running / Final Time");
		txtQueueRunning.setBounds(262, 535, 240, 22);
		MainFramePanel.add(txtQueueRunning);
		
		JButton btnstart1 = new JButton("");
		btnstart1.setIcon(null);
		btnstart1.setForeground(new Color(0, 128, 0));
		btnstart1.setBackground(new Color(0, 128, 0));
		btnstart1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "1", "");
			}
		});
		btnstart1.setBounds(285, 106, 43, 25);
		MainFramePanel.add(btnstart1);
		//btnstart1.addActionListener(new manualStartFin('1'));
		
		JButton btnstart3 = new JButton("");
		btnstart3.setIcon(null);
		btnstart3.setForeground(new Color(0, 128, 0));
		btnstart3.setBackground(new Color(0, 128, 0));
		btnstart3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "3", "");
			}
		});
		btnstart3.setBounds(340, 106, 43, 25);
		MainFramePanel.add(btnstart3);
		//btnstart3.addActionListener(new manualStartFin('3'));
		
		JButton btnstart5 = new JButton("");
		btnstart5.setIcon(null);
		btnstart5.setForeground(new Color(0, 128, 0));
		btnstart5.setBackground(new Color(0, 128, 0));
		btnstart5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "5", "");
			}
		});
		btnstart5.setBounds(395, 106, 43, 25);
		MainFramePanel.add(btnstart5);
		//btnstart5.addActionListener(new manualStartFin('5'));
		
		JButton btnstart7 = new JButton("");
		btnstart7.setIcon(null);
		btnstart7.setForeground(new Color(0, 128, 0));
		btnstart7.setBackground(new Color(0, 128, 0));
		btnstart7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "7", "");
			}
		});
		btnstart7.setBounds(450, 106, 43, 25);
		MainFramePanel.add(btnstart7);
		//btnstart7.addActionListener(new manualStartFin('7'));
		
		JTextArea txtrChronotimer = new JTextArea();
		txtrChronotimer.setBackground(new Color(211, 211, 211));
		txtrChronotimer.setFont(new Font("Monospaced", Font.BOLD, 24));
		txtrChronotimer.setText("ChronoTimer");
		txtrChronotimer.setBounds(309, 26, 184, 46);
		txtrChronotimer.setEditable(false);
		MainFramePanel.add(txtrChronotimer);
		
		JToggleButton btnPrinterPwr = new JToggleButton("PRINTER PWR");
		btnPrinterPwr.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(!btnPrinterPwr.isSelected()) {
					btnPrinterPwr.setBackground(new Color(152,251,152));
				}
				else {
					btnPrinterPwr.setBackground(new Color(192,192,192));
				}
				updatePrinterPowerValue(btnPrinterPwr.isSelected());
			}

		});
		btnPrinterPwr.setSelected(true);
		btnPrinterPwr.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPrinterPwr.setBackground(new Color(152,251,152));
		btnPrinterPwr.setBounds(617, 26, 155, 67);
		MainFramePanel.add(btnPrinterPwr);
//		btnPrinterPwr.addActionListener(new commandListener("Printer Pwr"));
		
		ScrollPane printerTapePane = new ScrollPane();
		printerTapePane.setBackground(Color.WHITE);
		printerTapePane.setBounds(559, 122, 184, 115);
		MainFramePanel.add(printerTapePane);
		printerPane = printerTapePane;
		
		
		ScrollPane printerBaseAreaPane = new ScrollPane();
		printerBaseAreaPane.setBackground(new Color(128, 128, 128));
		printerBaseAreaPane.setBounds(532, 182, 240, 89);
		MainFramePanel.add(printerBaseAreaPane);
		
		JButton btnfinish2 = new JButton("");
		btnfinish2.setForeground(new Color(255, 0, 0));
		btnfinish2.setIcon(null);
		btnfinish2.setBackground(new Color(255, 0, 0));
		btnfinish2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "2", "");
			}
		});
		btnfinish2.setBounds(285, 215, 43, 25);
		MainFramePanel.add(btnfinish2);
		//btnfinish2.addActionListener(new manualStartFin('2'));
				
		JButton btnfinish4 = new JButton("");
		btnfinish4.setForeground(new Color(255, 0, 0));
		btnfinish4.setIcon(null);
		btnfinish4.setBackground(new Color(255, 0, 0));
		btnfinish4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "4", "");
			}
		});
		btnfinish4.setBounds(340, 215, 43, 25);
		MainFramePanel.add(btnfinish4);
		//btnfinish4.addActionListener(new manualStartFin('4'));
		
		JButton btnfinish6 = new JButton("");
		btnfinish6.setForeground(new Color(255, 0, 0));
		btnfinish6.setIcon(null);
		btnfinish6.setBackground(new Color(255, 0, 0));
		btnfinish6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "6", "");
			}
		});
		btnfinish6.setBounds(395, 215, 43, 25);
		MainFramePanel.add(btnfinish6);
		//btnfinish6.addActionListener(new manualStartFin('6'));
		
		JButton btnfinish8 = new JButton("");
		btnfinish8.setForeground(new Color(255, 0, 0));
		btnfinish8.setIcon(null);
		btnfinish8.setBackground(new Color(255, 0, 0));
		btnfinish8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("trig", "8", "");
			}
		});
		btnfinish8.setBounds(450, 215, 43, 25);
		MainFramePanel.add(btnfinish8);
		//btnfinish8.addActionListener(new manualStartFin('8'));
		
		JRadioButton radbtnenable1 = new JRadioButton("New radio button");
		radbtnenable1.setEnabled(false);
		radbtnenable1.setBackground(new Color(211, 211, 211));
		radbtnenable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "1", "");
			}
		});
		radbtnenable1.setBounds(295, 140, 20, 25);
		MainFramePanel.add(radbtnenable1);
		enDiss.set(1, radbtnenable1);
		//radbtnenable1.addActionListener(new toggleListner('1',true) );
		
		JRadioButton radbtnenable3 = new JRadioButton("New radio button");
		radbtnenable3.setEnabled(false);
		radbtnenable3.setBackground(new Color(211, 211, 211));
		radbtnenable3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "3", "");
			}
		});
		radbtnenable3.setBounds(350, 140, 20, 25);
		MainFramePanel.add(radbtnenable3);
		enDiss.set(3, radbtnenable3);
		//radbtnenable1.addActionListener(new toggleListner('3',true) );
		
		JRadioButton radbtnenable5 = new JRadioButton("New radio button");
		radbtnenable5.setEnabled(false);
		radbtnenable5.setBackground(new Color(211, 211, 211));
		radbtnenable5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "5", "");
			}
		});
		radbtnenable5.setBounds(405, 140, 20, 25);
		MainFramePanel.add(radbtnenable5);
		enDiss.set(5, radbtnenable5);
		//radbtnenable1.addActionListener(new toggleListner('5',true) );
		
		JRadioButton radbtnenable7 = new JRadioButton("New radio button");
		radbtnenable7.setEnabled(false);
		radbtnenable7.setBackground(new Color(211, 211, 211));
		radbtnenable7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "7", "");
			}
		});
		radbtnenable7.setBounds(460, 140, 20, 25);
		MainFramePanel.add(radbtnenable7);
		enDiss.set(7, radbtnenable7);
		//radbtnenable1.addActionListener(new toggleListner('7',true) );
		
		JRadioButton radbtnenable2 = new JRadioButton("New radio button");
		radbtnenable2.setEnabled(false);
		radbtnenable2.setBackground(new Color(211, 211, 211));
		radbtnenable2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "2", "");
			}
		});
		radbtnenable2.setBounds(295, 249, 20, 25);
		MainFramePanel.add(radbtnenable2);
		enDiss.set(2, radbtnenable2);
		//radbtnenable1.addActionListener(new toggleListner('2',true) );
		
		JRadioButton radbtnenable4 = new JRadioButton("New radio button");
		radbtnenable4.setEnabled(false);
		radbtnenable4.setBackground(new Color(211, 211, 211));
		radbtnenable4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "4", "");
			}
		});
		radbtnenable4.setBounds(350, 249, 20, 25);
		MainFramePanel.add(radbtnenable4);
		enDiss.set(4, radbtnenable4);
		//radbtnenable1.addActionListener(new toggleListner('4',true) );
		
		JRadioButton radbtnenable6 = new JRadioButton("New radio button");
		radbtnenable6.setEnabled(false);
		radbtnenable6.setBackground(new Color(211, 211, 211));
		radbtnenable6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "6", "");
			}
		});
		radbtnenable6.setBounds(405, 249, 20, 25);
		MainFramePanel.add(radbtnenable6);
		enDiss.set(6, radbtnenable6);
		//radbtnenable1.addActionListener(new toggleListner('6',true) );
		
		JRadioButton radbtnenable8 = new JRadioButton("New radio button");
		radbtnenable8.setEnabled(false);
		radbtnenable8.setBackground(new Color(211, 211, 211));
		radbtnenable8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sendToHardware("tog", "8", "");
			}
		});
		radbtnenable8.setBounds(460, 249, 20, 25);
		MainFramePanel.add(radbtnenable8);
		enDiss.set(8, radbtnenable8);
		//radbtnenable1.addActionListener(new toggleListner('8',true) );
		
		JTextArea txtrStart = new JTextArea();
		txtrStart.setBackground(new Color(211, 211, 211));
		txtrStart.setEditable(false);
		txtrStart.setText("Start");
		txtrStart.setBounds(229, 106, 58, 22);
		MainFramePanel.add(txtrStart);
		
		JTextArea txtrFinish = new JTextArea();
		txtrFinish.setBackground(new Color(211, 211, 211));
		txtrFinish.setEditable(false);
		txtrFinish.setText("Finish");
		txtrFinish.setBounds(221, 218, 66, 22);
		MainFramePanel.add(txtrFinish);
		
		JTextArea txtrEnabledisable = new JTextArea();
		txtrEnabledisable.setBackground(new Color(211, 211, 211));
		txtrEnabledisable.setEditable(false);
		txtrEnabledisable.setText("Enable/Disable");
		txtrEnabledisable.setBounds(161, 141, 126, 16);
		MainFramePanel.add(txtrEnabledisable);
		
		JTextArea textED2 = new JTextArea();
		textED2.setBackground(new Color(211, 211, 211));
		textED2.setEditable(false);
		textED2.setText("Enable/Disable");
		textED2.setBounds(167, 242, 126, 16);
		MainFramePanel.add(textED2);
		
		JButton btnkeypad1 = new JButton("1");
		btnkeypad1.setBackground(new Color(192, 192, 192));
		btnkeypad1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateKeypad("1");
			}
		});
		btnkeypad1.setBounds(589, 304, 60, 60);
		MainFramePanel.add(btnkeypad1);
		//btnkeypad1.addActionListener(new optionsListener('1'));
		
		JButton btnkeypad2 = new JButton("2");
		btnkeypad2.setBackground(new Color(192, 192, 192));
		btnkeypad2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateKeypad("2");
			}
		});
		btnkeypad2.setBounds(650, 304, 60, 60);
		MainFramePanel.add(btnkeypad2);
		//btnkeypad2.addActionListener(new optionsListener('2'));
		
		JButton btnkeypad3 = new JButton("3");
		btnkeypad3.setBackground(new Color(192, 192, 192));
		btnkeypad3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateKeypad("3");
			}
		});
		btnkeypad3.setBounds(712, 304, 60, 60);
		MainFramePanel.add(btnkeypad3);
		//btnkeypad3.addActionListener(new optionsListener('3'));
		
		JButton btnkeypad4 = new JButton("4");
		btnkeypad4.setBackground(new Color(192, 192, 192));
		btnkeypad4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateKeypad("4");
			}
		});
		btnkeypad4.setBounds(589, 366, 60, 60);
		MainFramePanel.add(btnkeypad4);
		//btnkeypad4.addActionListener(new optionsListener('4'));
		
		JButton btnkeypad5 = new JButton("5");
		btnkeypad5.setBackground(new Color(192, 192, 192));
		btnkeypad5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				updateKeypad("5");
			}
		});
		btnkeypad5.setBounds(650, 366, 60, 60);
		MainFramePanel.add(btnkeypad5);
		//btnkeypad5.addActionListener(new optionsListener('5'));
		
		JButton btnkeypad6 = new JButton("6");
		btnkeypad6.setBackground(new Color(192, 192, 192));
		btnkeypad6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				updateKeypad("6");
			}
		});
		btnkeypad6.setBounds(712, 366, 60, 60);
		MainFramePanel.add(btnkeypad6);
		//btnkeypad6.addActionListener(new optionsListener('6'));
		
		JButton btnkeypad7 = new JButton("7");
		btnkeypad7.setBackground(new Color(192, 192, 192));
		btnkeypad7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				updateKeypad("7");
			}
		});
		btnkeypad7.setBounds(589, 425, 60, 60);
		MainFramePanel.add(btnkeypad7);
		//btnkeypad7.addActionListener(new optionsListener('7'));
				
		JButton btnkeypad8 = new JButton("8");
		btnkeypad8.setBackground(new Color(192, 192, 192));
		btnkeypad8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			
				updateKeypad("8");
			}
		});
		btnkeypad8.setBounds(650, 425, 60, 60);
		MainFramePanel.add(btnkeypad8);
		//btnkeypad8.addActionListener(new optionsListener('8'));
		
		JButton btnkeypad9 = new JButton("9");
		btnkeypad9.setBackground(new Color(192, 192, 192));
		btnkeypad9.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			
				updateKeypad("9");
			}
		});
		btnkeypad9.setBounds(712, 425, 60, 60);
		MainFramePanel.add(btnkeypad9);
		//btnkeypad9.addActionListener(new optionsListener('9'));
		
		JButton btnkeypadsDELETE = new JButton("DEL");
		btnkeypadsDELETE.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keypadEntry = keypadText.getText();
				keypadText.setText(deleteLastChar(keypadEntry));
			}

		});
		btnkeypadsDELETE.setBackground(new Color(192, 192, 192));
		btnkeypadsDELETE.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnkeypadsDELETE.setBounds(589, 488, 60, 60);
		MainFramePanel.add(btnkeypadsDELETE);
		//btnkeypadstar.addActionListener(new optionsListener('*'));
		
		
		JButton btnkeypad0 = new JButton("0");
		btnkeypad0.setBackground(new Color(192, 192, 192));
		btnkeypad0.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateKeypad("0");
			}
		});
		btnkeypad0.setBounds(650, 488, 60, 60);
		MainFramePanel.add(btnkeypad0);
		//btnkeypad0.addActionListener(new optionsListener('0'));
		
		JButton btnkeypadpound = new JButton("#");
		btnkeypadpound.setBackground(new Color(192, 192, 192));
		btnkeypadpound.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				handleCmdEntered();
				
			}
		});
		btnkeypadpound.setBounds(712, 488, 60, 60);
		MainFramePanel.add(btnkeypadpound);
		//btnkeypadpound.addActionListener(new optionsListener('#'));
		
		JPanel backviewpanel = new JPanel();
		backviewpanel.setBackground(new Color(169, 169, 169));
		backviewpanel.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		backviewpanel.setBounds(0, 570, 829, 147);
		MainFramePanel.add(backviewpanel);
		backviewpanel.setLayout(null);
		
		JTextArea txtrChan = new JTextArea();
		txtrChan.setBackground(new Color(169, 169, 169));
		txtrChan.setEditable(false);
		txtrChan.setText("Channels");
		txtrChan.setBounds(0, 13, 73, 22);
		backviewpanel.add(txtrChan);
		
		JRadioButton chan1 = new JRadioButton("New radio button");
		chan1.setBackground(new Color(169, 169, 169));
		chan1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(1).getSelectedItem().toString();
				if(!chan1.isSelected()) {
					sendToHardware("conn", type, "1");
					enDiss.get(1).setEnabled(true);
				}
				else {
					sendToHardware("disc", "1", "");
					enDiss.get(1).setEnabled(false);
				}
			}
		});
		chan1.setBounds(81, 44, 20, 25);
		backviewpanel.add(chan1);
		channels.set(1, chan1);
		//chan1.addActionListener(new toggleListner('1',false));
		
		JRadioButton chan3 = new JRadioButton("New radio button");
		chan3.setBackground(new Color(169, 169, 169));
		chan3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(3).getSelectedItem().toString();
				if(!chan3.isSelected()) {
					sendToHardware("conn", type, "3");
					enDiss.get(3).setEnabled(true);
				}
				else {
					sendToHardware("disc", "3", "");
					enDiss.get(3).setEnabled(false);
				}
			}
		});
		chan3.setBounds(166, 44, 20, 25);
		backviewpanel.add(chan3);
		channels.set(3, chan3);
	//	chan3.addActionListener(new toggleListner('3',false));
		
		JRadioButton chan5 = new JRadioButton("New radio button");
		chan5.setBackground(new Color(169, 169, 169));
		chan5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(5).getSelectedItem().toString();
				if(!chan5.isSelected()) {
					sendToHardware("conn", type, "5");
					enDiss.get(5).setEnabled(true);
				}
				else {
					sendToHardware("disc", "5", "");
					enDiss.get(5).setEnabled(false);
				}
			}
		});
		chan5.setBounds(251, 44, 20, 25);
		backviewpanel.add(chan5);
		channels.set(5, chan5);
		//chan5.addActionListener(new toggleListner('5',false));
		
		JRadioButton chan7 = new JRadioButton("New radio button");
		chan7.setBackground(new Color(169, 169, 169));
		chan7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(7).getSelectedItem().toString();
				if(!chan7.isSelected()) {
					sendToHardware("conn", type, "7");
					enDiss.get(7).setEnabled(true);
				}
				else {
					sendToHardware("disc", "7", "");
					enDiss.get(7).setEnabled(false);
				}
			}
		});
		chan7.setBounds(336, 44, 20, 25);
		backviewpanel.add(chan7);
		channels.set(7, chan7);
		//chan7.addActionListener(new toggleListner('7',false));
		
		JRadioButton chan2 = new JRadioButton("New radio button");
		chan2.setBackground(new Color(169, 169, 169));
		chan2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(2).getSelectedItem().toString();
				if(!chan2.isSelected()) {
					sendToHardware("conn", type, "2");
					enDiss.get(2).setEnabled(true);
				}
				else {
					sendToHardware("disc", "2", "");
					enDiss.get(2).setEnabled(false);
				}
			}
		});
		chan2.setBounds(81, 104, 20, 25);
		backviewpanel.add(chan2);
		channels.set(2, chan2);
//		chan2.addActionListener(new toggleListner('2',false));
		
		JRadioButton chan4 = new JRadioButton("New radio button");
		chan4.setBackground(new Color(169, 169, 169));
		chan4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(4).getSelectedItem().toString();
				if(!chan4.isSelected()) {
					sendToHardware("conn", type, "4");
					enDiss.get(4).setEnabled(true);
				}
				else {
					sendToHardware("disc", "4", "");
					enDiss.get(4).setEnabled(false);
				}
			}
		});
		chan4.setBounds(166, 104, 20, 25);
		backviewpanel.add(chan4);
		channels.set(4, chan4);
//		chan4.addActionListener(new toggleListner('4',false));
		
		JRadioButton chan6 = new JRadioButton("New radio button");
		chan6.setBackground(new Color(169, 169, 169));
		chan6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(6).getSelectedItem().toString();
				if(!chan6.isSelected()) {
					sendToHardware("conn", type, "6");
					enDiss.get(6).setEnabled(true);
				}
				else {
					sendToHardware("disc", "6", "");
					enDiss.get(6).setEnabled(false);
				}
			}
		});
		chan6.setBounds(251, 104, 20, 25);
		backviewpanel.add(chan6);
		channels.set(6, chan6);
//		chan6.addActionListener(new toggleListner('6',false));
		
		JRadioButton chan8 = new JRadioButton("New radio button");
		chan8.setBackground(new Color(169, 169, 169));
		chan8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String type = channelType.get(8).getSelectedItem().toString();
				if(!chan8.isSelected()) {
					sendToHardware("conn", type, "8");
					enDiss.get(8).setEnabled(true);
				}
				else {
					sendToHardware("disc", "8", "");
					enDiss.get(8).setEnabled(false);
				}
			}
		});
		chan8.setBounds(336, 104, 20, 25);
		backviewpanel.add(chan8);
		channels.set(8, chan8);
//		chan8.addActionListener(new toggleListner('8',false));
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(169, 169, 169));
		textArea_1.setEditable(false);
		textArea_1.setText("3");
		textArea_1.setBounds(166, 13, 12, 22);
		backviewpanel.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBackground(new Color(169, 169, 169));
		textArea_2.setEditable(false);
		textArea_2.setText("5");
		textArea_2.setBounds(251, 13, 12, 22);
		backviewpanel.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBackground(new Color(169, 169, 169));
		textArea_3.setEditable(false);
		textArea_3.setText("7");
		textArea_3.setBounds(336, 13, 12, 22);
		backviewpanel.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBackground(new Color(169, 169, 169));
		textArea_4.setEditable(false);
		textArea_4.setText("1");
		textArea_4.setBounds(81, 13, 12, 22);
		backviewpanel.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setBackground(new Color(169, 169, 169));
		textArea_5.setEditable(false);
		textArea_5.setText("2");
		textArea_5.setBounds(81, 73, 12, 22);
		backviewpanel.add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setBackground(new Color(169, 169, 169));
		textArea_6.setEditable(false);
		textArea_6.setText("4");
		textArea_6.setBounds(166, 73, 12, 22);
		backviewpanel.add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setBackground(new Color(169, 169, 169));
		textArea_7.setEditable(false);
		textArea_7.setText("6");
		textArea_7.setBounds(251, 73, 12, 22);
		backviewpanel.add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setBackground(new Color(169, 169, 169));
		textArea_8.setEditable(false);
		textArea_8.setText("8");
		textArea_8.setBounds(336, 73, 12, 22);
		backviewpanel.add(textArea_8);
		
		JToggleButton usbBooleantoggle = new JToggleButton("USB");
		usbBooleantoggle.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		usbBooleantoggle.setFont(new Font("Tahoma", Font.BOLD, 15));
		usbBooleantoggle.setBackground(new Color(192, 192, 192));
		usbBooleantoggle.setBounds(508, 44, 103, 25);
		backviewpanel.add(usbBooleantoggle);
		
		JComboBox<String> c1box = new JComboBox<String>();//Changed to be compatible with WindowBuilderPro - JASON
		c1box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(1).isSelected(), c1box.getSelectedItem().toString(), "1");
			}
		});
		
		c1box.setBackground(new Color(248, 248, 255));
		c1box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c1box.setBounds(105, 13, 53, 22);
		backviewpanel.add(c1box);
		channelType.set(1, c1box);
		
		JComboBox<String> c3box = new JComboBox<String>();//TODO finish cboxes 2-8 based on cbox1
		c3box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(3).isSelected(), c3box.getSelectedItem().toString(), "3");
			}
		});
		
		c3box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c3box.setBackground(new Color(248, 248, 255));
		c3box.setBounds(190, 13, 53, 22);
		backviewpanel.add(c3box);
		channelType.set(3, c3box);
		
		JComboBox<String> c5box = new JComboBox<String>();
		c5box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(5).isSelected(), c5box.getSelectedItem().toString(), "5");
			}
		});
		
		c5box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c5box.setBackground(new Color(248, 248, 255));
		c5box.setBounds(275, 13, 53, 22);
		backviewpanel.add(c5box);
		channelType.set(5, c5box);
		
		JComboBox<String> c7box = new JComboBox<String>();
		c7box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(7).isSelected(), c7box.getSelectedItem().toString(), "7");
			}
		});
		
		c7box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c7box.setBackground(new Color(248, 248, 255));
		c7box.setBounds(360, 13, 53, 22);
		backviewpanel.add(c7box);
		channelType.set(7, c7box);
		
		JComboBox<String> c2box = new JComboBox<String>();
		c2box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(2).isSelected(), c2box.getSelectedItem().toString(), "2");
			}
		});
		c2box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c2box.setBackground(new Color(248, 248, 255));
		c2box.setBounds(105, 73, 53, 22);
		backviewpanel.add(c2box);
		channelType.set(2, c2box);
		
		JComboBox<String> c4box = new JComboBox<String>();
		c4box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(4).isSelected(), c4box.getSelectedItem().toString(), "4");
			}
		});
		c4box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c4box.setBackground(new Color(248, 248, 255));
		c4box.setBounds(190, 73, 53, 22);
		backviewpanel.add(c4box);
		channelType.set(4, c4box);
		
		JComboBox<String> c6box = new JComboBox<String>();
		c6box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(6).isSelected(), c6box.getSelectedItem().toString(), "6");
			}
		});
		c6box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c6box.setBackground(new Color(248, 248, 255));
		c6box.setBounds(271, 73, 53, 22);
		backviewpanel.add(c6box);
		channelType.set(6, c6box);
		
		JComboBox<String> c8box = new JComboBox<String>();
		c8box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSensor(channels.get(8).isSelected(), c8box.getSelectedItem().toString(), "8");
			}
		});
		c8box.setModel(new DefaultComboBoxModel<String>(new String[] {"Eye", "Gate", "Pad"}));
		c8box.setBackground(new Color(248, 248, 255));
		c8box.setBounds(360, 73, 53, 22);
		backviewpanel.add(c8box);
		channelType.set(8, c8box);
		
		JTextArea starttxt1 = new JTextArea();
		starttxt1.setBackground(new Color(211, 211, 211));
		starttxt1.setEditable(false);
		starttxt1.setText("1");
		starttxt1.setBounds(295, 71, 20, 22);
		MainFramePanel.add(starttxt1);
		
		JTextArea starttxt3 = new JTextArea();
		starttxt3.setBackground(new Color(211, 211, 211));
		starttxt3.setEditable(false);
		starttxt3.setText("3");
		starttxt3.setBounds(350, 71, 20, 22);
		MainFramePanel.add(starttxt3);
		
		JTextArea starttxt5 = new JTextArea();
		starttxt5.setBackground(new Color(211, 211, 211));
		starttxt5.setEditable(false);
		starttxt5.setText("5");
		starttxt5.setBounds(405, 71, 20, 22);
		MainFramePanel.add(starttxt5);
		
		JTextArea starttxt7 = new JTextArea();
		starttxt7.setBackground(new Color(211, 211, 211));
		starttxt7.setEditable(false);
		starttxt7.setText("7");
		starttxt7.setBounds(460, 71, 20, 22);
		MainFramePanel.add(starttxt7);
		
		JTextArea starttxt2 = new JTextArea();
		starttxt2.setBackground(new Color(211, 211, 211));
		starttxt2.setEditable(false);
		starttxt2.setText("2");
		starttxt2.setBounds(295, 180, 24, 22);
		MainFramePanel.add(starttxt2);
		
		JTextArea starttxt4 = new JTextArea();
		starttxt4.setBackground(new Color(211, 211, 211));
		starttxt4.setEditable(false);
		starttxt4.setText("4");
		starttxt4.setBounds(350, 180, 24, 22);
		MainFramePanel.add(starttxt4);
		
		JTextArea starttxt6 = new JTextArea();
		starttxt6.setBackground(new Color(211, 211, 211));
		starttxt6.setEditable(false);
		starttxt6.setText("6");
		starttxt6.setBounds(405, 180, 24, 22);
		MainFramePanel.add(starttxt6);
		
		JTextArea starttxt8 = new JTextArea();
		starttxt8.setBackground(new Color(211, 211, 211));
		starttxt8.setEditable(false);
		starttxt8.setText("8");
		starttxt8.setBounds(460, 180, 24, 22);
		MainFramePanel.add(starttxt8);
		
		keypadText = new JTextField();
		keypadText.setHorizontalAlignment(SwingConstants.CENTER);
		keypadText.setEditable(false);
		keypadText.setMargin(new Insets(10, 10, 10, 10));
		keypadText.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		keypadText.setBounds(70, 452, 72, 31);
		MainFramePanel.add(keypadText);
		keypadText.setBackground(new Color(245, 245, 245));
		
		JButton btnEnter = new JButton("ENTER CMD");
		btnEnter.setEnabled(false);
		btnEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				handleCmdEntered();  
			}

		});
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEnter.setBackground(new Color(192, 192, 192));
		btnEnter.setBounds(12, 496, 143, 62);
		cmdButton = btnEnter;
		MainFramePanel.add(btnEnter);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setOpaque(false);
		textArea.setBounds(22, 323, 120, 123);
		MainFramePanel.add(textArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		panel_1.setBounds(23, 320, 119, 123);
		MainFramePanel.add(panel_1);
		panel_1.setLayout(null);
		
		JToggleButton tglbtnNum = new JToggleButton("num");
		tglbtnNum.setBorder(null);
		tglbtnNum.setFocusable(false);
		tglbtnNum.setSelected(true);
		tglbtnNum.setBounds(6, 18, 52, 15);
		panel_1.add(tglbtnNum);
		tglbtnNum.setBackground(new Color(53,151,255));
		tglbtnNum.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnNum);
		
		JToggleButton cmdnewrun = new JToggleButton("newrun");
		cmdnewrun.setBorder(null);
		cmdnewrun.setFocusable(false);
		cmdnewrun.setBounds(6, 38, 52, 15);
		panel_1.add(cmdnewrun);
		cmdnewrun.setBackground(new Color(245, 245, 245));
		cmdnewrun.setMargin(new Insets(2, 2, 2, 2));
		commands.add(cmdnewrun);
		
		JToggleButton tglbtnCancel = new JToggleButton("cancel");
		tglbtnCancel.setBorder(null);
		tglbtnCancel.setFocusable(false);
		tglbtnCancel.setBounds(6, 58, 52, 15);
		panel_1.add(tglbtnCancel);
		tglbtnCancel.setBackground(new Color(245, 245, 245));
		tglbtnCancel.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnCancel);
		
		JToggleButton tglbtnReset = new JToggleButton("reset");
		tglbtnReset.setBorder(null);
		tglbtnReset.setFocusable(false);
		tglbtnReset.setBounds(6, 78, 52, 15);
		panel_1.add(tglbtnReset);
		tglbtnReset.setBackground(new Color(245, 245, 245));
		tglbtnReset.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnReset);
		
		JToggleButton tglbtnClr = new JToggleButton("clr");
		tglbtnClr.setBorder(null);
		tglbtnClr.setFocusable(false);
		tglbtnClr.setBounds(6, 98, 52, 15);
		panel_1.add(tglbtnClr);
		tglbtnClr.setBackground(new Color(245, 245, 245));
		tglbtnClr.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnClr);
		
		JToggleButton tglbtnEndrun = new JToggleButton("endrun");
		tglbtnEndrun.setBorder(null);
		tglbtnEndrun.setFocusable(false);
		tglbtnEndrun.setBounds(62, 18, 52, 15);
		panel_1.add(tglbtnEndrun);
		tglbtnEndrun.setBackground(new Color(245, 245, 245));
		tglbtnEndrun.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnEndrun);
		
		JToggleButton tglbtnPrint = new JToggleButton("print");
		tglbtnPrint.setBorder(null);
		tglbtnPrint.setFocusable(false);
		tglbtnPrint.setBounds(62, 38, 52, 15);
		panel_1.add(tglbtnPrint);
		tglbtnPrint.setBackground(new Color(245, 245, 245));
		tglbtnPrint.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnPrint);
		
		JToggleButton tglbtnDnf = new JToggleButton("dnf");
		tglbtnDnf.setBorder(null);
		tglbtnDnf.setFocusable(false);
		tglbtnDnf.setBounds(62, 58, 52, 15);
		panel_1.add(tglbtnDnf);
		tglbtnDnf.setBackground(new Color(245, 245, 245));
		tglbtnDnf.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnDnf);
		
		JToggleButton tglbtnExport = new JToggleButton("export");
		tglbtnExport.setBorder(null);
		tglbtnExport.setFocusable(false);
		tglbtnExport.setBounds(62, 78, 52, 15);
		panel_1.add(tglbtnExport);
		tglbtnExport.setBackground(new Color(245, 245, 245));
		tglbtnExport.setMargin(new Insets(2, 2, 2, 2));
		commands.add(tglbtnExport);
		
		JToggleButton tglbtnEvent = new JToggleButton("event");
		tglbtnEvent.setBorder(null);
		tglbtnEvent.setFocusable(false);
		tglbtnEvent.setMargin(new Insets(2, 2, 2, 2));
		tglbtnEvent.setBackground(new Color(245, 245, 245));
		tglbtnEvent.setBounds(62, 98, 52, 15);
		panel_1.add(tglbtnEvent);
		commands.add(tglbtnEvent);
		
		JLabel rType = new JLabel("IND");
		rType.setFocusable(false);
		rType.setBackground(new Color(255, 255, 255));
		rType.setHorizontalTextPosition(SwingConstants.CENTER);
		rType.setHorizontalAlignment(SwingConstants.CENTER);
		rType.setBounds(47, 207, 79, 16);
		MainFramePanel.add(rType);
		raceType = rType;
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				sendToHardware("swap", "", "");
			}
		});
		btnSwap.setBounds(34, 169, 97, 25);
		MainFramePanel.add(btnSwap);
		
		JTextArea txtrConnectedSensor = new JTextArea();
		txtrConnectedSensor.setBackground(new Color(211, 211, 211));
		txtrConnectedSensor.setText("Connected Sensor");
		txtrConnectedSensor.setBounds(157, 159, 130, 16);
		MainFramePanel.add(txtrConnectedSensor);
		
		JTextArea textArea_9 = new JTextArea();
		textArea_9.setText("Connected Sensor");
		textArea_9.setBackground(new Color(211, 211, 211));
		textArea_9.setBounds(167, 259, 130, 16);
		MainFramePanel.add(textArea_9);
		
		commandText = new JTextField();
		commandText.setHorizontalAlignment(SwingConstants.CENTER);
		commandText.setText("num");
		commandText.setMargin(new Insets(10, 10, 10, 10));
		commandText.setEditable(false);
		commandText.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		commandText.setBackground(new Color(245, 245, 245));
		commandText.setBounds(12, 452, 58, 31);
		MainFramePanel.add(commandText);
		frame.setVisible(true);
	}

	public void sendToHardware(String cmd, String arg1, String arg2) {//sends GUI commands to hardwareHandler and updates GUI displays
		String [] updates = hardware.inputFromSimulator(cmd, new String[] {arg1, arg2}, "");
		if(!updates[0].equals("")) {
			updateDisplay(updates[0]);
		}
		if(!updates[1].equals("")) {
			updatePrinter(updates[1]);
		}
		//TODO update racetype.setText() with current racetype from hardware (hardwarehandler needs change)
	}
	public void updateDisplay(String s) {//Updates display scrollpane - JASON
		displayPane.removeAll();
		displayTextArea.setText(s);
		displayPane.add(displayTextArea);
		displayPane.repaint();
	}
	public void updatePrinter(String s) {//Updates printer tape - JASON
		printerPane.removeAll();
		printerPane.add(new JTextArea(s));
		printerPane.repaint();
	}
	public void updateKeypad(String s) {
		keypadText.setText(keypadText.getText()+s);
	}
	public void updateSensor(boolean conn, String type, String channel) {
		//TODO if true, disc previous sensor and conn new one of type on
		//channel using sendtohardware
		if(conn){
			sendToHardware("disc",channel,"");
			sendToHardware("CONN",type,channel);
		}
	}
	public void updatePrinterPowerValue(boolean selected) {
		// if true change hardwarehandler printerpower to 
		//true, else change to false
		if(selected){
			hardware.setPrinterPower(true);
		}else{
			hardware.setPrinterPower(false);
		}
	}
	private void handleCmdEntered() {
		// get command from commands[cmdIndex].getText().toString()
		//and the args from keypadEntry - call sendtohardware with them
		// reset keypadEntry to empty
		if(commands.get(cmdIndex).getText().toString().equalsIgnoreCase("event")) {
			handleEventChange();
		}
		else {
			sendToHardware(commands.get(cmdIndex).getText().toString(),keypadText.getText(),"");
			keypadText.setText("");
		}
	}
	private void handleEventChange() {
		switch(raceType.getText()) {
		case "IND":
			raceType.setText("PARIND");
			break;
		case "PARIND":
			raceType.setText("GRP");
			break;
		case "GRP":
			raceType.setText("PARGRP");
			break;
		case "PARGRP":
			raceType.setText("IND");
			break;
		default:
			//TODO PARGRP
			break;
		}
		sendToHardware("event", raceType.getText(), "");
		raceType.setText(hardware.getRaceType());
	}

	private String deleteLastChar(String keypadEntry) {
		// delete the last character and return the 
		// rest of the string or "" if already empty
		if(keypadEntry.length()>0){
			return(keypadEntry.substring(0,keypadEntry.length()-1));
		}
		return "";
	}

	private void updateCMDSelect() {
		//toggle current and next command in commands.
		
		commands.get(cmdIndex).doClick();
		commands.get(cmdIndex).setBackground(new Color(245,245,245));
		cmdIndex=(cmdIndex+1)%commands.size();
		commands.get(cmdIndex).doClick();
		commands.get(cmdIndex).setBackground(new Color(53,151,255));
		commandText.setText(commands.get(cmdIndex).getText());
	}
}
