package com.mailorderpharmacy.refill.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**Interface for connecting with authentication service*/
@FeignClient(url="http://localhost:8082/subscriptionapi",name="SubscriptionClient")
public interface SubscriptionClient {
	
	/**
	 * @param sId
	 * @param token
	 * @return
	 */
	@GetMapping("/getDrugName/{sId}")
	public ResponseEntity<String> getDrugNameBySubscriptionId(@PathVariable("sId") Long sId,@RequestHeader("Authorization") String token);

}
