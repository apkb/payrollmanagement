package com.spm.erp.service;

import java.util.List;
import java.util.Optional;

import com.spm.erp.model.Payroll;

public interface PayrollService {

	List<Payroll> fetchAllPayrolls();
	
	Optional<Payroll> fetchPayrollById(Long id);
	
	boolean insertPayroll(Payroll payroll);
	
	boolean updatePayroll(Payroll payroll, Long id);
	
	void deletePayroll(Long id);
	
}
