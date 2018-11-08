package com.spm.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spm.erp.model.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

}
