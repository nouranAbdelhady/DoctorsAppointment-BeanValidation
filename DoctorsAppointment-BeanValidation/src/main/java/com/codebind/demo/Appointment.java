package com.codebind.demo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Set;  

public class Appointment 
{
	@Size(min = 10, max = 100, message= "Patient's name must be between 10 and 100 characters")
	private String patientName;
	
	@Min(value = 0, message = "Age should be a positive number!")
    @Max(value = 150, message = "Age should not be greater than 150")
	private int patientAge;
	
	@Pattern(regexp = "(?=.*[0-9]).+", message = "Must contain building number")		//at least 1 digit
    @Pattern(regexp = "(?=.*[A-z]).+", message = "Must contain street name")		//at least 1 character
    private String patientAddress;
	
	@Email(message = "Email should be valid")
	private String patientEmail;
	
	@Size(min = 10, max = 100, message= "Doctor's name must be between 10 and 100 characters")
	String doctorName;
	
	@FutureOrPresent(message= "Must be a date in the present or in the future")
	private Date appDate;
	
	public void setPatientName(String patientName) {
		this.patientName=patientName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName=doctorName;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge=patientAge;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail=patientEmail;
	}
	public void setAppDate(Date appDate) {
		this.appDate=appDate;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress=patientAddress;
	}
		
    public static void main( String[] args ) throws Exception
    {
    	
    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    	javax.validation.Validator validator = factory.getValidator();
    	
        Appointment appointment = new Appointment();
        appointment.setPatientName("Nouran Ahmed");
        
        //incorrect
        appointment.setDoctorName("Ahmed");		
        appointment.setPatientAge(-6);		
        appointment.setPatientEmail("Nouran.com");		
        
        String dateInString = "10/Jan/2020";	
        Date dateParam = new SimpleDateFormat("dd/MMM/yyyy").parse(dateInString); 
        appointment.setAppDate( dateParam );
        
        //missing street name
        appointment.setPatientAddress( "56" );
        
        //missing building number
        //appointment.setPatientAddress( "maadi" );
        
        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);
        for (ConstraintViolation<Appointment> violation : violations) {
        	System.out.println(violation.getMessage()); 
        }

    }
}
