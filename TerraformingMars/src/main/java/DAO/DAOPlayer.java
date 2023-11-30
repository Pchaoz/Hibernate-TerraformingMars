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
	public void GeneratePlayers() {
		for (int i = 0; i < 4; i++) {
			String name ="Player"+(i+1);
			GeneratePlayer(name);
		}
	}
}
