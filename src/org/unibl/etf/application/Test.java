package org.unibl.etf.application;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Estate;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.Region;

public class Test {
	public static void main(String[] args) throws DAOException {
		List<Plant> plants = DAOFactory.getInstance().getPlantDAO().selectAll();
		List<Basis> bases = DAOFactory.getInstance().getBasisDAO().getByActive(true);
		List<Region> regions = new ArrayList<Region>();
		for(Basis b : bases) {
			Region region = new Region(null, 0, b, b.getBasisId());
			regions.add(region);
		}
		Estate estate = new Estate(regions);
		estate.print();
		System.out.println();
		estate.addPlants(estate.getRegions().get(0), 10);
		estate.print();
	}
}
