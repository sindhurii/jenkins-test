package com.tesco.resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.repository.OrderRepository;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest {
	
	@Mock
	OrderRepository mockOrderRepository;
	
	@InjectMocks
	OrderResource mockOrderResource = new OrderResource(mockOrderRepository);
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	

}
