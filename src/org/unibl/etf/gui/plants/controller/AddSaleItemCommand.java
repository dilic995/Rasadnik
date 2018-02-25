package org.unibl.etf.gui.plants.controller;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.SaleItem;

public class AddSaleItemCommand implements Command {

	public AddSaleItemCommand() {

	}

	public AddSaleItemCommand(Region region, SaleItem item, List<SaleItem> items, DrawRegionsController controller) {
		super();
		this.region = region;
		this.item = new SaleItem(item.getCount(), item.getPlant(), item.getSale(), item.getPlantId(), item.getSaleId(),
				item.getPrice(), item.getHeightMin());
		this.items = items;
		this.count = item.getCount();
		this.price = (item.getPrice());
		this.controller = controller;
	}

	@Override
	public void execute() {
		System.out.println(this.item);
		region.setNumberOfPlants(region.getNumberOfPlants() - this.count);
		boolean contains = false;
		for (SaleItem itemInList : items) {
			if (itemInList.getPlantId().equals(item.getPlantId())
					&& itemInList.getHeightMin().equals(item.getHeightMin())) {
				itemInList.setCount(itemInList.getCount() + this.count);
				itemInList.setPrice(itemInList.getPrice().add(this.price));
				contains = true;
				break;
			}
		}
		if (!contains) {
			items.add(new SaleItem(item.getCount(), item.getPlant(), item.getSale(), item.getPlantId(), item.getSaleId(),
					item.getPrice(), item.getHeightMin()));
		}
		controller.displayInfo(region);
		controller.refreshList();
	}

	@Override
	public void unexecute() {
		region.setNumberOfPlants(region.getNumberOfPlants() + count);
		for (Iterator<SaleItem> it = items.iterator(); it.hasNext();) {
			SaleItem itemInList = it.next();
			if (itemInList.getPlantId().equals(item.getPlantId())
					&& itemInList.getHeightMin().equals(item.getHeightMin())) {
				itemInList.setCount(itemInList.getCount() - count);
				itemInList.setPrice(itemInList.getPrice().subtract(price));
				if (itemInList.getCount().equals(Integer.valueOf(0))) {
					it.remove();
				}
				break;
			}
		}
		System.out.println(this.item);
		controller.displayInfo(region);
		controller.refreshList();
	}

	private Region region;
	private SaleItem item;
	private List<SaleItem> items;
	private Integer count;
	private BigDecimal price;
	private DrawRegionsController controller;
}
