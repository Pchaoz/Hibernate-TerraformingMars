package DAO;

import java.util.List;
import java.util.Set;

import Models.Games;
import Models.Players;

public class DAOGames extends DAOGeneric<Games, Integer>{

	public DAOGames() {
		super(Games.class);
		// TODO Auto-generated constructor stub
	}
	public void GenerateGames() {
		Games g = new Games();
		this.Persistir(g);
	}
	public void GenerateGames(int oxygen, int temperatura, int oceans) {
		Games g = new Games(oxygen, temperatura, oceans);
		this.Persistir(g);
	}
	
	public void AssignPlayersToCurrentGame(List<Players> players, Games game)
	{
		for (Players player : players) {
			game.addJugadors(player);
			//player.
		}
		this.update(game);
	}
	
	public void AddTemperature(Games game)
	{
		game.addTemperature(2);
		this.update(game);
	}
	
	public void AddOxygen(Games game)
	{
		game.addOxygen(1);
		this.update(game);
	}
	
}
