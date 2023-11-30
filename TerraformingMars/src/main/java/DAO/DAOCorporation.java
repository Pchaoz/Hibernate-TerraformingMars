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
	public void StartCorporations() {
		GenerateCorp("Credicor","1");
		GenerateCorp("Ecoline","2");
		GenerateCorp("Helion","3");
		GenerateCorp("Mining Guild","4");
		GenerateCorp("Phoblog","5");
		GenerateCorp("Tharsis Republic","1");
		GenerateCorp("U.N Mars Initiative","1");
		GenerateCorp("Thorgate","1");	
	}

}
