package com.mailorderpharmacy.refill.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mailorderpharmacy.refill.entity.TokenValid;

/**Interface for connecting with authentication service*/
@FeignClient(name = "authapp", url = "http://localhost:8090/authapp")
public interface AuthFeign {

	/**
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/validate")
	public TokenValid getValidity(@RequestHeader("Authorization") final String token);
}
