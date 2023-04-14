package com.mailorderpharmacy.refill.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mailorderpharmacy.refill.entity.RefillOrder;

class RefillOrderTest {

	Date date = new Date();
	RefillOrder refillOrder = new RefillOrder();
	RefillOrder refillOrder2 = new RefillOrder(1, date, true, 45, 4, "5");

	@Test
	void testRefillId() {
		refillOrder.setId(1);
		assertEquals(1, refillOrder.getId());
	}

	@Test
	void testrefilledDate() {
		refillOrder.setRefilledDate(date);
		assertEquals(date, refillOrder.getRefilledDate());
	}

	@Test
	void testPayStatus() {
		refillOrder.setPayStatus(true);
		assertEquals(true, refillOrder.getPayStatus());
	}

	@Test
	void testSubId() {
		refillOrder.setSubId(1);
		assertEquals(1, refillOrder.getSubId());
	}

	@Test
	void testRefillQuantity() {
		refillOrder.setQuantity(45);
		assertEquals(45, refillOrder.getQuantity());
	}

	@Test
	void testMemberId() {
		refillOrder.setMemberId("k5");
		assertEquals("k5", refillOrder.getMemberId());
	}

}
