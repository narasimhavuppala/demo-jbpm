package com.codegans.demo.jbpm.repository;

import com.codegans.demo.jbpm.model.BusinessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 17:08
 */
@Repository
public interface BusinessTransactionRepository extends JpaRepository<BusinessTransaction, UUID> {
    @Query("from BusinessTransaction where treated = false")
    List<BusinessTransaction> findAllNotTreated();
}
