package com.mailorderpharmacy.refill.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Model class for the business details*/
@Entity(name = "RefillOrderSubscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RefillOrderSubscription")
public class RefillOrderSubscription {
	
	/**
	 * Refill id
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	/**
	 * Subscription id
	 */
	long subscriptionId;
	/**
	 * Member id
	 */
	String memberId;
	/**
	 * Quantity to refill
	 */
	int refillQuantity;
	/**
	 * Time for refill
	 */
	int refillTime;
	
	

}
