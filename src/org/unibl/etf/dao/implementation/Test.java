package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Employee;

public class Test {

	public static void main(String[] args) {
		try {
			DAOFactory.getInstance().getEmployeeDAO().insert(new Employee(null, "Jovan", "Danilovic"));
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
