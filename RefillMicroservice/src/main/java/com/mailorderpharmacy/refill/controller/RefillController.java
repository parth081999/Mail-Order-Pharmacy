package com.mailorderpharmacy.refill.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mailorderpharmacy.refill.entity.RefillOrder;
import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.exception.SubscriptionIdNotFoundException;
import com.mailorderpharmacy.refill.service.RefillOrderService;
import com.mailorderpharmacy.refill.service.RefillOrderSubscriptionService;

import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(produces = "application/json", value="Manages refills and timer")
public class RefillController {

	@Autowired 
	public RefillOrderService service;

	@Autowired
	RefillOrderSubscriptionService refillOrderSubscriptionService;
	/*
	 * Views the Refill Status
	 */
	@ApiOperation(value = "View status of the subscriptions per id", response = ResponseEntity.class)
	@GetMapping(path = "/viewRefillStatus/{subId}")
	public ResponseEntity<List<RefillOrder>> viewRefillStatus(@RequestHeader("Authorization") String token,
			@PathVariable("subId") long subId) throws SubscriptionIdNotFoundException, InvalidTokenException {
		log.info("Inside Refill Controller viewRefillStatus method");
		return ResponseEntity.ok().body(service.getStatus(subId, token));
	}
	
	@ApiOperation(value = "View refill dues as of date", response = ResponseEntity.class)
	@GetMapping(path = "/getRefillDuesAsOfDate/{memberId}/{date}")
	public ResponseEntity<List<RefillOrderSubscription>> getRefillDuesAsOfDate(
			@RequestHeader("Authorization") String token, @PathVariable("memberId") String memberId,
			@PathVariable("date") int date) throws InvalidTokenException {
		log.info("Inside Refill Controller getRefillDuesAsOfDate method");
		return ResponseEntity.ok().body(service.getRefillDuesAsOfDate(memberId, date, token));
	}
	/*
	 * 
	 */
	@ApiOperation(value = "View payment dues as of date", response = ResponseEntity.class)
	@GetMapping(path = "/getRefillPaymentDues/{subscriptionId}")
	public ResponseEntity<Boolean> getRefillPaymentDues(@RequestHeader("Authorization") String token,
			@PathVariable("subscriptionId") long subscriptionId) throws InvalidTokenException {
		log.info("Inside Refill Controller getRefillDuesAsOfDate method");

		return ResponseEntity.ok().body(service.getRefillPaymentDues(subscriptionId, token));
	}

	@ApiOperation(value = "Request refill", response = ResponseEntity.class)
	@PostMapping(path = "/requestAdhocRefill/{subId}/{payStatus}/{quantity}/{location}")
	public ResponseEntity<RefillOrder> requestAdhocRefill(@RequestHeader("Authorization") String token,
			@PathVariable("subId") long i, @PathVariable("payStatus") Boolean payStatus,
			@PathVariable("quantity") int quantity, @PathVariable("location") String location)
			throws ParseException, FeignException, InvalidTokenException, DrugQuantityNotAvailable {
		log.info("Inside Refill Controller requestAdhocRefill method");

		return ResponseEntity.accepted() 
				.body(service.requestAdhocRefill(i, payStatus, quantity, location, token));
	} 

	@ApiOperation(value = "Request normal refill", response = ResponseEntity.class)
	@PostMapping(path = "/requestRefillSubscription/{subId}/{memberId}/{quantity}/{time}")
	public ResponseEntity<RefillOrderSubscription> requestRefillSubscription(
			@RequestHeader("Authorization") String token, @PathVariable("subId") long subId,
			@PathVariable("memberId") String memberId, @PathVariable("quantity") int quantity,
			@PathVariable("time") int time) throws ParseException, InvalidTokenException {
		log.info("Inside Refill Controller requestRefillSubscription method");
		return ResponseEntity.accepted().body(
				refillOrderSubscriptionService.updateRefillOrderSubscription(subId, memberId, quantity, time ,token));
	}

	@ApiOperation(value = "View list of refills", response = ResponseEntity.class)
	@GetMapping(path = "/viewRefillOrderSubscriptionStatus")
	public ResponseEntity<List<RefillOrderSubscription>> viewRefillOrderSubscriptionStatus(
			@RequestHeader("Authorization") String token) throws InvalidTokenException {
		log.info("Inside Refill Controller viewRefillOrderSubscriptionStatus method");

		return ResponseEntity.ok().body(refillOrderSubscriptionService.getall(token));
	}

	@ApiOperation(value = "Starts global timer")
	@GetMapping(path = "/startTimer")
	public void startTimer(@RequestHeader("Authorization") String token) throws InvalidTokenException {
		log.info("Inside Refill Controller startTimer method");

		service.startTimer(token);
	}

	@ApiOperation(value = "Delete subscription", response = ResponseEntity.class)
	@DeleteMapping(path = "/deleteBySubscriptionId/{subscriptionId}" )
	public void deleteBySubscriptionId(@RequestHeader("Authorization") String token,
			@PathVariable("subscriptionId") long subscriptionId) throws InvalidTokenException {
		log.info("Inside Refill Controller deleteBySubscriptionId method");

		refillOrderSubscriptionService.deleteBySubscriptionId(subscriptionId, token);
	}

}
