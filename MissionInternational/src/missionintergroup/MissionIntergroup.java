package missionintergroup;

import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;

import missionintergroup.MissionIntergroupUpdate.UpdateContent;

import com.google.gson.Gson;

/**
 * Mission class used for communication between the different organization's servers. Every organization has to use this to communicate with
 * each other. Use the updateMission() method to update an already existing mission.
 * @author robsi807
 *
 */
public class MissionIntergroup {
	private final MissionID id;
	private GPSCoordinate location;
	private String title, description;
	private Date creationTime;
	private LinkedList<MissionIntergroupUpdate> missionLog;
	private HashSet<MissionIntergroupListener> listeners;
	private int[] numberOfUnits;

	/**
	 * Mission object used for communication between the different organization's servers.
	 * @param id MÅSTE DEFINIERAS!!!!!!!!! UNIK FÖR VARJE ELLER DELAD?
	 * @param location describes the location of the mission as a GPSCoordinate
	 * @param title short description of the mission
	 * @param description more detailed description of the mission
	 * @param creationTime time when the mission is created
	 */
	public MissionIntergroup(MissionID id, GPSCoordinate location, String title,
			String description, Date creationTime) {
		this.id = id;
		this.location = location;
		this.title = title;
		this.description = description;
		this.creationTime = creationTime;
		missionLog = new LinkedList<MissionIntergroupUpdate>();
		numberOfUnits = new int[4]; // pos 0 = police, pos 1 = raddning, pos 2 =
									// militar, pos 3 = ambulans
		listeners = new HashSet<MissionIntergroupListener>();
		updateMission(new MissionIntergroupUpdate(getId(),
				UpdateContent.COMMENT, "Mission created"));
	}

	/**
	 * Updates the mission with a MissionIntergroupUpdate object. An update can only update a mission with the same ID.
	 * All updates are added to the missionlog.
	 * @param update
	 */
	public void updateMission(MissionIntergroupUpdate update) {
		if (getId().checkIfEqual(update.getMissionId())) {
			missionLog.add(update);
			processUpdate(update);
			notifyListeners(update);
		}
	}

	/*
	 * Process the update to only change the relevant information based on the Content of the update.
	 */
	private void processUpdate(MissionIntergroupUpdate update) {
		switch (update.getContent()) {
		case LOCATION:
			GPSCoordinate location = new Gson().fromJson(update.getNewValue(), GPSCoordinate.class);
			setLocation(location);
			break;
		case TITLE:
			setTitle(update.getNewValue());
			break;
		case DESCRIPTION:
			setDescription(update.getNewValue());
			break;
		case NR_OF_POLICE:
			setNumberOfPolice(update.getNewValue());
			break;
		case NR_OF_FIREBRIGADE:
			setNumberOfFirebrigade(update.getNewValue());
			break;
		case NR_OF_MILITARY:
			setNumberOfMilitary(update.getNewValue());
			break;
		case NR_OF_PARAMEDICS:
			setNumberOfParamedics(update.getNewValue());
			break;
		default:
			break;

		}

	}

	private void setNumberOfPolice(String nrOfPolice) throws NumberFormatException{
		numberOfUnits[0] = Integer.parseInt(nrOfPolice);
	}

	private void setNumberOfFirebrigade(String nrOfFirebridgade) throws NumberFormatException {
		numberOfUnits[1] = Integer.parseInt(nrOfFirebridgade);
	}

	private void setNumberOfMilitary(String nrOfMilitary) throws NumberFormatException {
		numberOfUnits[2] = Integer.parseInt(nrOfMilitary);
	}

	private void setNumberOfParamedics(String nrOfParamedics) throws NumberFormatException {
		numberOfUnits[3] = Integer.parseInt(nrOfParamedics);
	}

	/**
	 * @return the number of police units that are active on the mission
	 */
	public int getNumberOfPolice() {
		return numberOfUnits[0];
	}

	/**
	 * @return the number of firebrigade units that are active on the mission
	 */
	public int getNumberOfFirebrigade() {
		return numberOfUnits[1];
	}

	/**
	 * @return the number of military units that are active on the mission
	 */
	public int getNumberOfMilitary() {
		return numberOfUnits[2];
	}

	/**
	 * @return the number of paramedics that are active on the mission
	 */
	public int getNumberOfParamedics() {
		return numberOfUnits[3];
	}

	private void setLocation(GPSCoordinate location) {
		if(location != null)
		this.location = location;
	}

	public GPSCoordinate getLocation() {
		return location;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public MissionID getId() {
		return id;
	}

	/**
	 * 
	 * @return the detailed mission description
	 */
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return the current log of the mission as a HashSet <-? is this the vettigaste solösning?
	 */
	public LinkedList<MissionIntergroupUpdate> getMissionLog() {
		return missionLog;
	}

	private void notifyListeners(MissionIntergroupUpdate update) {
		for (MissionIntergroupListener listener : listeners) {
			listener.missionUpdated(update);
		}
	}

	/**
	 * Registers a listener to changes of the mission.
	 * @param listener
	 */
	public void addListener(MissionIntergroupListener listener) {
		listeners.add(listener);
	}

}
