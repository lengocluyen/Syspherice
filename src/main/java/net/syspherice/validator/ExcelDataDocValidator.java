package net.syspherice.validator;

import net.syspherice.form.ExcelDataDoc;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ExcelDataDocValidator implements Validator{

	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return ExcelDataDoc.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ExcelDataDoc excel = (ExcelDataDoc) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fileUrl","field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectname","field.required", "Required field");
	}

}
