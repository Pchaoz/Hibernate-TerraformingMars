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
		markersDAO.generarTablero();
		

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
		
		SetVictoryPoints(p1, p2, p3, p4);		
		
	}
	
	private static void SetVictoryPoints(Players p1, Players p2, Players p3, Players p4) {
		
		CheckPointsCoorp(p1.getCor());
	}

	private static void CheckPointsCoorp(Corporations c) {
		DAOMakers dmakers = new DAOMakers();
		
		List<Makers> cmk = dmakers.getMakersByCoorp(c);
		
	}
}
