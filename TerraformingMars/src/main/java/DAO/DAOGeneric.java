package DAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public class DAOGeneric<T, ID extends Serializable> implements IDAOGeneric<T, ID>{

	Class<T> entityClass;
	public DAOGeneric(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void Persistir(T entity) {
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		session.persist(entity);
		session.getTransaction().commit();	
	}

	@Override
	public T Search(ID id) {
		// TODO Auto-generated method stub
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		T Entity = session.find(entityClass, id);
		session.getTransaction().commit();
		return Entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> Llistar() {
		// TODO Auto-generated method stub
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		List<T> llistat = session.createQuery("from "+entityClass.getName()).getResultList();
		session.getTransaction().commit();
		return llistat;
	}

	@Override
	public void DeleteID(ID id) {
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		T Entity = session.find(entityClass, id);
		session.remove(Entity);
		session.getTransaction().commit();
	}

	@Override
	public void DeleteEntity(T entity) {
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();
		session.remove(entity);
		session.getTransaction().commit();	
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		Session session = Utils.getSessionFactory().getCurrentSession();
		session.getTransaction().begin();	
		session.merge(entity);
		session.getTransaction().commit();
	}
}
