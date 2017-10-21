package com.codegans.demo.jbpm.repository;

import com.codegans.demo.jbpm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 17:08
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
