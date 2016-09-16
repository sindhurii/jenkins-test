package com.tesco.resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.tesco.model.Order;
import com.tesco.repository.OrderRepository;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest {

	@Mock
	OrderRepository mockOrderRepository;
	
	@InjectMocks
	OrderResource mockOrderResource = new OrderResource(mockOrderRepository);
	
	@Mock
	Order mockOrder;
	
	@Mock
	List<Order> mockOrderList;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateOrder(){
		
		Order order = new Order();
		order.setOrderName("mobile");
		order.setOrderType("sales order");
		order.setQuantity(1);
		order.setPrice(2000);
		
		Mockito.doNothing().when(mockOrderRepository).saveOrder(order);
		Response response = mockOrderResource.createOrder(order.getOrderName(),order.getOrderType(),order.getQuantity(),order.getPrice());
		assertEquals(200, response.getStatus());
		Mockito.verify(mockOrderRepository, Mockito.times(1)).saveOrder(Mockito.anyObject());
	}
	
	@Test
	public void testGetAllOrders(){
		Mockito.when(mockOrderRepository.retriveAllOrders()).thenReturn(mockOrderList);
		Response response = mockOrderResource.getAllOrders();
		assertEquals(200, response.getStatus());
		Mockito.verify(mockOrderRepository, Mockito.times(1)).retriveAllOrders();
	}
	
	@Test
	public void testDeleteOrder(){
		Mockito.doNothing().when(mockOrderRepository).removeOrder(Mockito.anyString());
		Response response = mockOrderResource.deleteOrder("12345");
		assertEquals(200, response.getStatus());
		Mockito.verify(mockOrderRepository, Mockito.times(1)).removeOrder(Mockito.anyString());
	}
}
