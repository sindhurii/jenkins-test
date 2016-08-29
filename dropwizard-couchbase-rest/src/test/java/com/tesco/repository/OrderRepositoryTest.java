package com.tesco.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.couchbase.client.java.Bucket;
import com.tesco.model.Order;
import com.tesco.service.JsonConverter;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryTest {
	
	@Mock
	JsonConverter<Order> mockConverter;
	
	@Mock
	Bucket mockBucket;
	@InjectMocks
	OrderRepository mockOrderRepository = new OrderRepository(mockConverter, mockBucket);
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	/*public void saveOrder(Order order)  {

		String orderId = Utility.generateUniqueId();
		order.setOrderId(orderId);
		try {
			bucket.upsert(converter.serialize(orderId, order));
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}

	}*/

}
