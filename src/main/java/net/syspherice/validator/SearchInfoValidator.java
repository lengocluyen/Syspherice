package net.syspherice.validator;

import net.syspherice.form.SearchInfo;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SearchInfoValidator implements Validator{
	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return SearchInfo.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchContent","field.required", "(*)");
	}
}
