package main;
import Models.*;

import java.util.List;

import DAO.*;

public class MainPol {

	public static void main(String[] args) {
		
		DAOCorporation coorDAO = new DAOCorporation();
		DAOPlayer playerDAO = new DAOPlayer();
		DAOMakers markersDAO = new DAOMakers();
		
		coorDAO.StartCorporations();
		playerDAO.GeneratePlayers();
		markersDAO.regenerarTablero(true);
		

		Players p1 = playerDAO.Search(1);
		Players p2 = playerDAO.Search(2);
		Players p3 = playerDAO.Search(3);
		Players p4 = playerDAO.Search(4);
		Corporations c1 = coorDAO.Search(1);
		
		p1.setCor(c1);
		
		Makers mk1 = markersDAO.Search(1);
		Makers mk2 = markersDAO.Search(21);
		Makers mk3 = markersDAO.Search(10);
		 
		mk1.setMakerOwner(c1);
		mk2.setMakerOwner(c1);
		mk2.setMakerOwner(c1);
		
		markersDAO.update(mk1);
		markersDAO.update(mk2);
		markersDAO.update(mk3);
		
		List<Makers> withoutCoorp = markersDAO.getMarkersNoCoorp();
		System.out.println("CASILLAS SIN COORPORACION: ");
		System.out.println(withoutCoorp.toString());
		
		List<Makers> withC1 = markersDAO.getMakersByCoorp(c1);
		System.out.println("CASILLAS CON COORPORACION 1: ");
		System.out.println(withC1.toString());
		
		SetVictoryPoints();		
		
	}
	
	private static void SetVictoryPoints() {
		DAOCorporation coorDAO = new DAOCorporation();
		DAOMakers makersDAO = new DAOMakers();
		List<Corporations> lc = coorDAO.Llistar();
		
		int maxCitites = -1;
		int maxJungle = -1;
		int coorC = -1;
		int coorJ = -1;
		
		for (int i = 0; i > lc.size(); i++) {
						
			Corporations coor =  lc.get(i);
			List<Makers> coorMake = makersDAO.getMakersByCoorp(coor);
			
			int jungle = 0;
			int city = 0;
			int sea = 0;
			
			int points = 0;
			
			for (int x = 0; x < coorMake.size(); x++) {
				points++;
				
				if (coorMake.get(x).getTypeMaker() == TypeMaker.Bosc) {
					jungle++;
				}else if (coorMake.get(x).getTypeMaker() == TypeMaker.Ciutat) {
					city++;
				}else if  (coorMake.get(x).getTypeMaker() == TypeMaker.Oceà && sea < 3) {
					sea++;
					if (sea == 3) {
						points+= 3;
					}
				}
			}
			if (jungle > maxJungle) {
				maxJungle = jungle;
				coorJ = i;
			}
			if (city > maxCitites) {
				maxCitites = city;
				coorC = i;
			}
			
			coor.setVictorypoints(points);
		}
		
		lc.get(coorJ).setVictorypoints(lc.get(coorJ).getVictorypoints() + 3);
		lc.get(coorC).setVictorypoints(lc.get(coorC).getVictorypoints() + 3);
	}
}
