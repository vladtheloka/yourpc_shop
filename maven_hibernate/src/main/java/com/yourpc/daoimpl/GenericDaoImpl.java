package com.yourpc.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yourpc.dao.GenericDao;
@Repository
public class GenericDaoImpl<T, K> implements GenericDao<T, K>
{
	protected Class<T> entityClass;
	
	@PersistenceContext
	private EntityManager entityManager;

	public GenericDaoImpl(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	public GenericDaoImpl(){}

	public EntityManager getEntityManager() 
	{
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	public Class<T> getEntityClass()
	{
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	@Transactional
	public T add(T entity) 
	{
		entityManager.persist(entity);
		return entity;
	}

	@Transactional
	public T update(T entity) 
	{
		entityManager.merge(entity);
		return entity;
	}

	@Transactional
	public void delete(K fieldName, K fieldValue) 
	{
		T entity = getOne(fieldName, fieldValue);
		entityManager.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public T getOne(K fieldName, K fieldValue) 
	{
		return  (T) entityManager
				.createQuery("select u from " + entityClass.getSimpleName()+ " u where u." + fieldName + " =:value1")
				.setParameter("value1", fieldValue)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> getAll()
	{
		return entityManager
				.createQuery("from " + entityClass.getName())
				.getResultList();
	}
}
