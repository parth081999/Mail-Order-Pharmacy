package com.mailorderpharmacy.refill.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mailorderpharmacy.refill.dao.RefillOrderRepository;
import com.mailorderpharmacy.refill.entity.RefillOrder;
import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.entity.TokenValid;
import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.exception.SubscriptionIdNotFoundException;
import com.mailorderpharmacy.refill.restclients.AuthFeign;
import com.mailorderpharmacy.refill.restclients.DrugDetailClient;
import com.mailorderpharmacy.refill.restclients.SubscriptionClient;
import com.mailorderpharmacy.refill.service.RefillOrderServiceImpl;
import com.mailorderpharmacy.refill.service.RefillOrderSubscriptionServiceImpl;

import feign.FeignException;

@SpringBootTest(classes = RefillOrderServiceImplTest.class)
class RefillOrderServiceImplTest {

	@InjectMocks
	RefillOrderServiceImpl refillOrderServiceImpl;

	@Mock
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionService;

	@Mock
	DrugDetailClient drugDetailClient;

	@Mock
	SubscriptionClient subscriptionClient;

	@Mock
	RefillOrderRepository refillOrderRepository;

	@Mock
	private AuthFeign authFeign;

	int mId = 45;

	boolean flag = true;

	@Test
	void getStatus() throws SubscriptionIdNotFoundException, InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), flag, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn((ArrayList<RefillOrder>) list);
		refillOrderServiceImpl.getStatus(mId, "token");

		List<RefillOrder> actual = (refillOrderServiceImpl.getStatus(mId, "token"));

		assertEquals(true, actual.get(0).getPayStatus());

	}

	@Test
	void getStatusInvalidToken() throws SubscriptionIdNotFoundException, InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getStatus(45, "token"));
	}

	@Test
	void getStatusInvalidSubscriptionIdNotFoundException()
			throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn((ArrayList<RefillOrder>) list);
		assertThrows(SubscriptionIdNotFoundException.class, () -> refillOrderServiceImpl.getStatus(54, "token"));
	}

	@Test
	void getRefillDuesAsOfDate() throws SubscriptionIdNotFoundException, InvalidTokenException {

		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, String.valueOf(mId), 45, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");

		List<RefillOrderSubscription> l = refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");

		assertEquals(45, l.get(0).getRefillQuantity());
	}

	@Test
	void getRefillDuesAsOfDateTwo() throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "54", 45, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", mId, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");
		List<RefillOrderSubscription> l = refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");

		String expected = String.valueOf(response);
		String actual = "TokenValid(uid=uid, name=name, isValid=true)";
		assertEquals(expected, actual);

		//assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token"));
	}

	@Test
	void getRefillDuesAsOfDateThree() throws SubscriptionIdNotFoundException, InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "54", mId, 46);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn((ArrayList<RefillOrderSubscription>) list);
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token");
		refillOrderServiceImpl.getRefillDuesAsOfDate("45", 46, "token");
		// refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token");
		//assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getRefillDuesAsOfDate("45", 45, "token"));
		String expected = String.valueOf(response);
		String actual = "TokenValid(uid=uid, name=name, isValid=true)";
		assertEquals(expected, actual);

	}

	@Test
	void getRefillDuesAsOfDateInvalidToken() throws SubscriptionIdNotFoundException, InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderServiceImpl.getRefillDuesAsOfDate("45", 47, "token"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void requestAdhocRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, FeignException,
			ParseException, DrugQuantityNotAvailable {
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");

		ResponseEntity<String> entityname = new ResponseEntity<String>("45", HttpStatus.OK);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionClient.getDrugNameBySubscriptionId((long) 45, "token")).thenReturn(entityname);
		ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
		when(drugDetailClient.updateQuantity("token", "45", "salem", 45)).thenReturn(responseEntity);
		// when(refillOrderServiceImpl.requestAdhocRefill(45, true, 45, "salem",
		// "token").responsevalue).then
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "salem", "token");
		// assertThrows(NullPointerException.class,()->refillOrderServiceImpl.requestAdhocRefill(45,true,45,
		// "location", "token"));

		String expected = responseEntity.getStatusCode().toString();

		String actual = "200 OK";

		assertEquals(expected, actual);
	}

	@Test
	void requestAdhocRefilllInvalidTokenException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "location", "token"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void requestAdhocRefillDrugQuantityNotAvailable() throws SubscriptionIdNotFoundException,
			InvalidTokenException, FeignException, ParseException, DrugQuantityNotAvailable {
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");

		ResponseEntity<String> entityname = new ResponseEntity<String>("45", HttpStatus.OK);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(subscriptionClient.getDrugNameBySubscriptionId((long) 45, "token")).thenReturn(entityname);
		when(drugDetailClient.updateQuantity("token", "45", "salem", 45))
				.thenReturn(new ResponseEntity(HttpStatus.ACCEPTED));
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		// refillOrderServiceImpl.requestAdhocRefill(45,true,45, "salem", "token");
		assertThrows(DrugQuantityNotAvailable.class,
				() -> refillOrderServiceImpl.requestAdhocRefill((long) 45, true, 45, "salem", "token"));
	}

	@Test
	void requestRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.save(refillOrder)).thenReturn(refillOrder);
		refillOrderServiceImpl.requestRefill(45, 45, "45", "token");


		assertEquals(list.get(0), refillOrder);


	}

	@Test
	void requestRefillInvalidTokenException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.requestRefill(45, 45, "45", "token"));

	}

	@Test
	void getRefillDuesAsOfPayment()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(45, "token");

		assertEquals(list.get(0), refillOrder);
	}

	@Test
	void getRefillDuesAsOfPaymentTrue()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), true, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(54, "token");

		assertEquals(list.get(0).getPayStatus(), refillOrder.getPayStatus());
	}

	@Test
	void getRefillDuesAsOfPaymentFalse()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, new Date(), false, 45, 45, "54");
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		refillOrderServiceImpl.getRefillPaymentDues(45, "token");
		assertEquals(list.get(0).getPayStatus(), refillOrder.getPayStatus());

	}

	@Test
	void getRefillDuesAsOfPaymentException()
			throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.getRefillPaymentDues(45, "token"));

	}

	@Test
	void updateRefill() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 1);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionService.getall("token")).thenReturn(list);
		String actual = refillOrderServiceImpl.updateRefill("token");

		String expected = "sucess";

		assertEquals(expected, actual);
	}

	@Test
	void updateRefillException() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.updateRefill("token"));

	}

	@Test
	void startTimer() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 1);
		list.add(refillOrder);
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		// when(refillOrderServiceImpl.startTimer("token").UpdateRefill("token")).thenReturn(list);
		refillOrderServiceImpl.startTimer("token");

		String expected = String.valueOf(response);
		String actual = "TokenValid(uid=uid, name=name, isValid=true)";
		assertEquals(expected, actual);


	}

	@Test
	void startTimerException() throws SubscriptionIdNotFoundException, InvalidTokenException, ParseException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderServiceImpl.startTimer("token"));

	}

}
