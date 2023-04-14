package com.mailorderpharmacy.refill.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mailorderpharmacy.refill.dao.RefillOrderSubscriptionRepository;
import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.entity.TokenValid;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.restclients.AuthFeign;
import com.mailorderpharmacy.refill.service.RefillOrderSubscriptionServiceImpl;

@SpringBootTest(classes = RefillOrderSubscriptionServiceImplTest.class)
class RefillOrderSubscriptionServiceImplTest {

	@InjectMocks
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionServiceImpl;

	@Mock
	RefillOrderSubscriptionRepository refillOrderSubscriptionRepository;

	@Mock
	AuthFeign authFeign;

	@Test
	void updateRefillOrderSubscriptionTest() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		RefillOrderSubscription ros = refillOrderSubscriptionServiceImpl.updateRefillOrderSubscription(45, "memberId", 45, 4, "token");
		long actual = ros.getSubscriptionId();
		long expected = 45;
		assertEquals(expected, actual);

	}

	@Test
	void updateRefillOrderSubscriptionTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class,
				() -> refillOrderSubscriptionServiceImpl.updateRefillOrderSubscription(45, "memberId", 45, 4, "token"));

	}

	@Test
	void getallTest() throws InvalidTokenException {
		ArrayList<RefillOrderSubscription> list = new ArrayList<>();
		RefillOrderSubscription refillOrder = new RefillOrderSubscription(1, 2, "true", 45, 46);
		list.add(refillOrder);

		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderSubscriptionRepository.findAll()).thenReturn(list);
		List<RefillOrderSubscription> l = refillOrderSubscriptionServiceImpl.getall("token");

		assertEquals(list.get(0).getId(), l.get(0).getId());

	}

	@Test
	void getallTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionServiceImpl.getall("token"));

	}
	
	@Test
	void deleteBySubscriptionIdTest() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", true);
		when(authFeign.getValidity("token")).thenReturn(response);
		refillOrderSubscriptionServiceImpl.deleteBySubscriptionId(45, "token");

		String expected = String.valueOf(response);
		String actual = "TokenValid(uid=uid, name=name, isValid=true)";
		assertEquals(expected, actual);


	}
	@Test
	void deleteBySubscriptionIdTestFalse() throws InvalidTokenException {
		TokenValid response = new TokenValid("uid", "name", false);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionServiceImpl.deleteBySubscriptionId(45, "token"));

	}
	
}
