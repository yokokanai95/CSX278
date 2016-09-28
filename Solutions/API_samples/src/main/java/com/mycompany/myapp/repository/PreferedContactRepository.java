package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PreferedContact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PreferedContact entity.
 */
@SuppressWarnings("unused")
public interface PreferedContactRepository extends JpaRepository<PreferedContact,Long> {

}
