package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.entity.Order;
import com.order.exception.ResourceNotFoundException;
import com.order.repository.OrderRepository;


@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
//	Welcoming message
	@GetMapping("/welcome")
	public String welcome() {
		
		return "Welcome to Park & Pick users";
	}
	
	// get all users
	@GetMapping("/all")
	public List<Order> getAllProducts(){
    	System.out.println("All products are listed here");
		return this.orderRepository.findAll();
    }
	// get user by id
	@GetMapping("/{id}")
	public Order getUseById(@PathVariable(value = "id") int order_id) {
		
		return this.orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ order_id));
	
	}
	// create user 
	
	@PostMapping("/register")
	
	public Order createUser(@RequestBody Order order) {
		
		return this.orderRepository.save(order);
	}
	
	// update user
	@PutMapping("/{id}")
	public Order updateUser(@RequestBody Order order, @PathVariable("id") int order_id) {
		Order existing = this.orderRepository.findById(order_id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ order_id));
		existing.setDescription(order.getDescription());
		existing.setProductId(order.getProductId());
		existing.setQuantity(order.getQuantity());
				
		this.orderRepository.save(existing);
		return existing;
	}
	//delete user by id
    
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> deleteUser(@PathVariable("id") int id){
		Order existing = this.orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ id));
	    this.orderRepository.delete(existing);
	    return ResponseEntity.ok().build();
	}
}
