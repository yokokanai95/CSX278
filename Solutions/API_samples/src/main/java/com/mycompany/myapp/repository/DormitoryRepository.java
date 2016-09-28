package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dormitory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dormitory entity.
 */
@SuppressWarnings("unused")
public interface DormitoryRepository extends JpaRepository<Dormitory,Long> {

}
