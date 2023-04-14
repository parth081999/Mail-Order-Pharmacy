package com.mailorderpharmacy.refill.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mailorderpharmacy.refill.dao.RefillOrderRepository;
import com.mailorderpharmacy.refill.entity.RefillOrder;
import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;
import com.mailorderpharmacy.refill.exception.DrugQuantityNotAvailable;
import com.mailorderpharmacy.refill.exception.InvalidTokenException;
import com.mailorderpharmacy.refill.exception.SubscriptionIdNotFoundException;
import com.mailorderpharmacy.refill.restclients.AuthFeign;
import com.mailorderpharmacy.refill.restclients.DrugDetailClient;
import com.mailorderpharmacy.refill.restclients.SubscriptionClient;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/** Service class which holds the business logic */
@Service
@Slf4j
public class RefillOrderServiceImpl implements RefillOrderService {

	@Autowired
	public RefillOrderRepository refillOrderRepository;

	@Autowired
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionService;

	@Autowired
	DrugDetailClient drugDetailClient;

	@Autowired
	SubscriptionClient subscriptionClient;

	@Autowired
	private AuthFeign authFeign;

	String msg = "Invalid Credentials";

	/**
	 * @param subId
	 * @param token
	 * @return List<RefillOrder>
	 * @throws SubscriptionIdNotFoundException
	 * @throws InvalidTokenException
	 */
	@Override
	public List<RefillOrder> getStatus(long subId, String token)
			throws SubscriptionIdNotFoundException, InvalidTokenException {
		// get refill status
		log.info("inside getStatus method");
		if (authFeign.getValidity(token).isValid()) {
			ArrayList<RefillOrder> list = new ArrayList<>();
			List<RefillOrder> finallist = null;
			try {
				list = (ArrayList<RefillOrder>) refillOrderRepository.findAll();
				finallist = list.stream().filter(p -> p.getSubId() == subId).collect(Collectors.toList());
				finallist.get(0);
			} catch (Exception ex) {
				throw new SubscriptionIdNotFoundException("Subscription ID is invalid");
			}
			return finallist;
		} else
			throw new InvalidTokenException(msg);

	}

	/**
	 * @param memberId
	 * @param date
	 * @param token
	 * @return List<RefillOrderSubscription>
	 * @throws InvalidTokenException
	 */
	@Override
	public List<RefillOrderSubscription> getRefillDuesAsOfDate(String memberId, int date, String token)
			throws InvalidTokenException {
		// get refill orders that are due for refill from a given date till current date
		log.info("inside getRefillDuesAsOfDate method");

		if (authFeign.getValidity(token).isValid()) {
			List<RefillOrderSubscription> list = refillOrderSubscriptionService.getall(token);

			List<RefillOrderSubscription> memberList = list.stream().filter(p -> p.getMemberId().equals(memberId))
					.collect(Collectors.toList());

			int userTime = date;

			/**List<RefillOrderSubscription> members = */
			return memberList.stream().filter(p -> userTime % p.getRefillTime() != 0)
					.collect(Collectors.toList());

			/** return members; */
		} else
			throw new InvalidTokenException(msg);

	}

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
	@Override
	public RefillOrder requestAdhocRefill(Long subId, Boolean payStatus, int quantity, String location, String token)
			throws ParseException, FeignException, InvalidTokenException, DrugQuantityNotAvailable {
		// request a on-the-go refill order
		log.info("inside requestAdhocRefill method");

		if (authFeign.getValidity(token).isValid()) {
			ResponseEntity<String> entityname = subscriptionClient.getDrugNameBySubscriptionId(subId, token);

			String name = entityname.getBody();
			log.info("drugname ");

			// change this qs mark to appropriate type
			ResponseEntity<?> responseEntity = drugDetailClient.updateQuantity(token, name, location, quantity);
			log.info("updated");
			int responsevalue = responseEntity.getStatusCodeValue();
			log.info("staus val");
			if (responsevalue == 200) {
				// checking drug availability then if yes
				RefillOrder refillOrder = new RefillOrder();
				refillOrder.setSubId(subId);
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm:ss");
				String str = format.format(date);
				refillOrder.setRefilledDate(format.parse(str));
				refillOrder.setQuantity(quantity);
				refillOrder.setPayStatus(payStatus);

				refillOrderRepository.save(refillOrder);
				log.info("refiloredr sabed");
				return refillOrder;
			} else {
				throw new DrugQuantityNotAvailable("DrugQuantityNotAvailable");

			}
		} else
			throw new InvalidTokenException(msg);
	}

