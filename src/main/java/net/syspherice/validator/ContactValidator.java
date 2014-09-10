package net.syspherice.validator;

import net.syspherice.form.Account;
import net.syspherice.form.Contact;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContactValidator implements Validator{

	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return Contact.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		Contact contact= (Contact) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName","field.required", "(*)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName","field.required", "(*)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","field.required", "(*)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message","field.required", "(*)");
	}

}
