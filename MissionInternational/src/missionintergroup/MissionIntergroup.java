package missionintergroup;

import java.sql.Time;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class MissionIntergroup {
	private final long id;
	private GPSCoordinate location;
	private String title, description;
	private Time creationTime;
	private LinkedHashSet<MissionIntergroupUpdate> missionLog;
	private HashSet<MissionIntergroupListener> listeners;
	private int[] numberOfUnits;

	/**
	 * 
	 * @param id
	 * @param longitude
	 * @param latitude
	 * @param title
	 * @param description
	 * @param creationTime
	 */
	public MissionIntergroup(long id, GPSCoordinate location,
			String title, String description, Time creationTime) {
		this.id = id;
		this.location = location;
		this.title = title;
		this.description = description;
		this.creationTime = creationTime;
		missionLog = new LinkedHashSet<MissionIntergroupUpdate>();
		numberOfUnits = new int[4]; // pos 0 = police, pos 1 = raddning, pos 2 =
									// militar, pos 3 = ambulans
		listeners = new HashSet<MissionIntergroupListener>();
	}

	/**
	 * 
	 * @param entry
	 */
	public void addMissionUpdate(MissionIntergroupUpdate update) {
		missionLog.add(update);
		processUpdate(update);
		notifyListeners();
	}

	private void processUpdate(MissionIntergroupUpdate update) {
		switch (update.getContent()) {
		case LOCATION:
			setLocation(update.getGPSCooordinate());
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

	// EXCEPTIONS AND STUFFS!
	private void setNumberOfPolice(String nrOfPolice) {
		numberOfUnits[0] = Integer.parseInt(nrOfPolice);
	}

	private void setNumberOfFirebrigade(String nrOfFirebridgade) {
		numberOfUnits[1] = Integer.parseInt(nrOfFirebridgade);
	}

	private void setNumberOfMilitary(String nrOfMilitary) {
		numberOfUnits[2] = Integer.parseInt(nrOfMilitary);
	}

	private void setNumberOfParamedics(String nrOfParamedics) {
		numberOfUnits[3] = Integer.parseInt(nrOfParamedics);
	}

	// EXCEPTIONS AND STUFFS!

	public int getNumberOfPolice() {
		return numberOfUnits[0];
	}

	public int getNumberOfFirebrigade() {
		return numberOfUnits[1];
	}

	public int getNumberOfMilitary() {
		return numberOfUnits[2];
	}

	public int getNumberOfParamedics() {
		return numberOfUnits[3];
	}

	public void setLocation(GPSCoordinate location) {
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

	public Time getCreationTime() {
		return creationTime;
	}

	private void setCreationTime(Time creationTime) {
		this.creationTime = creationTime;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public HashSet<MissionIntergroupUpdate> getMissionLog() {
		return missionLog;
	}

	private void notifyListeners() {
		for (MissionIntergroupListener listener : listeners) {
			listener.missionUpdated(this);
		}
	}

	/**
	 * 
	 * @param listener
	 */
	public void addListener(MissionIntergroupListener listener) {
		listeners.add(listener);
	}

}
