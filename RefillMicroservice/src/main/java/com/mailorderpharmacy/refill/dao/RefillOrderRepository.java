package com.mailorderpharmacy.refill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mailorderpharmacy.refill.entity.RefillOrder;

/**JPA Repository interacts with database*/
@Repository
public interface RefillOrderRepository extends JpaRepository<RefillOrder,Integer> {

}
