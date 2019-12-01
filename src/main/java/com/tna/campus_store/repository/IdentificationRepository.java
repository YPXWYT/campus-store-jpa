package com.tna.campus_store.repository;

import com.tna.campus_store.beans.Identification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationRepository extends JpaRepository<Identification, Integer> {
}
