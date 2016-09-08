package com.tesco.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tesco.model.Order;
import com.tesco.repository.OrderRepository;

/**
 * 
 * @author sindhuri
 *
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

	OrderRepository orderRepository;
	/**
	 * 
	 * @param orderRepository
	 */
	public OrderResource(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	/**
	 * 
	 * @param orderName
	 * @param orderType
	 * @param quantity
	 * @param price
	 * @return Response
	 */
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(@FormParam("orderName") String orderName, @FormParam("orderType") String orderType,
			@FormParam("quantity") int quantity,
			@FormParam("price") double price) {

		Order order = new Order();
		order.setOrderName(orderName);
		order.setOrderType(orderType);
		order.setQuantity(quantity);
		order.setPrice(price);

		orderRepository.saveOrder(order);

		return Response.ok("Order Created").build();

	}
	
	/**
	 * 
	 * @return Response
	 */
	@GET
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllOrders() {
		
		List<Order> orderList = orderRepository.retriveAllOrders();
		return Response.ok().entity(orderList).build();
		
	}
	
	/**
	 * 
	 * @param id
	 * @return Response
	 */
	@GET
	@Path("/getOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@QueryParam("id") String id){
		
		Order order = orderRepository.retriveById(id);
		return Response.ok().entity(order).build();
		
	}
	
	/**
	 * 
	 * @return Response
	 */
	@GET
	@Path("/getPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaxPrice(){
		
		Order order = orderRepository.getMaxPrice();
		return Response.ok().entity(order.getPrice()).build();
		
	}
	
	/**
	 * 
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("/deleteOrder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOrder(@QueryParam("id") String id){
		orderRepository.removeOrder(id);
		return Response.ok().build();
	}
	
}
