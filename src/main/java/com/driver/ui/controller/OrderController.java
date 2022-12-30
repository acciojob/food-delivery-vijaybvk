package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	OrderService orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto=orderService.getOrderById(id);
		OrderDetailsResponse orderDetailsResponse=null;
		if(orderDto!=null){
			orderDetailsResponse=OrderDetailsResponse.builder().orderId(orderDto.getOrderId()).cost(orderDto.getCost())
					.items(orderDto.getItems()).status(orderDto.isStatus()).userId(orderDto.getUserId()).build();
		}
		return orderDetailsResponse;
	}

	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto=OrderDto.builder().userId(order.getUserId()).items(order.getItems()).cost(order.getCost()).build();
		orderService.createOrder(orderDto);
		OrderDetailsResponse orderDetailsResponse=OrderDetailsResponse.builder().cost(orderDto.getCost())
				.items(orderDto.getItems()).status(true).userId(orderDto.getUserId()).build();
		return orderDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto=OrderDto.builder().items(order.getItems()).cost(order.getCost()).userId(order.getUserId()).status(true).build();
		OrderDto orderDto1=orderService.updateOrderDetails(id, orderDto);
		OrderDetailsResponse orderDetailsResponse=null;
		if(orderDto1!=null){
			orderDetailsResponse=OrderDetailsResponse.builder().items(orderDto1.getItems()).cost(orderDto1.getCost())
					.userId(orderDto1.getUserId()).status(true).build();
		}
		return orderDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();
		orderService.deleteOrder(id);
		return operationStatusModel;
	}

	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> orderDtos=orderService.getOrders();
		List<OrderDetailsResponse> orderDetailsResponses=new ArrayList<>();
		for(OrderDto orderDto:orderDtos){
			OrderDetailsResponse orderDetailsResponse=OrderDetailsResponse.builder().items(orderDto.getItems()).cost(orderDto.getCost())
					.userId(orderDto.getUserId()).status(true).build();
		}
		return orderDetailsResponses;
	}
}