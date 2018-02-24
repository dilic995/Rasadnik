package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Account;

public interface AccountDAO {

	// CRUD methods
		public Account getByPrimaryKey(Integer accountID);
		
		public List<Account> selectAll();

		public List<Account> select(String whereStatement, Object[] bindVariables);

		public long selectCount();

		public long selectCount(String whereStatement, Object[] bindVariables);

		public int update(Account obj);

		public int insert(Account obj);

		public int delete(Account obj);

		// Finders
		public Account getByUsername(String username);

		public List<Account> getByHash(String hash);

		public List<Account> getByFirstLogin(Boolean firstLogin);
		
		public List<Account> getByIsAdmin(Boolean isAdmin);
		
		public List<Account> getByDeleted(Boolean deleted);
}
