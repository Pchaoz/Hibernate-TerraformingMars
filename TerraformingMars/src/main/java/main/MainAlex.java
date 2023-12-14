package main;

import DAO.DAOCorporation;
import DAO.DAOGames;
import DAO.DAOPlayer;
import DAO.Utils;
import Models.Corporations;
import Models.Games;
import Models.Players;

public class MainAlex {
	static DAOPlayer daop = new DAOPlayer();
	static DAOCorporation daoC = new DAOCorporation();
	static DAOGames daoG = new DAOGames();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int numpartida = 1;
		daoG.GenerateGames();
		daoC.StartCorporations();
		daop.GeneratePlayers();
		SetWinner(numpartida);
	}
	private static void SetWinner(int NumPartida) {
		Corporations Guanyador = new Corporations();
		for (Corporations c : daoC.Llistar()) {
			if(c.getVictorypoints()>Guanyador.getVictorypoints()) {
				Guanyador=c;
			}
		}
		//Conseguimos el winner mediante la corporation
		Players Winner = Guanyador.getPlayer();
		
		Games g = daoG.Search(NumPartida);
		g.setGuanyador(Winner);
		Winner.setWins(Winner.getWins()+1);
		Winner.getGuanyades().add(g);
		daoG.update(g);
		daop.update(Winner);
	
	}

}
