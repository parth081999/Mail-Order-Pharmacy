package com.mailorderpharmacy.refill.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**Interface for connecting with authentication service*/
@FeignClient(url="http://localhost:8081/drugdetailapi",name="drugdetail")
public interface DrugDetailClient {
	/**
	 * @param token
	 * @param name
	 * @param location
	 * @param quantity
	 * @return
	 */
	@PutMapping("/updateDispatchableDrugStock/{name}/{location}/{quantity}")
	public ResponseEntity<Object> updateQuantity(@RequestHeader("Authorization") String token,@PathVariable("name") String name, @PathVariable("location") String location,
			@PathVariable("quantity") int quantity);

}
