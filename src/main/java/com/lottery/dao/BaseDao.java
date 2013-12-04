package com.lottery.dao;

import java.util.List;

public interface BaseDao<T>{

	public void persist(T t);
	public List<T> find(String jpql, Object[] param);
	public T findById(Class<T> clazz,long id);
	public void saveList(List<T> list);
	public void update(T t);
	public void deleteById(Class<T> clazz, long id);
	public void delete(T t);
	public void executeUpdate(String jpql, Object[] param);
	public long findTotalCount(String jpql,Object[] param);
}
