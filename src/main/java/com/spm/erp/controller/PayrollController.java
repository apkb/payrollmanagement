package com.spm.erp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spm.erp.model.CustomResponse;
import com.spm.erp.model.Payroll;
import com.spm.erp.security.JwtAuthenticationResponse;
import com.spm.erp.service.PayrollService;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

	@Autowired
	PayrollService payrollService;

	@GetMapping("/payroll")
	public ResponseEntity<?> getAllPayrolls() {
		return ResponseEntity.ok(payrollService.fetchAllPayrolls());
	}
	
	@GetMapping("/payroll/{payroll_id}")
	public ResponseEntity<?> getPayrollById(@PathVariable("payroll_id") Long id) {
		Optional<Payroll> payroll = payrollService.fetchPayrollById(id);
		return ResponseEntity.ok(payroll.isPresent()? payroll.get() : new CustomResponse(Boolean.FALSE,"No Payroll found with id:"+id));
	}
	
	@PostMapping("/payroll")
	public ResponseEntity<?> insertPayroll(@Valid @RequestBody Payroll payroll) {
		boolean isSaved = payrollService.insertPayroll(payroll);
		CustomResponse response;
		if(isSaved) {
			response = new CustomResponse(Boolean.TRUE, "Payroll saved successfully.");
		} 
		else {
			response = new CustomResponse(Boolean.FALSE, "Payroll not saved.");
		}
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/payroll/{payroll_id}")
	public ResponseEntity<?> updatePayroll(@PathVariable("payroll_id") Long id,@RequestBody Payroll payroll) {
		boolean isUpdated = payrollService.updatePayroll(payroll,id);
		CustomResponse response;
		if(isUpdated) {
			response = new CustomResponse(Boolean.TRUE, "Payroll updated successfully.");
		} 
		else {
			response = new CustomResponse(Boolean.FALSE, "No Payroll found with id:"+id);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/payroll/{payroll_id}")
	public ResponseEntity<?> deletePayroll(@PathVariable("payroll_id") Long id) {
		Optional<Payroll> payroll = payrollService.fetchPayrollById(id);
		if(!payroll.isPresent()) {
			return ResponseEntity.ok(new CustomResponse(Boolean.FALSE,"No Payroll found with id:"+id));
		}
		payrollService.deletePayroll(id);
		return ResponseEntity.ok(new CustomResponse(Boolean.TRUE,"Payroll deleted successfully."));
	}
}
