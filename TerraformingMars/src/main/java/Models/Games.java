package Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Games")
public class Games {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idGames")
	private int idGame;
	@Column(name="Oxygen")
	private int Oxygen = 0;
	@Column(name="Temperature")
	private int Temperature = -30;
	@Column(name="Oceans")
	private int Oceans = 0;
	@Column
	private LocalDateTime DateStart = LocalDateTime.now();
	@Column
	private LocalDateTime EndDateTime = null;
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, targetEntity = Players.class)
	@JoinTable(name ="PlayerGames", joinColumns = @JoinColumn(name ="PartidaID"), inverseJoinColumns = @JoinColumn(name="JugadorId"))
	private Set<Players> Jugadors = new HashSet<Players>();
	@ManyToOne
	//@JoinColumn(name="idGuanyador")
	@JoinTable(name ="GamesWins", joinColumns = @JoinColumn(name ="PartidaID"), inverseJoinColumns = @JoinColumn(name="GuanyadorID"))
	private Players Guanyador;
}
