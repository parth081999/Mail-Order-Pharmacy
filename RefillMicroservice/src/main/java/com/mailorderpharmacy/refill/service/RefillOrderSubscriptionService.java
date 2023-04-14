package com.mailorderpharmacy.refill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;

/** Interface which holds the methods of service class */
@Service
public interface RefillOrderSubscriptionService {

	/**
	 * @param subId
	 * @param memberId
	 * @param quantity
	 * @param time
	 * @param token 
	 * @return
	 * @throws InvalidTokenException
	 */
	public RefillOrderSubscription updateRefillOrderSubscription(long subId, String memberId, int quantity, int time, String token) throws InvalidTokenException;

	/**
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	public List<RefillOrderSubscription> getall(String token) throws InvalidTokenException;

	/**
	 * @param subscriptionId
	 * @param token
	 * @throws InvalidTokenException
	 */
	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException;

}
