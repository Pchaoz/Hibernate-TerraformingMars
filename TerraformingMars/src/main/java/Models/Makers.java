package Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Makers")
public class Makers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idmakers;

	@Column(name = "Name")
	String name;

	@Column(name = "MaxNeightbours", nullable = false)
	int maxneightbours = 0;

	@Enumerated(EnumType.STRING)
	@Column(name = "TypeMaker")
	TypeMaker typeMaker;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	Corporations MakerOwner;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="MakersNeightbours", //NOM COLUMNA 
			joinColumns = @JoinColumn(name="id_maker"), //NOM CLAU QUE PORTA EL PES
			inverseJoinColumns = @JoinColumn(name="id_neightbour") //NOM DE LA CLAU QUE NO PORTA EL PES
	)
	private Set<Makers> neightbours = new HashSet<Makers>();
	
	
	
	public int getIdmakers() {
		return idmakers;
	}

	public void setIdmakers(int idmakers) {
		this.idmakers = idmakers;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getMaxneightbours() {
		return maxneightbours;
	}

	public Corporations getMakerOwner() {
		return MakerOwner;
	}

	public void setMakerOwner(Corporations makerOwner) {
		MakerOwner = makerOwner;
	}

	public void setMaxneightbours(int maxneightbours) {
		this.maxneightbours = maxneightbours;
	}


	public TypeMaker getTypeMaker() {
		return typeMaker;
	}


	public void setTypeMaker(TypeMaker typeMaker) {
		this.typeMaker = typeMaker;
	}
	
	public Makers(String name, int maxneightbours, TypeMaker typeMaker) {
		super();
		this.name = name;
		this.maxneightbours = maxneightbours;
		this.typeMaker = typeMaker;
	}
	
	public Makers(String name, int maxneighbours) {
		super();
		this.maxneightbours = maxneighbours;
		this.name = name;
	}

	public Makers() {
		super();
	}

	@Override
	public String toString() {
		return "Makers [idmakers=" + idmakers + ", name=" + name + ", maxneightbours=" + maxneightbours + ", typeMaker="
				+ typeMaker + ", MakerOwner=" + MakerOwner + ", neightbours=" + neightbours + "]";
	}


}