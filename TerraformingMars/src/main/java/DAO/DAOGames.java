package DAO;

import Models.Games;

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
	
	
}
