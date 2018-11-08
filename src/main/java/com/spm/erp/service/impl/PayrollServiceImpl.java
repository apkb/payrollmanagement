package com.spm.erp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.erp.exception.AppException;
import com.spm.erp.model.Payroll;
import com.spm.erp.repository.PayrollRepository;
import com.spm.erp.service.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	PayrollRepository payrollRepo;

	@Override
	public List<Payroll> fetchAllPayrolls() {
		return payrollRepo.findAll();
	}

	@Override
	public Optional<Payroll> fetchPayrollById(Long id) {
		return payrollRepo.findById(id);
	}

	@Override
	public boolean insertPayroll(Payroll payroll) {
		try {
			payroll.setGrossPay(computeGrossPay(payroll));
			payroll.setNetPay(computeNetPay(payroll));
			payrollRepo.save(payroll);
			return true;
		} catch (Exception ex) {
			throw new AppException("Something went wrong. Payroll not saved.");
		}
	}

	@Override
	public boolean updatePayroll(Payroll payroll, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deletePayroll(Long id) {
		try {
			payrollRepo.deleteById(id);
		} catch (Exception ex) {
			throw new AppException("Something went wrong. Payroll not deleted.");
		}
	}

	public double computeGrossPay(Payroll payroll) {
		return (payroll.getHoursWorked() * payroll.getRatePerHour()) + payroll.getAllowances()
				- payroll.getDeductions();
	}
	
	public double computeNetPay(Payroll payroll) {
		return payroll.getGrossPay()-payroll.getTax();
	}
}