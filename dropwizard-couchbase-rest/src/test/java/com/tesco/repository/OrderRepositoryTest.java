package com.tesco.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.SimpleN1qlQuery;
import com.tesco.model.Order;
import com.tesco.service.JsonConverter;
import com.tesco.util.Utility;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(Utility.class)
public class OrderRepositoryTest {
	
	@Mock
	JsonConverter<Order> mockConverter;
	@Mock
	Bucket mockBucket;
	
	@InjectMocks
	OrderRepository mockOrderRepository = new OrderRepository(mockConverter, mockBucket);
	
	@Mock
	Utility mockUtility;
	@Mock
	Order mockOrder;
	@Mock
	JsonDocument mockJsonDoc;
	@Mock
	JsonDocument mockJsonNewDoc;
	@Mock
	N1qlQuery mockN1qlQuery;
	@Mock
	SimpleN1qlQuery mockSimpleN1qlQuery;
	@Mock
	N1qlQueryResult mockN1qlQueryResult;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSaveOrder() {
		
		/*PowerMockito.mockStatic(Utility.class);
        when(Utility.generateUniqueId()).thenReturn(Mockito.anyString());
		try {
			Mockito.when(mockConverter.serialize(Mockito.anyString(), Mockito.anyObject())).thenReturn(mockJsonDoc);
			Mockito.when(mockBucket.upsert(mockJsonDoc)).thenReturn(mockJsonDoc);
			mockOrderRepository.saveOrder(mockOrder);
		} catch (IOException e) {
			throw new InternalServerErrorException(e);
		}*/
	}
	

}
