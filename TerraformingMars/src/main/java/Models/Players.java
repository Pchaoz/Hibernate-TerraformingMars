package Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Player")
public class Players {
	
	@Override
	public String toString() {
		return "Players [IdPlayer=" + IdPlayer + ", Name=" + Name + ", Wins=" + Wins + ", cor=" + cor + ", Partides="
				+ Partides + ", Guanyades=" + Guanyades + "]";
	}
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPlayer")
	private int IdPlayer;
	@Column(name = "Name", length = 50, nullable = false)
	private String Name;
	@Column(name = "Wins")
	private int Wins = 0;
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	private Corporations cor;
	@ManyToMany(mappedBy = "Jugadors")
	private Set<Games> Partides = new HashSet<Games>();
	@OneToMany(mappedBy = "Guanyador")
	private Set<Games> Guanyades = new HashSet<Games>();
	
	public Players(String name) {
		super();
		Name = name;
	}
	
	
	public int getIdPlayer() {
		return IdPlayer;
	}
	public void setIdPlayer(int idPlayer) {
		IdPlayer = idPlayer;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getWins() {
		return Wins;
	}
	public void setWins(int wins) {
		Wins = wins;
	}
	
}