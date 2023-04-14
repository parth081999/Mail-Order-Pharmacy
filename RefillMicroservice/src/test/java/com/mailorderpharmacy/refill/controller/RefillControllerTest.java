package com.mailorderpharmacy.refill.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;

import java.util.List;

import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.mailorderpharmacy.refill.entity.RefillOrder;
import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.service.RefillOrderService;
import com.mailorderpharmacy.refill.service.RefillOrderSubscriptionService;

import feign.FeignException;

@SpringBootTest(classes = RefillControllerTest.class)
class RefillControllerTest {

	@InjectMocks
	RefillController refillController;

	@Mock
	public RefillOrderService service;

	@Mock
	RefillOrderSubscriptionService refillOrderSubscriptionService;

//	@MockBean
//	MockMvc mockMvc;

	@Test
	void viewRefillStatusTest() throws Exception {

//		MvcResult result = mockMvc.perform(get("/viewRefillStatus/45")).andReturn();
//		assertThrows(SubscriptionIdNotFoundException.class, ()->result.getResponse());
//		Date date =new Date();
//		RefillOrder refillOrder = new RefillOrder(1,date,true,1,1,"1");
//		List<RefillOrder> list = new ArrayList<RefillOrder>();
//		list.add(refillOrder);
//		//when(service.getStatus(1, "token")).
		ResponseEntity<List<RefillOrder>> s = refillController.viewRefillStatus("token", 1);

		String expected = "200 OK";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);

	}

	@Test
	void getRefillDuesAsOfDateTest() throws InvalidTokenException {
		ResponseEntity<List<RefillOrderSubscription>> s = refillController.getRefillDuesAsOfDate("token", "memberId", 45);
		String expected = "200 OK";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);
	}

	@Test
	void getRefillDuesAsOfPayment() throws InvalidTokenException {
		ResponseEntity<Boolean> s = refillController.getRefillPaymentDues("token",45 );
		String expected = "200 OK";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);
	}

	@Test
	void requestAdhocRefill() throws InvalidTokenException, FeignException, ParseException, DrugQuantityNotAvailable {
		ResponseEntity<RefillOrder> s= refillController.requestAdhocRefill("token",54,true,45,"salem");

		String expected = "202 ACCEPTED";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);
	}

	@Test
	void requestRefillSubscription() throws InvalidTokenException, ParseException {
		ResponseEntity<RefillOrderSubscription> s = refillController.requestRefillSubscription("memberId", 4, "token", 45, 5);

		String expected = "202 ACCEPTED";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);
	}

	@Test
	void viewRefillOrderSubscriptionStatus() throws InvalidTokenException {
		ResponseEntity<List<RefillOrderSubscription>> s = refillController.viewRefillOrderSubscriptionStatus ("token");
		String expected = "200 OK";

		String actual = s.getStatusCode().toString();

		assertEquals(expected, actual);
	}

	@Test
	void startTimer() throws InvalidTokenException {
		refillController.startTimer("token");
		String actual = "OK";
		String expected = "OK";
		assertEquals(actual, expected);
	}
	@Test
	void deleteBySubscriptionId() throws InvalidTokenException {
		refillController.deleteBySubscriptionId("token",45);
		String actual = "OK";
		String expected = "OK";
		assertEquals(actual, expected);
	}


}
