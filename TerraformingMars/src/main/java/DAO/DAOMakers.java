package DAO;

import Models.Makers;
import Models.TypeMaker;

public class DAOMakers extends DAOGeneric<Makers, Integer>{

	public DAOMakers(Class<Makers> entityClass) {
		super(Makers.class);
	}
	
	//Genera un nuevo Maker y lo guarda en la bbdd
	public void generateMaker(String name, String description, int maxneightbours, TypeMaker typeMaker) {
		Makers newMaker = new Makers(name, description, maxneightbours, typeMaker);
		this.Persistir(newMaker);
	}

	
	
}
