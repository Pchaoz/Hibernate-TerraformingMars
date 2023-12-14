package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import DAO.DAOCorporation;
import DAO.DAOGames;
import DAO.DAOMakers;
import DAO.DAOPlayer;
import Models.Corporations;
import Models.Games;
import Models.Makers;
import Models.Players;
import Models.TypeMaker;



public class MainJose {
	
	private static Random rand = new Random();
	private static DAOMakers daoMakers = new DAOMakers();
	private static DAOPlayer daoPlayers = new DAOPlayer();
	private static DAOCorporation daoCorporation = new DAOCorporation();
	private static DAOGames daoGames = new DAOGames();
	private static boolean isGameover = false;
	
	public static void main(String[] args) {


		
		int numPartida = 1;
		isGameover = false;

		daoMakers.generarTablero();
		daoGames.GenerateGames();
		Games partida = daoGames.Search(numPartida);
		daoCorporation.StartCorporations();
		List<Corporations> corporations = daoCorporation.Llistar();
		daoPlayers.GeneratePlayers();
		daoPlayers.AssignCorporationToPlayers(corporations);
		List<Players> players = daoPlayers.Llistar();
		daoGames.AssignPlayersToCurrentGame(players, partida);
		
		Players playerActual = players.get(0);
		
		RollDices(playerActual, partida);
		RollDices(playerActual, partida);
		RollDices(playerActual, partida);

	}
	
	
	/*Función que se encarga de resolver el turno del jugador. Lanza los dados y resuelve la tirada*/
	public static void RollDices(Players player, Games partida) 
	{
		int [] dados = new int [6];
		for (int i = 0; i < dados.length; i++) {
			int tirada = rand.nextInt(6)+1;
			dados[i] = tirada;
		}
		System.out.print("El jugador " + player.getName() + " ha sacado los resultados: ");
		PrintDices(dados);
		ResolveDices(player.getCor(), dados, partida);
		Corporations playerCorporation = player.getCor();
		CheckWinConditions(partida.getIdGame(), playerCorporation);
	}
	
	/* Resolución de dados:
	 * 1- Temperatura - Por cada 3 dados, incrementa 2 grados la temperatura
	 * 2- Oxigeno - Por cada 3 dados, incrementa 1 el oxigeno
	 * 3- Oceano - Por cada 3 dados, se asocia una casilla de tipo oceano a la corporacion
	 * 4- Jungla o Bosque - Por cada 4 dados, se asocia una casilla de tipo bosque a la corporacion
	 * 5- Ciudad - Por cada 3 dados, se asocia una casilla de tipo ciudad a la corporacion
	 * 6- Puntos de Victoria - Por cada 3 dados, se suman 2 puntos de victoria a la corporacion
	 */
	
	public static void ResolveDices(Corporations playerCorporation, int[] dices, Games partida)
	{
		int [] resultados = RecountDices(dices);
		if(resultados[0] >= 3) {
			//Incrementar Temperatura
			daoGames.AddTemperature(partida);
			System.out.println("Se incrementa la temperatura en 2.");
			if(resultados[0] == 6) {
				//Incrementar Temperatura
				daoGames.AddTemperature(partida);
				System.out.println("Se incrementa la temperatura en 2 nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[1] >= 3) {
			//Incrementar oxigeno
			daoGames.AddOxygen(partida);
			System.out.println("Se incrementa el oxigeno en 1.");
			if(resultados[1] == 6) {
				//Incrementar oxigeno
				daoGames.AddOxygen(partida);
				System.out.println("Se incrementa el oxigeno en 1 nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[2] >= 3) {
			//Asociar casilla oceano
			System.out.println("Se asocia una casilla oceano.");
			if(resultados[2] == 6) {
				//Asociar casilla oceano
				System.out.println("Se asocia otra casilla oceano nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[3] >= 4) {
			//Asociar casilla Jungla
			System.out.println("Se asocia una casilla bosque.");
			return;
		}
		if(resultados[4] >= 3) {
			//Asociar ciudad
			System.out.println("Se asocia una casilla ciudad.");
			if(resultados[4] == 6) {
				//Asociar ciudad
				System.out.println("Se asocia otra casilla ciudad nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[5] >= 3) {
			//Incrementar puntos victoria
			System.out.println("Se incrementa puntos de victoria en 2.");
			if(resultados[5] == 6) {
				//Incrementar puntos victoria
				System.out.println("Se incrementa puntos de victoria en 2 nuevamente por obtener seis dados iguales!");
				return;
			}
		} else {
			System.out.println("No te ha tocado nada en los dados pringao.");
		}
	}
	
	public static int[] RecountDices(int [] dices) {
		int[] resultados = {0,0,0,0,0,0};
		System.out.println("Comprobando dados...");
		for(int i = 0; i < dices.length; i++) {
			switch(dices[i]) {
				case 1:
					resultados[0] += 1;
					break;
				case 2:
					resultados[1] += 1;
					break;
				case 3:
					resultados[2] += 1;
					break;
				case 4:
					resultados[3] += 1;
					break;
				case 5:
					resultados[4] += 1;
					break;
				default:
					resultados[5] += 1;
					break;
			}
		}
		System.out.print("El recuento de los dados es: ");
		PrintDices(resultados);
		return resultados;
	}
	
	public static void PrintDices(int [] dados) {
		for (int i : dados) {
			System.out.print(i + ", ");
		}
		System.out.println();
	}
	
	public static void CheckWinConditions(int numPartida, Corporations playerCorporation) 
	{
		Games partida = daoGames.Search(numPartida);
		System.out.println("Comprobando si se cumplen condiciones de victoria...");
		if(partida.getTemperature() >= 0) 
		{
			isGameover = true;
			System.out.println("La temperatura ha llegado a 0. Se acaba la partida.");
		}
		if(partida.getOxygen() >= 14)
		{
			isGameover = true;
			System.out.println("El oxigeno ha llegado a 14. Se acaba la partida.");
		}
		List<Makers> makers = daoMakers.GetMakersByType(TypeMaker.OCEA);
		boolean oceansBelongToCorporation = CheckAllOceanMakers(makers, playerCorporation);
		if(oceansBelongToCorporation)
			isGameover = true;
		
	}
	
	public static boolean CheckAllOceanMakers(List<Makers> oceanMakers, Corporations playerCorporation)
	{
		boolean winCondition = true;
		for (Makers maker : oceanMakers) {
			if(!maker.getMakerOwner().equals(playerCorporation) || maker.getMakerOwner() == null)
			{
				winCondition = false;
				return winCondition;
			}		
		}
		System.out.println("Todas las casillas de oceano pertenecen a un jugador. Se acaba la partida.");
		return winCondition;
	}
}
