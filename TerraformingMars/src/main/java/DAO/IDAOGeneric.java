package DAO;

import java.io.Serializable;
import java.util.List;

public interface IDAOGeneric <T, ID extends Serializable>{
	void Persistir(T entity);
	T Search(ID id);
	List<T> Llistar();
	void DeleteID(ID id);
	void update(T entity);
	void DeleteEntity(T entity);
}
