# payrollmanagement
Payroll microservice implementation using Spring boot project + JPA to perform operations on Payroll table
1. POST - to insert new payroll,
   URL : http://localhost:8082/payrolls/payroll/
   Request Body :  {
 		"hoursWorked":10,
        "employeeId": 2,
        "deductions": 10,
        "allowances": 10,
        "ratePerHour": 10,
        "tax":50,
        "startDate": "2018-12-15",
        "endDate": "2019-01-01"
    }

2. GET - get all payrolls,
   URL : http://localhost:8082/payrolls/payroll
   
3. GET - get payroll by ID,
   URL : http://localhost:8082/payrolls/payroll/1
   
4. PUT - update an existing payroll.
     Long employeeId;
     Double hoursWorked;
     Double ratePerHour;
     Double deductions;
     Double allowances;
     Double netPay;
     Double grossPay;
     Double tax;
     Date startDate(yyyy-mm-dd);
     Date endDate(yyyy-mm-dd);

   URL : http://localhost:8082/payrolls/payroll/1
   Request Body : {
 		"hoursWorked":20,
        "tax":30
    }
