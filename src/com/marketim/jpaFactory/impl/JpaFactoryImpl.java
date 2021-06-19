package com.marketim.jpaFactory.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.marketim.jpaFactory.JpaFactory;

public class JpaFactoryImpl implements JpaFactory {

	public static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();
	private EntityManager entityManager = threadLocal.get();

	@Override
	public EntityManager getEntityManager() {
		if (entityManager == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence-unit");
			this.entityManager = factory.createEntityManager();
			threadLocal.set(entityManager);
		}

		return entityManager;
	}

	@Override
	public EntityTransaction getEntityTransaction() {

		EntityTransaction transaction = this.entityManager.getTransaction();

		return transaction;
	}

}
