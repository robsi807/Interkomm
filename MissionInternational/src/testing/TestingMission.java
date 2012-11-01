package testing;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import missionintergroup.GPSCoordinate;
import missionintergroup.MissionID;
import missionintergroup.MissionIntergroup;
import missionintergroup.MissionIntergroupListener;
import missionintergroup.MissionIntergroupUpdate;
import missionintergroup.MissionIntergroupUpdate.UpdateContent;

public class TestingMission implements MissionIntergroupListener {

	MissionIntergroup mission;

	JTextArea txtEdit;
	JFrame frame;
	JLabel lblTitle;
	JLabel lblId, lblLocation, lblDesc, lblTime;
	JTextArea txtLog;
	UpdateContent thatContent = UpdateContent.TITLE;

	public static void main(String[] args) {
		Date d = new Date(System.currentTimeMillis());
		System.out.println(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(d));
		new TestingMission();
	}

	public TestingMission() {
		mission = new MissionIntergroup(new MissionID('r', 74891272323l), new GPSCoordinate(
				10.123535, -3.508903), "Brand",
				"Stor brand i radhus. 2 personer i fara.", new Date(System.currentTimeMillis()));
		mission.addListener(this);
		createGUI();
	}

	private void createGUI() {
		frame = new JFrame("Testing mission");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel labelPanel = new JPanel();
		frame.setContentPane(mainPanel);

		mainPanel.add(labelPanel, BorderLayout.NORTH);

		allTheTexts();

		labelPanel.add(lblTitle);
		labelPanel.add(lblTime);
		labelPanel.add(lblDesc);
		labelPanel.add(lblLocation);
		labelPanel.add(lblId);

		JPanel editPanel = new JPanel();
		txtEdit = new JTextArea("Edit the title!");
		JButton btnEdit = new JButton("Edit!");
		editPanel.add(txtEdit);
		editPanel.add(btnEdit);

		btnEdit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				editPressed();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		JButton btnChangeContent = new JButton("that description");
		editPanel.add(btnChangeContent);
		btnChangeContent.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				thatContentPressed();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		mainPanel.add(editPanel, BorderLayout.CENTER);

		JPanel logPanel = new JPanel();
		txtLog = new JTextArea(10, 10);
		txtLog.setEditable(false);
		updateLog(null);
		logPanel.add(txtLog);
		
		mainPanel.add(logPanel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private void allTheTexts() {
		lblId = new JLabel("ID: " + mission.getId().idToString());
		lblLocation = new JLabel("@long: "
				+ mission.getLocation().getLongitude() + " lat: "
				+ mission.getLocation().getLatitude());
		lblTitle = new JLabel(mission.getTitle());
		lblDesc = new JLabel(mission.getDescription());
		lblTime = new JLabel(mission.getCreationTime().toString());
	}

	private void thatContentPressed() {
		thatContent = UpdateContent.COMMENT;
	}

	private void editPressed() {

		updateCurrentMission(new MissionIntergroupUpdate(mission.getId(),
				thatContent, txtEdit.getText()));

		updateAllText();
		frame.repaint();

	}

	private void updateAllText() {
		lblDesc.setText(mission.getDescription());
		lblLocation.setText("@long: " + mission.getLocation().getLongitude()
				+ " lat: " + mission.getLocation().getLatitude());
		lblTitle.setText(mission.getTitle());
	}

	private void updateCurrentMission(MissionIntergroupUpdate update) {
		mission.updateMission(update);
	}

	@Override
	public void missionUpdated(MissionIntergroupUpdate updatedMission) {
		updateLog(updatedMission);
	}

	private void updateLog(MissionIntergroupUpdate update) {
		txtLog.setText("");
		
		for (MissionIntergroupUpdate up : mission.getMissionLog()) {
			if (up.getContent() == UpdateContent.COMMENT) {
				txtLog.setText( txtLog.getText()+ "<" + up.getTimestamp() + ">" + up.getNewValue() + "\n");
			} else {
				txtLog.setText(txtLog.getText() + "<" + up.getTimestamp() + ">" + up.getContent().toString()
						+ " is set to " + up.getNewValue() + "\n");
			}
		}
		
	}
}
