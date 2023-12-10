package main;

import DAO.DAOCorporation;
import DAO.DAOGames;
import DAO.DAOPlayer;
import DAO.Utils;
import Models.Corporations;
import Models.Games;
import Models.Players;

public class MainAlex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DAOPlayer daop = new DAOPlayer();
		DAOCorporation daoC = new DAOCorporation();
		DAOGames daoG = new DAOGames();
		daoC.StartCorporations();
		daop.GeneratePlayers();
		SetWinner(daop, daoC, daoG);
	}
	private static void SetWinner(DAOPlayer DAOp, DAOCorporation DAOc, DAOGames DAOg) {
		Corporations Guanyador = new Corporations();
		for (Corporations c : DAOc.Llistar()) {
			if(c.getVictorypoints()>Guanyador.getVictorypoints()) {
				Guanyador=c;
			}
		}
		Players Winner = Guanyador.getPlayer();
		Games g = DAOg.Search(1);
		g.setGuanyador(Winner);
		Winner.setWins(Winner.getWins()+1);
		Winner.getGuanyades().add(g);
		DAOg.update(g);
		DAOp.update(Winner);
	
	}

}
