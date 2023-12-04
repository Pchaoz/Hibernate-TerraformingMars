package DAO;

import Models.Makers;
import Models.TypeMaker;

public class DAOMakers extends DAOGeneric<Makers, Integer>{

	public DAOMakers() {
		super(Makers.class);
	}
	
	//Genera un nuevo Maker y lo guarda en la bbdd
	public void generateMaker(String name, int maxneightbours, TypeMaker typeMaker) {
		Makers newMaker = new Makers(name, maxneightbours, typeMaker);
		this.Persistir(newMaker);
	}
	
	
	public void generateMaker(String name, int maxneighbours) {
		Makers newMaker = new Makers(name, maxneighbours); 
		System.out.println("Se genera el maker " + newMaker.toString());
		this.Persistir(newMaker);
	}

	/* Genera el tablero con 61 makers. Comprueba qué tipo de casilla es (esquina, borde o interior) y le asigna 
	 * el valor de las casillas vecinas que tiene. Para ello, usa un constructor donde le pasas el maxneighbours */
	public void generarTablero() {
		int makersTotales = 61;
		for(int i = 0; i < makersTotales; i++) {
			if(i < 6)
			{
				this.generateMaker("Maker "+(i+1),3);
			}
			else if(i >= 6 && i < 24)
			{
				this.generateMaker("Maker "+(i+1),4);
			}
			else 
			{
				this.generateMaker("Maker "+(i+1),6);
			}
		}
	}
}
