package net.syspherice.validator;

import net.syspherice.form.Account;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AccountValidator implements Validator{

	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return Account.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		Account account = (Account) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName","field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName","field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username","field.required", "Required field");
		
	}
}
