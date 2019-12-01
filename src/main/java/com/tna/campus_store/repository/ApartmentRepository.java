package com.tna.campus_store.repository;

import com.tna.campus_store.beans.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
}
