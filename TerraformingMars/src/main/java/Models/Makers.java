package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Makers")
public class Makers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idmakers;

	@Column(name = "Name", nullable = false)
	String name;

	@Column(name = "Description")
	String description;

	@Column(name = "MaxNeightbours", nullable = false)
	int maxneightbours = 0;

	@Enumerated(EnumType.STRING)
	@Column(name = "TypeMaker")
	TypeMaker typeMaker;


	
	
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMaxneightbours() {
		return maxneightbours;
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

	
	public Makers(String name, String description, int maxneightbours, TypeMaker typeMaker) {
		super();
		this.name = name;
		this.description = description;
		this.maxneightbours = maxneightbours;
		this.typeMaker = typeMaker;
	}

	public Makers() {
		super();
	}

	@Override
	public String toString() {
		return "Makers [idmakers=" + idmakers + ", name=" + name + ", description=" + description + ", maxneightbours="
				+ maxneightbours + ", typeMaker=" + typeMaker + "]";
	}

}