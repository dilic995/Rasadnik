package org.unibl.etf.gui.plants.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.unibl.etf.dto.HeightPriceRatio;
import org.unibl.etf.dto.MyPlant;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.scene.image.Image;

public abstract class PlantBrowserController extends BaseController {

	public void loadPlants() {
		HeightPriceRatio s = new HeightPriceRatio(0.0, 10.0, 20.0);
		HeightPriceRatio m = new HeightPriceRatio(10.0, 50.0, 50.0);
		HeightPriceRatio l = new HeightPriceRatio(50.0, null, 100.0);
		List<HeightPriceRatio> ratios = new ArrayList<HeightPriceRatio>();
		ratios.add(s);
		ratios.add(m);
		ratios.add(l);
		try {
			plants.add(new MyPlant(1, "Cedrus atlantica", "Atlaski kedar",
					"Atlaski kedar (Cedrus atlantica (Endl.) Manetti ex Carr) pripada rodu Cedrus, koji se u sastavu familije Pinaceae. Naziv Cedrus potiče od grčke reči κεο = goreti, jer se drvo upotrebljava za kađenje. Kod Plinija starijeg naziv označava kedrovo drvo i kedrovo ulje, ali i crvenu kleku (Juniperus oxycedrus) [1]. Većina savremenih izvora tretira atlaski kedar kao posebnu vrstu Cedrus atlantica, ali neki izvori smatraju da je podvrsta libanskog kedra (Cedrus libani A. Rich. subsp. atlantica (Endl.) Batt. & Trab.). U okviru roda nalaze se još dve do tri vrste zimzelenog drveća, čiji areal je ograničen na područje južnog i istočnog Mediterana i na zapadne Himalaje.",
					true, new Image(new FileInputStream("resources/images/cedrus.jpg")), ratios));
			plants.add(new MyPlant(2, "Mimosa pudica", "Sramezljiva mimoza",
					"Mimoza, drvo iz porodice akacija, u odnosu na neke druge vrste, nema dug vek. Ima kratak životni vek, najveći broj živi 15—30 gidina, domaća mimoza uspeva oko 30 godina, a italijanka do petnaest godina. Akacije su uglavnom žbunovi ili manja stabla koja rastu na sušnim i sunčanim predelima. Obično naraste do 12 metara visine, premda neke mogu dostići i veće visine. Mnoge vrste imaju zelene peraste listove koji su gusto zbijeni na granama i stvaraju utisak paprati.",
					true, new Image(new FileInputStream("resources/images/mimosa.jpg")), ratios));
			plants.add(new MyPlant(3, "Pinus parviflora", "Japanski bijeli bor",
					"Bonsai, bolje rečeno bonsaj, u doslovnom prevodu znači stabaoce u posudi. Poreklom je iz Kine odakle je prenesen u Japan gde je dalje razvijen i gde je dobio današnju formu. Za njegovo oblikovanje i uzgoj je potrebno mnogo više od same hortikulturne veštine. Veći je naglasak na umetničkim sklonostima samog uzgajivača. Osnovni princip je uzgojiti minijaturno stabaoce koje ima sve karakteristike te biljne vrste u „normalnoj“ veličini u prirodi.",
					false, new Image(new FileInputStream("resources/images/bonsai.jpg")), ratios));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Collections.sort(plants, new Comparator<MyPlant>() {

			@Override
			public int compare(MyPlant o1, MyPlant o2) {
				return o1.getLatinName().compareTo(o2.getLatinName());
			}
		});
	}
	public Boolean addPlant(MyPlant plant) {
		if(plants.add(plant)) {
			return true;
		}
		return false;
	}
	public Boolean removePlant(MyPlant plant) {
		if(plants.remove(plant)) {
			return true;
		} else {
			return false;
		}
	}
	public abstract void displayPlantInfo(MyPlant plant);
	public abstract void displayAllPlants();
	public void buildInitialWindow() {
		loadPlants();
		displayAllPlants();
		displayPlantInfo(plants.get(0));
	}
	public void setPlants(List<MyPlant> plants) {
		this.plants = plants;
	}
	protected List<MyPlant> plants = new ArrayList<MyPlant>();
	protected MyPlant selectedPlant = null;
}
