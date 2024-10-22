package com.lms.repository.checkout;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Checkout;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;
import com.lms.model.enums.CheckoutStatus;

@NoRepositoryBean
public interface CheckoutRepositoryBasic extends GenericRepository<Checkout> {

	List<Checkout> findByCheckoutDate(LocalDateTime checkoutDate);
	List<Checkout> findByDueDate(LocalDate dueDate);
	List<Checkout> findByReturnDate(LocalDate returnDate);
	List<Checkout> findByStatus(CheckoutStatus status);

}
