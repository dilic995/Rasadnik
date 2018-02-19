package org.unibl.etf.dao.implementation;

import java.sql.Connection;

import org.unibl.etf.dao.interfaces.BasisDAO;
import org.unibl.etf.dao.interfaces.ConditionDAO;
import org.unibl.etf.dao.interfaces.CustomerDAO;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dao.interfaces.EmployeeDAO;
import org.unibl.etf.dao.interfaces.EmployeeHasTaskDAO;
import org.unibl.etf.dao.interfaces.EventDAO;
import org.unibl.etf.dao.interfaces.PlanDAO;
import org.unibl.etf.dao.interfaces.PlantDAO;
import org.unibl.etf.dao.interfaces.PlantMaintanceActivityDAO;
import org.unibl.etf.dao.interfaces.PriceHeightRatioDAO;
import org.unibl.etf.dao.interfaces.PricelistDAO;
import org.unibl.etf.dao.interfaces.PricelistHasPlantDAO;
import org.unibl.etf.dao.interfaces.PurchaseDAO;
import org.unibl.etf.dao.interfaces.RegionDAO;
import org.unibl.etf.dao.interfaces.ReproductionCuttingDAO;
import org.unibl.etf.dao.interfaces.SaleDAO;
import org.unibl.etf.dao.interfaces.SaleItemDAO;
import org.unibl.etf.dao.interfaces.TaskDAO;
import org.unibl.etf.dao.interfaces.ToolDAO;
import org.unibl.etf.dao.interfaces.ToolItemDAO;
import org.unibl.etf.dao.interfaces.ToolMaintanceActivityDAO;
import org.unibl.etf.dao.interfaces.TransactionDAO;

public class JDBCDAOFactory extends DAOFactory {
	public BasisDAO getBasisDAO() {
		return new BasisDAOImpl();
	}

	public BasisDAO getBasisDAO(Connection conn) {
		return new BasisDAOImpl(conn);
	}

	public ConditionDAO getConditionDAO() {
		return new ConditionDAOImpl();
	}

	public ConditionDAO getConditionDAO(Connection conn) {
		return new ConditionDAOImpl(conn);
	}

	public CustomerDAO getCustomerDAO() {
		return new CustomerDAOImpl();
	}

	public CustomerDAO getCustomerDAO(Connection conn) {
		return new CustomerDAOImpl(conn);
	}

	public EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}

	public EmployeeDAO getEmployeeDAO(Connection conn) {
		return new EmployeeDAOImpl(conn);
	}

	public EmployeeHasTaskDAO getEmployeeHasTaskDAO() {
		return new EmployeeHasTaskDAOImpl();
	}

	public EmployeeHasTaskDAO getEmployeeHasTaskDAO(Connection conn) {
		return new EmployeeHasTaskDAOImpl(conn);
	}

	public EventDAO getEventDAO() {
		return new EventDAOImpl();
	}

	public EventDAO getEventDAO(Connection conn) {
		return new EventDAOImpl(conn);
	}

	public PlantDAO getPlantDAO() {
		return new PlantDAOImpl();
	}

	public PlantDAO getPlantDAO(Connection conn) {
		return new PlantDAOImpl(conn);
	}
	
	public PlanDAO getPlanDAO() {
		return new PlanDAOImpl();
	}

	public PlanDAO getPlanDAO(Connection conn) {
		return new PlanDAOImpl(conn);
	}

	public PlantMaintanceActivityDAO getPlantMaintanceActivityDAO() {
		return new PlantMaintanceActivityDAOImpl();
	}

	public PlantMaintanceActivityDAO getPlantMaintanceActivityDAO(Connection conn) {
		return new PlantMaintanceActivityDAOImpl(conn);
	}

	public PriceHeightRatioDAO getPriceHeightRatioDAO() {
		return new PriceHeightRatioDAOImpl();
	}

	public PriceHeightRatioDAO getPriceHeightRatioDAO(Connection conn) {
		return new PriceHeightRatioDAOImpl(conn);
	}

	public PricelistDAO getPricelistDAO() {
		return new PricelistDAOImpl();
	}

	public PricelistDAO getPricelistDAO(Connection conn) {
		return new PricelistDAOImpl(conn);
	}

	public PricelistHasPlantDAO getPricelistHasPlantDAO() {
		return new PricelistHasPlantDAOImpl();
	}

	public PricelistHasPlantDAO getPricelistHasPlantDAO(Connection conn) {
		return new PricelistHasPlantDAOImpl(conn);
	}

	public PurchaseDAO getPurchaseDAO() {
		return new PurchaseDAOImpl();
	}

	public PurchaseDAO getPurchaseDAO(Connection conn) {
		return new PurchaseDAOImpl(conn);
	}

	public RegionDAO getRegionDAO() {
		return new RegionDAOImpl();
	}

	public RegionDAO getRegionDAO(Connection conn) {
		return new RegionDAOImpl(conn);
	}

	public ReproductionCuttingDAO getReproductionCuttingDAO() {
		return new ReproductionCuttingDAOImpl();
	}

	public ReproductionCuttingDAO getReproductionCuttingDAO(Connection conn) {
		return new ReproductionCuttingDAOImpl(conn);
	}

	public SaleDAO getSaleDAO() {
		return new SaleDAOImpl();
	}

	public SaleDAO getSaleDAO(Connection conn) {
		return new SaleDAOImpl(conn);
	}

	public SaleItemDAO getSaleItemDAO() {
		return new SaleItemDAOImpl();
	}

	public SaleItemDAO getSaleItemDAO(Connection conn) {
		return new SaleItemDAOImpl(conn);
	}

	public TaskDAO getTaskDAO() {
		return new TaskDAOImpl();
	}

	public TaskDAO getTaskDAO(Connection conn) {
		return new TaskDAOImpl(conn);
	}

	public ToolDAO getToolDAO() {
		return new ToolDAOImpl();
	}

	public ToolDAO getToolDAO(Connection conn) {
		return new ToolDAOImpl(conn);
	}

	public ToolItemDAO getToolItemDAO() {
		return new ToolItemDAOImpl();
	}

	public ToolItemDAO getToolItemDAO(Connection conn) {
		return new ToolItemDAOImpl(conn);
	}

	public ToolMaintanceActivityDAO getToolMaintanceActivityDAO() {
		return new ToolMaintanceActivityDAOImpl();
	}

	public ToolMaintanceActivityDAO getToolMaintanceActivityDAO(Connection conn) {
		return new ToolMaintanceActivityDAOImpl(conn);
	}

	public TransactionDAO getTransactionDAO() {
		return new TransactionDAOImpl();
	}

	public TransactionDAO getTransactionDAO(Connection conn) {
		return new TransactionDAOImpl(conn);
	}
}
