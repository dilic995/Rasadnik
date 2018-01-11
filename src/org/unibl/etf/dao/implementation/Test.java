package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dto.Employee;

public class Test {

	public static void main(String[] args) {
		try {
			JDBCDAOFactory.getInstance().getEmployeeDAO().insert(new Employee(null, "Dragan", "Ilic"));
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
