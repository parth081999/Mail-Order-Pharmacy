package com.mailorderpharmacy.refill.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**Model class for the business details*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefillOrder {
	
	/**
	 * Refill id
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	/**
	 * Refill date 
	 */
	@JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
	Date refilledDate;
	/**
	 * Pay status
	 */
	private Boolean payStatus;
	/**
	 * Subscription id
	 */
	private long subId;
	/**
	 * refill Quantity
	 */
	int quantity;
	/**
	 * Member id
	 */
	String memberId;

}
