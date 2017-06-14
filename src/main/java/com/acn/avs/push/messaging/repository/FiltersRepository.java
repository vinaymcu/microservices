package com.acn.avs.push.messaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acn.avs.push.messaging.entity.Filters;

public interface FiltersRepository extends JpaRepository<Filters, Long> {

	@Modifying
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("DELETE FROM Filters f WHERE f.filterId =:filterId")
	void deleteFilters(@Param("filterId") Long filterId);
}
