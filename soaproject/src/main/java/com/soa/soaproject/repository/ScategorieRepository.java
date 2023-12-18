package com.soa.soaproject.repository;

import com.soa.soaproject.model.Scategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScategorieRepository extends JpaRepository<Scategorie, Long> {


}
