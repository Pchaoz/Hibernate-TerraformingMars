package main;

import java.util.Random;

import DAO.DAOMakers;
import DAO.DAOPlayer;
import Models.Corporations;
import Models.Players;

public class MainJose {
	
	private static Random rand = new Random();
	
	public static void main(String[] args) {

		DAOMakers daoMakers = new DAOMakers();
		DAOPlayer daoPlayers = new DAOPlayer();
		daoPlayers.GeneratePlayer("Jose");
		Players player = daoPlayers.Search(1);
		
		daoMakers.generarTablero();
		RollDices(player);
		RollDices(player);
		RollDices(player);

	}
	
	public static void RollDices(Players player) 
	{
		int [] dados = new int [6];
		System.out.print("El jugador " + player.getName() + " ha sacado los resultados: ");
		for (int i : dados) {
			int tirada = rand.nextInt(6)+1;
			dados[i] = tirada;
			System.out.print(tirada +", ");
		}
		
		System.out.println();
	}
	
	public static void ResolveDices(Corporations playerCorporation, int[] dices)
	{
		
	}

}
