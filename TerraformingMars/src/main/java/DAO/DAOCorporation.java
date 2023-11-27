package DAO;

import Models.Corporations;

public class DAOCorporation extends DAOGeneric<Corporations, Integer>{

	public DAOCorporation() {
		super(Corporations.class);
		// TODO Auto-generated constructor stub
	}
	public void GenerateCorp(String Name, String Desc) {
		Corporations c = new Corporations(Name, Desc);
		this.Persistir(c);
	}

}
