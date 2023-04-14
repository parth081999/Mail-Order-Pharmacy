package com.mailorderpharmacy.refill.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mailorderpharmacy.refill.entity.RefillOrderSubscription;

/**JPA Repository interacts with database*/
@Repository
@Transactional
public interface RefillOrderSubscriptionRepository extends JpaRepository<RefillOrderSubscription, Long> {
	
	/**
	 * @param subscriptionId
	 */
	@Modifying
	@Query("delete from RefillOrderSubscription where subscriptionId=?1")
	public int deleteBySubscriptionId(long subscriptionId);

}
