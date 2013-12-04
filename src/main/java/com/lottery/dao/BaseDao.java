package com.lottery.dao;

import java.util.List;
import java.util.Set;

public interface BaseDao<T>{

	public void persist(T t);
	public List<T> find(String jpql, Object[] param);
	public T findById(Class<T> clazz,long id);
	public void saveList(Set<T> list);
	public void update(T t);
	public void deleteById(Class<T> clazz, long id);
	public void delete(T t);
	public void executeUpdate(String jpql, Object[] param);
	public long findTotalCount(String jpql,Object[] param);
}
