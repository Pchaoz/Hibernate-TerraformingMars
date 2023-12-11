package DAO;

import Models.Players;

public class DAOPlayer extends DAOGeneric<Players, Integer>{
	
	public DAOPlayer() {
		super(Players.class);
		// TODO Auto-generated constructor stub
	}
	
	public void GeneratePlayer(String name) {
		Players p = new Players(name);
		this.Persistir(p);
	}

}
