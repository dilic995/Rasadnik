package org.unibl.etf.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

	private IntegerProperty accountId;
	private StringProperty username;
	private StringProperty hash;
	private BooleanProperty firstLogin;
	private BooleanProperty isAdmin;
	private BooleanProperty deleted;
	
	public Account() {
		super();
	}

	public Account(String username, String hash, Boolean firstLogin, Boolean isAdmin, Boolean deleted) {
		super();
		this.username = username == null ? null : new SimpleStringProperty(username);
		this.hash = hash == null ? null : new SimpleStringProperty(hash);
		this.firstLogin = firstLogin == null ? null : new SimpleBooleanProperty(firstLogin);
		this.isAdmin = isAdmin == null ? null : new SimpleBooleanProperty(isAdmin);
		this.deleted = deleted == null ? null : new SimpleBooleanProperty(deleted);
	}

	public Integer getAccountId() {
		if(this.accountId == null) {
			return null;
		}
		
		return this.accountId.get();
	}
	
	public void setAccountId(Integer accountId) {
		this.accountId = new SimpleIntegerProperty(accountId);
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username = new SimpleStringProperty(username);
	}
	
	public String getHash() {
		if(this.hash == null) {
			return null;
		}
		
		return this.hash.get();
	}
	
	public void setHash(String hash) {
		this.hash = new SimpleStringProperty(hash);
	}
	
	public Boolean getFirstLogin() {
		return firstLogin.get();
	}
	
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = new SimpleBooleanProperty(firstLogin);
	}
	
	public Boolean getIsAdmin() {
		return isAdmin.get();
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = new SimpleBooleanProperty(isAdmin);
	}

	public Boolean getDeleted() {
		return deleted.get();
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = new SimpleBooleanProperty(deleted);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
