package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Models.Makers;
import Models.TypeMaker;

public class DAOMakers extends DAOGeneric<Makers, Integer>{

	static Random rand;
	
	public DAOMakers() {
		super(Makers.class);
		this.rand = new Random();
	}
	
	//Genera un nuevo Maker y lo guarda en la bbdd
	public void generateMaker(String name, int maxneightbours, TypeMaker typeMaker) {
		Makers newMaker = new Makers(name, maxneightbours, typeMaker);
		this.Persistir(newMaker);
	}
	
	//Devuelve un tipo aleatorio de casilla para definirla en su construcción.
	public TypeMaker SetMakerType()
	{
		TypeMaker type = null;
		int randomNumber = rand.nextInt(3);
		if(randomNumber == 0)
			type = TypeMaker.BOSC;
		if(randomNumber == 1)
			type = TypeMaker.CIUTAT;
		if(randomNumber == 2)
			type = TypeMaker.OCEA;
		return type;
	}
	
	/* Genera un maker y le proporciona el máximo de vecinos que puede tener, determinando así si la casilla es esquina, 
	 * borde o central. Además, le proporciona un tipo aleatorio de casilla con el método SetMakerType() */
	public void generateMaker(String name, int maxneighbours) {
		TypeMaker type = SetMakerType();
		Makers newMaker = new Makers(name, maxneighbours, type); 
		System.out.println("Se genera el maker " + newMaker.toString());
		this.Persistir(newMaker);
	}

	/* Genera el tablero con 61 makers. Comprueba qué tipo de casilla es (esquina, borde o interior) y le asigna 
	 * el valor de las casillas vecinas que tiene. Para ello, usa un constructor donde le pasas el maxneighbours */
	public void regenerarTablero(boolean isFreshLoad) 
	{
		int makersTotales = 61;
		
		if(!isFreshLoad) 
		{
			List<Makers> makers = this.Llistar();
			System.out.println("------------ Eliminando makers anteriores ---------------");
			//Al tener un CascadeType.REMOVE en sus relaciones de vecinos, se borran todos.
			this.DeleteEntity(makers.get(0));
			//Si en su lugar tuviese un CascadeType.DETACH, podriamos utilizar un bucle para eliminarlos.
			/*for (int i = makersTotales-1; i >= 0; i--) {
				Makers maker = makers.get(i);
				System.out.println("Intentando eliminar el maker con ID " + maker.getIdmakers());
				this.DeleteEntity(maker);
			}*/
		}
		System.out.println("Generando piezas del tablero...");
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
		System.out.println("Makers generados. Conectandolos con otros makers...");
		boolean allMakersConnected = false;
		while(!allMakersConnected) {
			List<Makers> llistaMakersNoNeightbours = this.GetAllMakerPossibleNeightbour();
			System.out.println("Cantidad lista:" + llistaMakersNoNeightbours.size());
			Makers maker1 = GetRandomMakerNoNeightbour(llistaMakersNoNeightbours);
			Makers maker2 = GetRandomMakerNoNeightbour(llistaMakersNoNeightbours);
			if(llistaMakersNoNeightbours.size() == 0) 
			{
				allMakersConnected = true;
			} 
			else 
			{
				AddMakersPosibleNeightbour(maker1, maker2);

			}
		}
		System.out.println("Makers conectados!");
	}
	
	
	
	//Método que devuelve una lista con todos los makers de un tipo concreto.
	public ArrayList<Makers> GetMakersByType(TypeMaker tm) 
	{
		ArrayList<Makers> llistaMateixtipus = new ArrayList<Makers>();
		List<Makers> llista =  this.Llistar();
		for (Makers makers : llista) {
			if(makers.getTypeMaker() == tm) {
				llistaMateixtipus.add(makers);
			}
		}
		return llistaMateixtipus;
	}
	
	/*Comprueba si dos makers pueden ser vecinos (o si son vecinos actualmente) y de ser así
	 * los asigna como vecinos respectivamente. Llama también a la función para controlar los casos de uso. */
	public void AddMakersPosibleNeightbour(Makers m1, Makers m2) 
	{
		System.out.println("Se recibe el maker " + m1.getIdmakers() + " y el maker " + m2.getIdmakers());
		if(m1.getNeightbours().contains(m2)) 
		{
			System.out.println("El maker " +m1.getIdmakers() + " contiene al maker " + m2.getIdmakers() + ". El hashset ignora esta relación.");
			if(GetAllMakerPossibleNeightbour().size() == 2)
			{
				System.out.println("Se ha dado el caso de que los dos últimos maker se intentaban asignar entre sí teniendo una relación. "
						+ "Como no he sabido resolver esto, regenero el tablero y a prendre pel cul.");
				regenerarTablero(false);
			}
			return;
		}
		if(m1.getIdmakers() == m2.getIdmakers())
		{
			System.out.println("El maker es el mismo");
			if(GetAllMakerPossibleNeightbour().size() == 1)
			{
				System.out.println("Se ha dado el caso de que el último maker se intentaba asignar a sí mismo. "
						+ "Se vincula con otros dos al azar y esos dos a este.");
				FixLastNeightbour(m1);
			}
			return;
		}
		m1.AddNeightbour(m2);
		m2.AddNeightbour(m1);
		this.update(m1);
		this.update(m2);
		System.out.println("Intentando vincular el maker " + m1.getIdmakers() + " con el maker " + m2.getIdmakers());
	}
	
