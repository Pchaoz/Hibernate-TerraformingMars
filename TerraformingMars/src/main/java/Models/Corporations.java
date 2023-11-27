package Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
