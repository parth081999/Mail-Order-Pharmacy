package com.mailorderpharmacy.refill.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;

class RefillOrderSubscriptionTest {
	

	Date date = new Date();
	RefillOrderSubscription refillOrderSubscription = new RefillOrderSubscription();
	RefillOrderSubscription refillOrderSubscription2 = new RefillOrderSubscription(1,2,"k54",4,5);
	
	@Test
	void testRefillId() {
		refillOrderSubscription.setId(1);
		assertEquals(1, refillOrderSubscription.getId());
	}



	@Test
	void testsubscriptionId() {
		refillOrderSubscription.setSubscriptionId(1);
		assertEquals(1, refillOrderSubscription.getSubscriptionId());
	}

	@Test
	void testRefillQuantity() {
		refillOrderSubscription.setRefillQuantity(45);
		assertEquals(45, refillOrderSubscription.getRefillQuantity());
	}

	@Test
	void testMemberId() {
		refillOrderSubscription.setMemberId("k5");
		assertEquals("k5", refillOrderSubscription.getMemberId());
	}


	@Test
	void testRefillTime() {
		refillOrderSubscription.setRefillTime(45);
		assertEquals(45, refillOrderSubscription.getRefillTime());
	}

}
