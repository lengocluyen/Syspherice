package net.syspherice.validator;

import java.util.Map;

import net.syspherice.form.SearchType;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SearchTypeValidator  implements Validator{
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return SearchType.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameDisplay","field.required", "(*)");
	}
}
