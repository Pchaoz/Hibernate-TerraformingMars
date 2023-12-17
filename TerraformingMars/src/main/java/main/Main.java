package main;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import DAO.DAOCorporation;
import DAO.DAOGames;
import DAO.DAOMakers;
import DAO.DAOPlayer;
import DAO.Utils;
import Models.Corporations;
import Models.Games;
import Models.Makers;
import Models.Players;
import Models.TypeMaker;



public class Main {
	
	private static Random rand = new Random();
	private static DAOMakers daoMakers = new DAOMakers();
	private static DAOPlayer daoPlayers = new DAOPlayer();
	private static DAOCorporation daoCorporation = new DAOCorporation();
	private static DAOGames daoGames = new DAOGames();
	private static boolean isGameover = false;
	private static boolean quitGame = false;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
	
		int numPartida = 1;
		boolean isFreshGame = true;
		daoCorporation.StartCorporations();
		daoPlayers.GeneratePlayers();
		
		while(!quitGame) 
		{
			isGameover = false;

			daoCorporation.ResetCorporation();
			daoMakers.regenerarTablero(isFreshGame);
			daoGames.GenerateGames();
			Games partida = daoGames.Search(numPartida);
			List<Corporations> corporations = daoCorporation.Llistar();
			daoPlayers.AssignCorporationToPlayers(corporations);
			List<Players> players = daoPlayers.Llistar();
			daoGames.AssignPlayersToCurrentGame(players, partida);
			System.out.println("Comienza la partida!");
			while(!isGameover)
			{
				for (Players player : players) {
					System.out.println("Turno del jugador: " + player.getName());
					RollDices(player, partida);
				}
			}
			System.out.println("La partida ha terminado!");
			SetVictoryPoints();
			SetWinner(partida);
			quitGame = Menu();
			numPartida++;
			isFreshGame = false;
		}
		System.out.println("Saliendo del juego. Hasta pronto!");
		Utils.close();

	}
	
	public static boolean Menu() {	
		boolean exitConsola = false;
		boolean terminar = false;
		while(!exitConsola)
		{
			System.out.println("La partida ha terminado. Quieres empezar otra partida? 1- Sí | 2- No \n");
			String entrada = scan.nextLine();
			switch(entrada)
			{
				case "1":
					terminar = false;
					exitConsola = true;
					break;
				case "2":
					terminar = true;
					exitConsola = true;
					break;
				default:
					System.out.println("Opción incorrecta. Selecciona entre las opciones 1 y 2.");
					break;
			}
		}
		return terminar;
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
		CheckWinConditions(partida.getIdGame(), player.getCor());
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
			Games partidaActual = daoGames.Search(partida.getIdGame());
			daoGames.AddTemperature(partidaActual);
			System.out.println("Se incrementa la temperatura en 2.");
			if(resultados[0] == 6) {
				//Incrementar Temperatura
				daoGames.AddTemperature(partidaActual);
				System.out.println("Se incrementa la temperatura en 2 nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[1] >= 3) {
			//Incrementar oxigeno
			Games partidaActual = daoGames.Search(partida.getIdGame());
			daoGames.AddOxygen(partidaActual);
			System.out.println("Se incrementa el oxigeno en 1.");
			if(resultados[1] == 6) {
				//Incrementar oxigeno
				daoGames.AddOxygen(partidaActual);
				System.out.println("Se incrementa el oxigeno en 1 nuevamente por obtener seis dados iguales!");
				return;
			}
		}
		if(resultados[2] >= 3) {
			//Asociar casilla oceano
			List<Makers> oceans = daoMakers.GetMakersByType(TypeMaker.OCEA);
			Makers maker = AssignMakerToCorporation(oceans, playerCorporation);
			if(maker != null)
			{
				System.out.println("Se asocia una casilla oceano a la casilla " + maker.getIdmakers() + " a la corporación "+ playerCorporation.getName() +".");
				daoMakers.setMakerToCorporation(maker, playerCorporation);
			} else 
			{
				System.out.println("No quedan casillas de oceano disponibles.");
			}
					
			if(resultados[2] == 6) 
			{
				//Asociar casilla oceano			
				Makers maker2 = AssignMakerToCorporation(oceans, playerCorporation);
				if(maker != null)
				{
					System.out.println("Se asocia otra casilla oceano a la casilla " + maker.getIdmakers() + " a la corporación "+ playerCorporation.getName() 
					+ " nuevamente por obtener seis dados iguales!");
					daoMakers.setMakerToCorporation(maker2, playerCorporation);		
				} 		
				return;
			}
		}
		if(resultados[3] >= 4) {
			//Asociar casilla Jungla
			List<Makers> junglas = daoMakers.GetMakersByType(TypeMaker.BOSC);
			Makers maker = AssignMakerToCorporation(junglas, playerCorporation);
			if(maker != null)
			{
				System.out.println("Se asocia una casilla bosque a la casilla " + maker.getIdmakers() + " a la corporación "+ playerCorporation.getName() +".");
				daoMakers.setMakerToCorporation(maker, playerCorporation);
			} else 
			{
				System.out.println("No quedan casillas de bosque disponibles.");
			}		
			return;
		}
		if(resultados[4] >= 3) {
			//Asociar ciudad
			List<Makers> ciutats = daoMakers.GetMakersByType(TypeMaker.CIUTAT);
			Makers maker = AssignMakerToCorporation(ciutats, playerCorporation);
			if(maker != null)
			{
				System.out.println("Se asocia una casilla ciudad a la casilla " + maker.getIdmakers() + " a la corporación "+ playerCorporation.getName() +".");
				daoMakers.setMakerToCorporation(maker, playerCorporation);
			} else 
			{
				System.out.println("No quedan casillas de ciudad disponibles.");
			}		
			if(resultados[4] == 6) {
				//Asociar ciudad
				Makers maker2 = AssignMakerToCorporation(ciutats, playerCorporation);
				if(maker != null)
				{
					System.out.println("Se asocia otra casilla ciudad a la casilla " + maker.getIdmakers() + " a la corporación "+ playerCorporation.getName() 
					+ " nuevamente por obtener seis dados iguales!");
					daoMakers.setMakerToCorporation(maker2, playerCorporation);		
				} 		
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
	
	public static Makers AssignMakerToCorporation(List<Makers> makers, Corporations playerCorporation)
	{
		Makers maker = null;
		for (Makers tipo : makers) 
		{
			if(tipo.getMakerOwner() == null)
				maker = tipo;
		}
		return maker;
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
		int gameConditionRecount = 0;
		Games partida = daoGames.Search(numPartida);
		System.out.println("Comprobando si se cumplen condiciones de victoria...");
		if(partida.getTemperature() >= 0) 
		{
			gameConditionRecount++;
			System.out.println("La temperatura ha llegado a 0. Se cumple una condicion de partida terminada.");
		}
		if(partida.getOxygen() >= 14)
		{
			gameConditionRecount++;
			System.out.println("El oxigeno ha llegado a 14. Se cumple una condicion de partida terminada.");
		}
		List<Makers> makers = daoMakers.GetMakersByType(TypeMaker.OCEA);
		boolean oceansBelongToCorporation = CheckAllOceanMakers(makers, playerCorporation);
		if(oceansBelongToCorporation)
			gameConditionRecount++;
		if(gameConditionRecount >= 2)
			isGameover = true;
	}
	
	public static boolean CheckAllOceanMakers(List<Makers> oceanMakers, Corporations playerCorporation)
	{
		for (Makers maker : oceanMakers) {
			if(maker.getMakerOwner() == null)
			{
				return false;
			} else
			{
				if(!maker.getMakerOwner().equals(playerCorporation))
				{
					return false;
				}
			}
		}
		System.out.println("Todas las casillas de oceano pertenecen a un jugador. Se cumple una condicion de partida terminada.");
		return true;
	}
	
	//Método que setea los victory points a las corporaciones
	private static void SetVictoryPoints() 
	{
		
		System.out.println("Haciendo recuento de puntos de victoria...");
		List<Corporations> lc = daoCorporation.Llistar();		
		
		int maxCitites = 0;
		int maxJungle = 0;
		int coorC = 0;
		int coorJ = 0;
		
		for (int i = 0; i < lc.size(); i++) 
		{
						
			Corporations coor =  lc.get(i);
			List<Makers> coorMake = daoMakers.getMakersByCoorp(coor);
			
			int jungle = 0;
			int city = 0;
			int sea = 0;
			
			int points = 0;
			
			for (int x = 0; x < coorMake.size(); x++) 
			{
				points++;
				System.out.println("Añadiendo un punto de victoria a la corporacion " + coor.getName() + " por casilla en posesión.");
				if (coorMake.get(x).getTypeMaker() == TypeMaker.BOSC) 
				{
					jungle++;
				}else if (coorMake.get(x).getTypeMaker() == TypeMaker.CIUTAT) 
				{
					city++;
				}else if  (coorMake.get(x).getTypeMaker() == TypeMaker.OCEA && sea < 3) 
				{
					sea++;
					if (sea == 3) {
						System.out.println("Añadiendo tres puntos de victoria a la corporación " + coor.getName() + " por tener tres casillas de oceano en posesión.");
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
			daoCorporation.update(coor);
		}
		
		Corporations corporationMaxJungles = lc.get(coorJ);
		corporationMaxJungles.setVictorypoints(lc.get(coorJ).getVictorypoints() + 3);
		System.out.println("Añadiendo tres puntos de victoria a la corporación " + corporationMaxJungles.getName() + " por tener el mayor de casillas de jungla en posesión.");
		daoCorporation.update(corporationMaxJungles);
		
		Corporations corporationMaxCities = lc.get(coorC);
		corporationMaxCities.setVictorypoints(lc.get(coorC).getVictorypoints() + 3);
		System.out.println("Añadiendo tres puntos de victoria a la corporación " + corporationMaxCities.getName() + " por tener el mayor de casillas de ciudad en posesión.");
		daoCorporation.update(corporationMaxCities);
		
		for (Corporations corporation : lc) {
			if(corporation.getPlayer() != null)
				System.out.println("La corporación " + corporation.getName() + " suma un total de " + corporation.getVictorypoints() + " puntos de victoria.");
		}
	}
	
	//Función para determinar el ganador
	private static void SetWinner(Games partida)
	{
		List<Corporations> corporations = daoCorporation.Llistar();
		Corporations winner = null;
		for (Corporations corporation: corporations) 
		{
			if(winner == null || winner.getVictorypoints() < corporation.getVictorypoints())
				winner = corporation;
		}
		Players player = winner.getPlayer();
		System.out.println("El ganador de la partida es " + player.getName() + " con la corporación " + winner.getName() + " sumando un total de " + winner.getVictorypoints() + " puntos de victoria!!!");
		
		Games partidaActual = daoGames.Search(partida.getIdGame());
		daoGames.SetWinner(partidaActual, player);
	}
	
}
