package com.spm.erp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "payroll")
public class Payroll {	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@NotNull
    private Integer employeeId;
	
    @NotNull
    private Double hoursWorked;

    @NotNull
    private Double ratePerHour;

    @NotNull
    private Double deductions;
    
    @NotNull
    private Double allowances;

    private Double netPay;
    
    private Double grossPay;
    
    @NotNull
    private Double tax;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @NotNull 
    @Temporal(TemporalType.DATE)
    private Date endDate;

	public Payroll(@NotNull Integer employeeId, @NotNull Double hoursWorked, @NotNull Double ratePerHour,
			@NotNull Double deductions, @NotNull Double allowances, @NotNull Double netPay, @NotNull Double grossPay,
			@NotNull Double tax, @NotNull Date startDate, @NotNull Date endDate) {
		this.employeeId = employeeId;
		this.hoursWorked = hoursWorked;
		this.ratePerHour = ratePerHour;
		this.deductions = deductions;
		this.allowances = allowances;
		this.netPay = netPay;
		this.grossPay = grossPay;
		this.tax = tax;
		this.startDate = startDate;
		this.endDate = endDate;
	}
    
}
