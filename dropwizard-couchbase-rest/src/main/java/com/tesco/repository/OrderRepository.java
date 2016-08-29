package com.tesco.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;
import com.tesco.model.Order;
import com.tesco.service.JsonConverter;
import com.tesco.util.Utility;

public class OrderRepository {

	JsonConverter<Order> converter;
	Bucket bucket;

	public OrderRepository(JsonConverter<Order> converter, Bucket bucket) {
		super();
		this.converter = converter;
		this.bucket = bucket;
	}

	public void saveOrder(Order order)  {

		String orderId = Utility.generateUniqueId();
		order.setOrderId(orderId);
		try {
			bucket.upsert(converter.serialize(orderId, order));
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}

	}

	public List<Order> retriveAllOrders() {

		List<Order> orderList = new ArrayList<>();

		bucket.query(N1qlQuery.simple(Index.createPrimaryIndex().on(
				bucket.name())));
		String query = "SELECT * FROM  %s WHERE _type='"
				+ Order.class.getName() + "'";
		String statement = String.format(query, bucket.name());
		N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(statement));

		//if(queryResult.equals(""))
		if (!(queryResult == null || "".equals(queryResult))) {
			for (N1qlQueryRow row : queryResult) {
				try {
					orderList
							.add(converter.deSerialize(
									row.value().get(bucket.name()).toString(),
									Order.class));
				} catch (IOException e) {
					throw new InternalServerErrorException(e);
				}

			}
			return orderList;
		} else {
			return orderList;
		}
	}

	// query document by ViewResult
	public Order retriveById(String id)  {

		Order order = null;
		ViewResult result = bucket.query(ViewQuery.from("dev_orders",
				"get_by_price").key(id));
		for (ViewRow row : result) {
			JsonDocument doc = row.document();
			if (doc.content().getString("_type").equals(Order.class.getName())) {
				try {
					order = converter.deSerialize(doc.content().toString(),
							Order.class);
				} catch (IOException e) {
					throw new InternalServerErrorException(e);
				}
			}
		}
		return order;

	}

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
	
	public void removeOrder(String id){
		bucket.remove(id);
	}

}
