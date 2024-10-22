package com.lms.repository.fine;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Fine;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface FineRepositoryBasic extends GenericRepository<Fine> {

	List<Fine> findByFineAmount(Double fineAmount);
	List<Fine> findByPaid(Boolean paid);
	List<Fine> findByFineDate(LocalDate fineDate);

}