	/**
	 * @param subId
	 * @param quantity
	 * @param memberId
	 * @param token
	 * @return RefillOrder
	 * @throws ParseException
	 * @throws InvalidTokenException
	 */
	@Override
	public RefillOrder requestRefill(long subId, int quantity, String memberId, String token)
			throws ParseException, InvalidTokenException {
		// request a refill order for given subscription id
		log.info("inside requestRefill method");

		if (authFeign.getValidity(token).isValid()) {
			RefillOrder refillOrder = new RefillOrder();
			refillOrder.setSubId(subId);
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String str = format.format(date);
			refillOrder.setRefilledDate(format.parse(str));
			refillOrder.setQuantity(quantity);
			refillOrder.setPayStatus(true);
			refillOrder.setMemberId(memberId);
			refillOrderRepository.save(refillOrder);

			return refillOrder;
		} else
			throw new InvalidTokenException(msg);
	}

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
	@Override
	public boolean getRefillPaymentDues(long subscriptionId, String token) throws InvalidTokenException {
		//check if there are any payment dues for a subscription
		log.info("inside getRefillDuesAsOfPayment method");

		if (authFeign.getValidity(token).isValid()) {
			List<RefillOrder> list = refillOrderRepository.findAll();

			List<RefillOrder> paymentDueList = list.stream().filter(p -> p.getSubId() == subscriptionId)
					.filter(p -> (!p.getPayStatus())).collect(Collectors.toList());

			if (paymentDueList.isEmpty()) {
				return true;
			}else {
				return false;
			}
		} else
			throw new InvalidTokenException(msg);

	}

	/**
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public String updateRefill(String token) throws InvalidTokenException {
		// fulfill refill orders for the day.
		// this method is invoked by the global timer
		log.info("inside UpdateRefill method");

		if (authFeign.getValidity(token).isValid()) {

			List<RefillOrderSubscription> list = refillOrderSubscriptionService.getall(token);

			try {
				list.stream().forEach(

						l -> {
							Calendar cal = Calendar.getInstance(); // this is the method you should use, not the Date(),
																	// because it is deprecated.
							int min = cal.get(Calendar.MINUTE);// get the hour number of the day, from 0 to 23
							if (min % l.getRefillTime() == 0) {

								try {
									requestRefill(l.getSubscriptionId(), l.getRefillQuantity(), l.getMemberId(), token);
								} catch (ParseException | InvalidTokenException e) {
									e.printStackTrace();
								}
							}
						}

				);
			}

			catch (Exception e) {
				log.info("Exception inside UpdateRefill:");
			}

			return "sucess";
		} else
			throw new InvalidTokenException(msg);
	}

	/**
	 * @param token
	 * @throws InvalidTokenException
	 */
	@Override
	public void startTimer(String token) throws InvalidTokenException {
		// Global timer function 
		log.info("inside startTimer method");

		if (authFeign.getValidity(token).isValid()) {
			Timer timer = new Timer();
			TimerTask tt = new TimerTask() {
				public void run() {
					try {
						updateRefill(token);
					} catch (Exception e) {
						log.info("Exception inside StartTimer:");
					}
				}
			};
			timer.schedule(tt, 1000, 1000 * 60); // delay the task 1 second, and then run task every five seconds
		} else
			throw new InvalidTokenException(msg);
	}

}
