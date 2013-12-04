package com.lottery.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lottery.dao.BaseDao;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void persist(T t) {
		// TODO Auto-generated method stub
		em.persist(t);
	}

	@Override
	public List<T> find(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		for (int i = 1; param!=null&&i <= param.length; i++) {
			query.setParameter(i, param[i - 1]);
		}
		return query.getResultList();
	}

	@Override
	public void saveList(List<T> list) {
		// TODO Auto-generated method stub
		if(list!=null&&list.size()>0){
			for(T t : list){
				this.persist(t);
			}
		}
	}

	@Override
	public void executeUpdate(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		if(param!=null&&param.length>0){
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		query.executeUpdate();
	}

	@Override
	public T findById(Class<T> clazz, long id) {
		// TODO Auto-generated method stub
		return em.find(clazz, id);
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		em.merge(t);
	}

	@Override
	public void deleteById(Class<T> clazz, long id) {
		// TODO Auto-generated method stub
		T t = this.findById(clazz, id);
		if(t!=null){
			this.delete(t);
		}
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		em.remove(t);
	}

	@Override
	public long findTotalCount(String jpql, Object[] param) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(jpql);
		if(param!=null&&param.length>0){
			//这个版本要求hql改成use named parameters or JPA-style positional parameters，以前的from Model where id=?这种语句要改成from Model where id=?0，设置参数要用org.hibernate.Query.setParameter("0", java.lang.Object)才行，否则升级完系统会有警告
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return (Long) query.getSingleResult();
	} 
	
}
