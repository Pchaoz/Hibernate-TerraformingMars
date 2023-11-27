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

	@Override
	public String toString() {
		return "Games [idGame=" + idGame + ", Oxygen=" + Oxygen + ", Temperature=" + Temperature + ", Oceans=" + Oceans
				+ ", DateStart=" + DateStart + ", EndDateTime=" + EndDateTime + ", Jugadors=" + Jugadors
				+ ", Guanyador=" + Guanyador + "]";
	}
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
	
	
	
	
	
	public Games(int oxygen, int temperature, int oceans) {
		super();
		Oxygen = oxygen;
		Temperature = temperature;
		Oceans = oceans;
	}
	public Games() {
		
	}
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	public int getOxygen() {
		return Oxygen;
	}
	public void setOxygen(int oxygen) {
		Oxygen = oxygen;
	}
	public int getTemperature() {
		return Temperature;
	}
	public void setTemperature(int temperature) {
		Temperature = temperature;
	}
	public int getOceans() {
		return Oceans;
	}
	public void setOceans(int oceans) {
		Oceans = oceans;
	}
	public LocalDateTime getDateStart() {
		return DateStart;
	}
	public void setDateStart(LocalDateTime dateStart) {
		DateStart = dateStart;
	}
	public LocalDateTime getEndDateTime() {
		return EndDateTime;
	}
	public void setEndDateTime(LocalDateTime endDateTime) {
		EndDateTime = endDateTime;
	}
	public Set<Players> getJugadors() {
		return Jugadors;
	}
	public void setJugadors(Set<Players> jugadors) {
		Jugadors = jugadors;
	}
	public Players getGuanyador() {
		return Guanyador;
	}
	public void setGuanyador(Players guanyador) {
		Guanyador = guanyador;
	}
}
