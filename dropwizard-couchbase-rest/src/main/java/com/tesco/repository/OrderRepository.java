package com.tesco.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.tesco.model.Order;
import com.tesco.service.JsonConverter;
import com.tesco.util.Utility;

/**
 * 
 * @author sindhuri
 *
 */
public class OrderRepository {

	JsonConverter<Order> converter;
	Bucket bucket;

	/**
	 * 
	 * @param converter
	 * @param bucket
	 */
	public OrderRepository(JsonConverter<Order> converter, Bucket bucket) {
		super();
		this.converter = converter;
		this.bucket = bucket;
	}

	/**
	 * 
	 * @param order
	 */
	public void saveOrder(Order order)  {

		String orderId = Utility.generateUniqueId();
		order.setOrderId(orderId);
		try {
			bucket.upsert(converter.serialize(orderId, order));
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}

	}

	/**
	 * 
	 * @return List<Order>
	 */
	public List<Order> retriveAllOrders() {

		List<Order> orderList = new ArrayList<>();

		bucket.query(N1qlQuery.simple(Index.createPrimaryIndex().on(
				bucket.name())));
		String query = "SELECT * FROM  %s WHERE _type='"
				+ Order.class.getName() + "'";
		String statement = String.format(query, bucket.name());
		N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(statement));

		List<N1qlQueryRow> rows = queryResult.allRows();
		System.out.println("rows are : "+rows.size());
		
		if (!(queryResult == null || "".equals(queryResult))) {
			for (N1qlQueryRow row : queryResult) {
				try {
					orderList.add(converter.deSerialize(row.value().get(bucket.name()).toString(),Order.class));
				} catch (IOException e) {
					throw new InternalServerErrorException(e);
				}

			}
			return orderList;
		} else {
			return orderList;
		}
	}

	/**
	 * 
	 * @return Order
	 */
	public Order getMaxPrice() {
		Order order = null;
		String query = "SELECT * FROM  %s WHERE _type='"
				+ Order.class.getName() + "' ORDER BY price DESC";
		try {
			String statement = String.format(query, bucket.name());
			N1qlQueryResult queryResult = bucket.query(N1qlQuery
					.simple(statement));

			if (queryResult != null && null != queryResult.allRows()) {
				order = converter.deSerialize(queryResult.allRows().get(0)
						.value().get(bucket.name()).toString(), Order.class);
			}
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}
		return order;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeOrder(String id){
		bucket.remove(id);
	}

}
