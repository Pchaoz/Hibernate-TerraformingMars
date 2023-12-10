package main;

import DAO.DAOCorporation;
import DAO.DAOPlayer;
import DAO.Utils;

public class MainAlex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DAOPlayer daop = new DAOPlayer();
		DAOCorporation daoC = new DAOCorporation();
		daoC.StartCorporations();
		daop.GeneratePlayers();
	}

}
