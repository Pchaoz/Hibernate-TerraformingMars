package DAO;

import java.util.Collections;
import java.util.List;

import Models.Corporations;
import Models.Players;

public class DAOPlayer extends DAOGeneric<Players, Integer>{
	
	public DAOPlayer() {
		super(Players.class);
		// TODO Auto-generated constructor stub
	}
	
	public void GeneratePlayer(String name) 
	{
		Players p = new Players(name);
		this.Persistir(p);
	}
	
	public void GeneratePlayers() 
	{
		for (int i = 0; i < 4; i++) 
		{
			String name ="Player"+(i+1);
			this.GeneratePlayer(name);
		}
	}
	
	/*Método que asigna una corporación aleatoria a cada jugador.
	Comprueba si la corporación ya está asignada a un jugador antes de hacerlo.*/
	public void AssignCorporationToPlayers(List<Corporations> corporations) 
	{
		List<Players> players = this.Llistar();
		for (Players player : players) {
			while(player.getCor() == null)
			{
				Collections.shuffle(corporations);
				Corporations randomCorp = corporations.get(0);
				if(randomCorp.getPlayer() == null)
				{
					player.setCor(randomCorp);
					randomCorp.setPlayer(player);
					System.out.println("Asignando corporación " + randomCorp.getName() + " al jugador " + player.getName());
				}
			}
			this.update(player);
		}
	}
	
}
