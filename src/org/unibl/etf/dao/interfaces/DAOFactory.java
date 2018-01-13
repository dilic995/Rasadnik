package org.unibl.etf.dao.interfaces;

import java.sql.Connection;

public abstract class DAOFactory {
	private static DAOFactory singleton;

	public static DAOFactory getInstance() throws DAOException {
		try {
			if (null == singleton) {
				singleton = (DAOFactory) Class.forName("org.unibl.etf.dao.implementation.JDBCDAOFactory").newInstance();
			}
		} catch (Exception e) {
			throw new DAOException("Could not create the DAOFactory singleton", e);
		}

		return singleton;
	}

	public abstract BasisDAO getBasisDAO();

	public abstract BasisDAO getBasisDAO(Connection conn);

	public abstract ConditionDAO getConditionDAO();

	public abstract ConditionDAO getConditionDAO(Connection conn);

	public abstract CustomerDAO getCustomerDAO();

	public abstract CustomerDAO getCustomerDAO(Connection conn);

	public abstract EmployeeDAO getEmployeeDAO();

	public abstract EmployeeDAO getEmployeeDAO(Connection conn);

	public abstract EmployeeHasTaskDAO getEmployeeHasTaskDAO();

	public abstract EmployeeHasTaskDAO getEmployeeHasTaskDAO(Connection conn);

	public abstract EventDAO getEventDAO();

	public abstract EventDAO getEventDAO(Connection conn);

	public abstract PlantDAO getPlantDAO();

	public abstract PlantDAO getPlantDAO(Connection conn);

	public abstract PlantMaintanceActivityDAO getPlantMaintanceActivityDAO();

	public abstract PlantMaintanceActivityDAO getPlantMaintanceActivityDAO(Connection conn);

	public abstract PriceHeightRatioDAO getPriceHeightRatioDAO();

	public abstract PriceHeightRatioDAO getPriceHeightRatioDAO(Connection conn);

	public abstract PricelistDAO getPricelistDAO();

	public abstract PricelistDAO getPricelistDAO(Connection conn);

	public abstract PricelistHasPlantDAO getPricelistHasPlantDAO();

	public abstract PricelistHasPlantDAO getPricelistHasPlantDAO(Connection conn);

	public abstract PurchaseDAO getPurchaseDAO();

	public abstract PurchaseDAO getPurchaseDAO(Connection conn);

	public abstract RegionDAO getRegionDAO();

	public abstract RegionDAO getRegionDAO(Connection conn);

	public abstract ReproductionCuttingDAO getReproductionCuttingDAO();

	public abstract ReproductionCuttingDAO getReproductionCuttingDAO(Connection conn);

	public abstract SaleDAO getSaleDAO();

	public abstract SaleDAO getSaleDAO(Connection conn);

	public abstract SaleItemDAO getSaleItemDAO();

	public abstract SaleItemDAO getSaleItemDAO(Connection conn);

	public abstract TaskDAO getTaskDAO();

	public abstract TaskDAO getTaskDAO(Connection conn);

	public abstract ToolDAO getToolDAO();

	public abstract ToolDAO getToolDAO(Connection conn);

	public abstract ToolItemDAO getToolItemDAO();

	public abstract ToolItemDAO getToolItemDAO(Connection conn);

	public abstract ToolMaintanceActivityDAO getToolMaintanceActivityDAO();

	public abstract ToolMaintanceActivityDAO getToolMaintanceActivityDAO(Connection conn);

	public abstract TransactionDAO getTransactionDAO();

	public abstract TransactionDAO getTransactionDAO(Connection conn);
}