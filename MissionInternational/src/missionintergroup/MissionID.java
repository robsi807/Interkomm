package missionintergroup;

public class MissionID {

	private char organizationChar;
	long id;

	public MissionID(char organizationChar, long id) {
		this.organizationChar = organizationChar;
		this.id = id;
	}

	public char getOrganizationChar() {
		return organizationChar;
	}

	public long getId() {
		return id;
	}

}
