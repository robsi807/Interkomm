package missionintergroup;


public class MissionIntergroupUpdate {
	private long missionId;
	private String newValue;
	private UpdateContent content;
	private GPSCoordinate location;

	/**
	 * 
	 * @param missionId
	 * @param content
	 * @param newValue
	 */
	public MissionIntergroupUpdate(long missionId, UpdateContent content,
			String newValue) {
		this.missionId = missionId;
		this.content = content;
		this.newValue = newValue;
	}

	/**
	 * Use for update of LOCATION, and LOCATION ONLY. Deal with it..
	 * @param missionId
	 * @param location
	 */
	public MissionIntergroupUpdate(long missionId,
			GPSCoordinate location) {
		this.missionId = missionId;
		this.content = UpdateContent.LOCATION;
		this.location = location;
	}
	
	public long getMissionId() {
		return missionId;
	}

	public String getNewValue() {
		return newValue;
	}

	public UpdateContent getContent() {
		return content;
	}
	public GPSCoordinate getGPSCooordinate(){
		return location;
	}

	/**
	 * 
	 * @author robsi807
	 *
	 */
	public enum UpdateContent {
		LOCATION, TITLE, DESCRIPTION, COMMENT, NR_OF_POLICE, NR_OF_FIREBRIGADE, NR_OF_MILITARY, NR_OF_PARAMEDICS;
	}
}
