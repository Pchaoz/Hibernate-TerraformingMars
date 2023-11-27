package Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Corporations")
public class Corporations {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int idCorporations;
	@Column(length = 50, nullable = false)
	private String Name;
	@Column(length = 100, nullable = false)
	private String Description;
	@Column
	private int Victorypoints = 0;
	@OneToOne(mappedBy = "cor", cascade = CascadeType.PERSIST)
	private Players player;
	@OneToMany(mappedBy = "Copr")
	private Set<Makers> Casilles = new HashSet<Makers>();
	
	
	
	public Corporations(String name, String description) {
		super();
		Name = name;
		Description = description;
	}
	
	@Override
	public String toString() {
		return "Corporations [idCorporations=" + idCorporations + ", Name=" + Name + ", Description=" + Description
				+ ", Victorypoints=" + Victorypoints + ", player=" + player + "]";
	}

	public int getIdCorporations() {
		return idCorporations;
	}
	public void setIdCorporations(int idCorporations) {
		this.idCorporations = idCorporations;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getVictorypoints() {
		return Victorypoints;
	}
	public void setVictorypoints(int victorypoints) {
		Victorypoints = victorypoints;
	}
	public Players getPlayer() {
		return player;
	}
	public void setPlayer(Players player) {
		this.player = player;
	}
	
}
