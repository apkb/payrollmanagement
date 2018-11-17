package com.spm.erp.service;

import java.util.List;
import java.util.Optional;

import com.spm.erp.model.Payroll;

import net.sf.jasperreports.engine.JasperPrint;

public interface PayrollService {

	List<Payroll> fetchAllPayrolls();
	
	Optional<Payroll> fetchPayrollById(Integer id);
	
	boolean insertPayroll(Payroll payroll);
	
	boolean updatePayroll(Payroll payroll, Integer id);
	
	void deletePayroll(Integer id);
	
	byte[] generatePayrollReport(Payroll payroll);
	
}
