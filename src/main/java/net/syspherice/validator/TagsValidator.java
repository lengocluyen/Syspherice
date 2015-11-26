package net.syspherice.validator;

import net.syspherice.form.Tags;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TagsValidator  implements Validator{
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return Tags.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","field.required", "(*)");
	}
}
