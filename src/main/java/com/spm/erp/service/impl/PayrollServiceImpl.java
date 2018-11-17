package com.spm.erp.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.erp.exception.AppException;
import com.spm.erp.model.Payroll;
import com.spm.erp.repository.PayrollRepository;
import com.spm.erp.service.PayrollService;
import com.spm.erp.util.BeanUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	PayrollRepository payrollRepo;

	@Override
	public List<Payroll> fetchAllPayrolls() {
		return payrollRepo.findAll();
	}

	@Override
	public Optional<Payroll> fetchPayrollById(Integer id) {
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
	public boolean updatePayroll(Payroll payroll, Integer id) {
		Optional<Payroll> payrollOptional = payrollRepo.findById(id);
		if (!payrollOptional.isPresent())
			return false;
		Payroll payrollOriginal = payrollOptional.get();
		payrollOriginal = BeanUtil.<Payroll>copyNonNullProperties(payrollOriginal, payroll);
		payrollOriginal.setGrossPay(computeGrossPay(payrollOriginal));
		payrollOriginal.setNetPay(computeNetPay(payrollOriginal));
		try {
			payrollRepo.save(payrollOriginal);
			return true;
		} catch (Exception ex) {
			throw new AppException("Something went wrong.Payroll not updated.");
		}
	}

	@Override
	public void deletePayroll(Integer id) {
		try {
			payrollRepo.deleteById(id);
		} catch (Exception ex) {
			throw new AppException("Something went wrong. Payroll not deleted.");
		}
	}

	@Override
	public byte[] generatePayrollReport(Payroll payroll) {
		byte[] bytes = null;
		Map<String, Object> param = new HashMap<>();
		List<Payroll> tableData = new ArrayList<>();
		try {
			InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("Payroll.jrxml");
			JasperDesign design = JRXmlLoader.load(jasperStream);
			tableData.add(payroll);

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(tableData);

			param.put("datasource", jrDataSource);

			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, jrDataSource);
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public double computeGrossPay(Payroll payroll) {
		return (payroll.getHoursWorked() * payroll.getRatePerHour()) + payroll.getAllowances()
				- payroll.getDeductions();
	}

	public double computeNetPay(Payroll payroll) {
		return payroll.getGrossPay() - payroll.getTax();
	}
}
