package com.tna.campus_store.repository;

import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Integer> {

}
