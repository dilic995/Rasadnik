package org.unibl.etf.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHash {
	private static BCryptHash instance=null;
	public static BCryptHash getInstance() {
		if(instance==null) {
			instance=new BCryptHash();
		}
		return instance;
	}
	public String hash(String password) {
		return BCrypt.hashpw(password,BCrypt.gensalt());
	}
	public Boolean check(String password,String hash) {
		if(hash==null) {
			return false;
		}
		return BCrypt.checkpw(password, hash);
	}
	public static void main(String[] args) {
		
	}
	
}
