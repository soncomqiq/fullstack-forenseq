package io.forensic.springboot.Other;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class OtherRepositoryImpl implements OtherRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> mycustomQuery(String query) {
		List<Object[]> temp = entityManager.createNativeQuery(query).getResultList();
		System.out.println(temp);
		return temp;
	}

	@Override
	public List<Object[]> mycustomQueryY(String query) {
		List<Object[]> temp = entityManager.createNativeQuery(query).getResultList();
		System.out.println(temp);
		return temp;
	}

	@Override
	public List<Object[]> mycustomQueryX(String query) {
		List<Object[]> temp = entityManager.createNativeQuery(query).getResultList();
		System.out.println(temp);
		return temp;
	}   
}