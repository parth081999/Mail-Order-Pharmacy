package com.mailorderpharmacy.refill.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mailorderpharmacy.refill.entity.RefillOrder;
import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.exception.SubscriptionIdNotFoundException;

import feign.FeignException;

/** Interface which holds the methods of service class */
@Service
public interface RefillOrderService {

	/**
	 * @param subId
	 * @param token
	 * @return List<RefillOrder>
	 * @throws SubscriptionIdNotFoundException
	 * @throws InvalidTokenException
	 */
	public List<RefillOrder> getStatus(long subId, String token)
			throws SubscriptionIdNotFoundException, InvalidTokenException;

	/**
	 * @param memberId
	 * @param date
	 * @param token
	 * @return List<RefillOrderSubscription>
	 * @throws InvalidTokenException
	 */
	public List<RefillOrderSubscription> getRefillDuesAsOfDate(String memberId, int date, String token)
			throws InvalidTokenException;

	/**
	 * @param subId
	 * @param payStatus
	 * @param quantity
	 * @param location
	 * @param token
	 * @return RefillOrder
	 * @throws ParseException
	 * @throws FeignException
	 * @throws InvalidTokenException
	 * @throws DrugQuantityNotAvailable
	 */
	public RefillOrder requestAdhocRefill(Long subId, Boolean payStatus, int quantity, String location, String token)
			throws ParseException, FeignException, InvalidTokenException, DrugQuantityNotAvailable;

	/**
	 * @param subId
	 * @param quantity
	 * @param memberId
	 * @param token
	 * @return RefillOrder
	 * @throws ParseException
	 * @throws InvalidTokenException
	 */
	public RefillOrder requestRefill(long subId, int quantity, String memberId, String token)
			throws ParseException, InvalidTokenException;

	/**
	 * @param token
	 * @return String
	 * @throws InvalidTokenException
	 */
	public String updateRefill(String token) throws InvalidTokenException;

	/**
	 * @param token
	 * @throws InvalidTokenException
	 */
	public void startTimer(String token) throws InvalidTokenException;

	/**
	 * @param subId
	 * @param payStatus
	 * @param quantity
	 * @param location
	 * @param token
	 * @return boolean
	 * @throws ParseException
	 * @throws FeignException
	 * @throws InvalidTokenException
	 * @throws DrugQuantityNotAvailable
	 */
	public boolean getRefillPaymentDues(long subscriptionId, String token) throws InvalidTokenException;

}
