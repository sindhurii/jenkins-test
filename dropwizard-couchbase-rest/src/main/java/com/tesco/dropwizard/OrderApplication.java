package com.tesco.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.tesco.model.Order;
import com.tesco.repository.OrderRepository;
import com.tesco.resource.OrderResource;
import com.tesco.service.JsonConverter;
/**
 * 
 * @author sindhuri
 *
 */
public class OrderApplication extends Application<OrderConfiguration> {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		new OrderApplication().run(args);

	}

	@Override
	public void run(OrderConfiguration configuration, Environment environment) throws Exception {

		Cluster cluster = CouchbaseCluster.create(DefaultCouchbaseEnvironment.builder()
				.connectTimeout(10000).build());// default 8092
        Bucket bucket = cluster.openBucket();
        JsonConverter<Order> converter = new JsonConverter<>();
		OrderRepository orderRepository = new OrderRepository(converter, bucket);
		OrderResource orderResource = new OrderResource(orderRepository);
		environment.jersey().register(orderResource);
	}
	
}
