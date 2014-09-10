package net.syspherice.validator;

import net.syspherice.form.Login;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class LoginValidator implements Validator{

	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return Login.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username","field.required", "(*)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","field.required", "(*)");
	}

}
