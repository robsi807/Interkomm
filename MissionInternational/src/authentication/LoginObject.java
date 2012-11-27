package authentication;

import java.io.Serializable;

/**
 * Skickar detta object till servern. Servern identifierar att det är ett
 * loginobject som kommer in med objectStreamen (instanceof) och lagrar socketen
 * tillsammans med organisationen i en hashmap. Hashmapen används för att
 * referera till outputstream där object skrivs över nätverket.
 * 
 * @author robsi807
 * 
 */
public class LoginObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private char organizationChar;

	public LoginObject(char organizationChar) {
		this.organizationChar = organizationChar;
	}

	public char getOrganizationChar() {
		return organizationChar;
	}

}
