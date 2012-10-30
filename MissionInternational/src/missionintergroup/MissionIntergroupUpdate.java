package missionintergroup;

import java.sql.Time;

/**
 * Class used to update missions.
 * @author robsi807
 *
 */
public class MissionIntergroupUpdate {
	private long missionId;
	private String newValue;
	private UpdateContent content;
	private GPSCoordinate location;
	private Time timestamp;

	/**
	 * Constructor used for updating all mission updates except Location updates. See the other constructor for details about updating the LOCATION.
	 * @param missionId the unique ID of the mission that you want to update
	 * @param content the type of content you want to update. See enum UpdateContent for details
	 * @param newValue the value of the update as a String. REMEMBER to write an integer as a String when updating NR_OF_... Else error will find you
	 */
	public MissionIntergroupUpdate(long missionId, UpdateContent content,
			String newValue) {
		this.missionId = missionId;
		this.content = content;
		this.newValue = newValue;
		
		if(content == UpdateContent.LOCATION){
			this.content = UpdateContent.COMMENT;
			System.out.println("THIS IS " + content.toString());
			this.newValue = "ERROR WRONG CONSTRUCTOR USED! Location was not updated! Value of the failed update was: " + newValue;
		}
		timestamp = new Time(System.currentTimeMillis());
	}

	/**
	 * This constructor is used to update the location of a mission. For updating other values then LOCATION, see the other constructor.
	 * @param missionId the unique ID of the mission that you want to update
	 * @param location the new location of a mission as a GPSCoordinate.
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

	/**
	 * 
	 * @return the value in the update
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * 
	 * @return what type of content that will be updated
	 */
	public UpdateContent getContent() {

		return content;
	}
	
	/**
	 * 
	 * @return the new GPSCoordinate for this update. If LOCATION is NOT changed it will return NULL
	 */
	public GPSCoordinate getGPSCooordinate(){
		return location;
	}
	
	/**
	 * 
	 * @return the time when the update was created
	 */
	public Time getTimestamp() {
		return timestamp;
	}

	/**
	 * The different types of possible updates. Update the LOCATION using the correct constructor (there is two of them, find it!) else errors will find you 
	 * @author robsi807
	 *
	 */
	public enum UpdateContent {
		LOCATION, TITLE, DESCRIPTION, COMMENT, NR_OF_POLICE, NR_OF_FIREBRIGADE, NR_OF_MILITARY, NR_OF_PARAMEDICS;
	}
}
