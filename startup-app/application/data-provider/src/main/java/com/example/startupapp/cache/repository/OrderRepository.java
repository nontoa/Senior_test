package com.example.startupapp.cache.repository;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.example.startupapp.dao.OrderDao;

/**
 * The repository class to access to the database to orders.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Repository
public class OrderRepository {

	public static final String ORDER_KEY = "ORDER";
	public static final String HASH_KEY = "orderId";

	private HashOperations hashOperations;

	private RedisTemplate redisTemplate;

	public OrderRepository(RedisTemplate redisTemplate){
		this.redisTemplate = redisTemplate;
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	public Long save(OrderDao order) {
		Long newOrderId = hashOperations.increment(ORDER_KEY, HASH_KEY, 1);
		order.setId(newOrderId);
		hashOperations.put(ORDER_KEY, newOrderId, order);
		return newOrderId;
	}

	public List findAll(){
		return hashOperations.values(ORDER_KEY);
	}

	public OrderDao findById(Long id) {
		return (OrderDao) hashOperations.get(ORDER_KEY, id);
	}

	public void update(Long orderId, OrderDao order) {
		hashOperations.put(ORDER_KEY, orderId, order);
	}

	public void delete(String id) {
		hashOperations.delete(ORDER_KEY, id);
	}

}
