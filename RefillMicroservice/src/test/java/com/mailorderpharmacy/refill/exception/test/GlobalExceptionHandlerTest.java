package com.mailorderpharmacy.refill.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.ServiceUnavailableException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.GlobalExceptionHandler;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.exception.SubscriptionIdNotFoundException;

@SpringBootTest(classes = GlobalExceptionHandlerTest.class)
class GlobalExceptionHandlerTest {
		
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	void invalidTokenException()
	{
		assertEquals(HttpStatus.UNAUTHORIZED, globalExceptionHandler.invalidTokenException
				(new InvalidTokenException("invalidTokenException")).getStatusCode());
	}
	
	@Test
	void subscriptionIdNotFoundException()
	{
		assertEquals(HttpStatus.NOT_FOUND, globalExceptionHandler.subscriptionIdNotFoundException
				(new SubscriptionIdNotFoundException("subscriptionIdNotFoundException")).getStatusCode());
	}
	
	@Test
	void serviceUnavailableException()
	{
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, globalExceptionHandler.serviceUnavailableException
				(new ServiceUnavailableException("serviceUnavailableException")).getStatusCode());
	}
	
	@Test
	void drugQuantityNotAvailable()
	{
		assertEquals(HttpStatus.NOT_FOUND, globalExceptionHandler.drugQuantityNotAvailable
				(new DrugQuantityNotAvailable("DrugQuantityNotAvailable")).getStatusCode());
	}
	

}
