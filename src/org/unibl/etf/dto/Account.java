package org.unibl.etf.dto;

public class Account {

	private Integer accountId;
	private String username;
	private String hash;
	private Boolean firstLogin;
	private Boolean isAdmin;
	private Boolean deleted;
	
	public Integer getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Boolean getFirstLogin() {
		return firstLogin;
	}
	
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", username=" + username + ", hash=" + hash + ", firstLogin="
				+ firstLogin + ", isAdmin=" + isAdmin + ", deleted=" + deleted + "]";
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
