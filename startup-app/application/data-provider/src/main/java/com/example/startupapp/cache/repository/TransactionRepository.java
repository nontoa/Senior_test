package com.example.startupapp.cache.repository;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.example.startupapp.dao.TransactionDao;

/**
 * The repository class to access to the database to transactions.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Repository
public class TransactionRepository{

	public static final String TRANSACTION_KEY = "TRANSACTION";

	private HashOperations hashOperations;

	private RedisTemplate redisTemplate;

	public TransactionRepository(RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	public void save(TransactionDao transaction) {
		hashOperations.put(TRANSACTION_KEY, transaction.getId(), transaction);
	}

	public List findAll(){
		return hashOperations.values(TRANSACTION_KEY);
	}

	public TransactionDao findById(String id) {
		return (TransactionDao) hashOperations.get(TRANSACTION_KEY, id);
	}

	public void update(TransactionDao transaction) {
		save(transaction);
	}

	public void delete(String id) {
		hashOperations.delete(TRANSACTION_KEY, id);
	}

}
