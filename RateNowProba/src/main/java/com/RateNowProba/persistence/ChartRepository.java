package com.RateNowProba.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.RateNowProba.model.Chart;


@Component
@Repository
public interface ChartRepository extends CrudRepository<Chart, Long> {
	
	    List<Chart> findByLastName(String lastName);

		Chart findById(long id);
	


}