	/* Función para controlar el caso donde el último maker se intente asignar a sí mismo. Obtiene otros dos makers relacionados
	 * entre sí y deshace esa relación para integrar el maker que se ha quedado solito. uwu */
	public void FixLastNeightbour(Makers maker1) 
	{
		List<Makers> makers = this.Llistar();
		Makers maker2 = makers.get(0);
		Makers maker3 = null;
		for (Makers makerActual : makers) {
			if(makerActual.getNeightbours().contains(maker2))
			{
				maker3 = makerActual;
			}
		}
		maker2.getNeightbours().remove(maker3);
		maker3.getNeightbours().remove(maker2);
		this.update(maker2);
		this.update(maker3);
		maker2.AddNeightbour(maker1);
		maker3.AddNeightbour(maker1);
		maker1.AddNeightbour(maker2);
		maker1.AddNeightbour(maker3);
		this.update(maker2);
		this.update(maker3);
		this.update(maker1);
	}
	
	/* Función para controlar el caso donde los dos últimos makers ya tienen una relación. Básicamente deja a uno huérfano. */
	/*public void FixLastCoupleNeightboursWhenRelationExists(Makers maker1, Makers maker2)
	{
		List<Makers> makers = this.Llistar();
		//Tengo que hacer esta basura por que cuando cargas con Llistar() ya no son los mismos objetos según java.
		Makers maker1Alias = null;
		Makers maker2Alias = null;
		Makers maker3Alias = null;
		for (Makers makerActual : makers) {
			if(makerActual.getIdmakers() == maker1.getIdmakers())
				maker1Alias = makerActual;
			if(makerActual.getIdmakers() == maker2.getIdmakers())
				maker2Alias = makerActual;
		}
		System.out.println("A partir de aquí necesito un cerebro nuevo.");
		for (Makers makerActual : makers) {
			if(makerActual.getNeightbours().contains(maker1Alias))
				maker3Alias = makerActual;
		}
		maker1Alias.getNeightbours().remove(maker3Alias);
		maker3Alias.getNeightbours().remove(maker1Alias);
		this.update(maker1Alias);
		this.update(maker3Alias);
		maker2Alias.AddNeightbour(maker3Alias);
		maker3Alias.AddNeightbour(maker2Alias);
		this.update(maker2Alias);
		this.update(maker3Alias);
	}*/
	
	/* El método devuelve un Maker aleatorio que todavía puede tener vecinos. Para hacerlo, utiliza una
	 * lista que contiene todos los Makers que pueden tener casillas adjuntas, y 
	 */
	public Makers GetRandomMakerNoNeightbour(List<Makers> llistaMakersNoNeightbours) 
	{
		Random r = new Random();
		Makers MakerNoNeightbour = null;
		//List<Makers> llistaMakersNoNeightbours = this.GetAllMakerPossibleNeightbour();
		if(AllMakersNeightbours(llistaMakersNoNeightbours)) {
			return null;
		}
		while(MakerNoNeightbour==null) {
			int random = r.nextInt(llistaMakersNoNeightbours.size());
			 MakerNoNeightbour = llistaMakersNoNeightbours.get(random);
			 System.out.println("El maker tiene " + MakerNoNeightbour.getNeightbours().size() + " y el maker puede tener "+ MakerNoNeightbour.getMaxneightbours());
			 if(MakerNoNeightbour.getNeightbours().size() >= MakerNoNeightbour.getMaxneightbours()) {			
				MakerNoNeightbour = null;
			}
		}
		return MakerNoNeightbour;
	}
	
	//La función devuelve una lista con todos los makers que todavía pueden tener vecinos
	public List<Makers> GetAllMakerPossibleNeightbour(){
		List<Makers> LlistaTotsMakers = this.Llistar();
		List<Makers> LlistaPossibleNeightbour = new ArrayList<Makers>();
		for (Makers maker : LlistaTotsMakers) {
			if(maker.getNeightbours().size() < maker.getMaxneightbours()) {
				LlistaPossibleNeightbour.add(maker);
			}
		}
		return LlistaPossibleNeightbour;	
	}
	
	public boolean AllMakersNeightbours(List<Makers> makers) {
		if(makers.size() == 0) {
			return true;
		}
		return false;
	}
}
