package net.syspherice.validator;

import net.syspherice.form.FolderImage;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FolderImageValidator  implements Validator{

	public boolean supports(Class aClass) {
		// TODO Auto-generated method stub
		return FolderImage.class.equals(aClass);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectname","field.required", "(*)");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url","field.required", "(*)");
	}

}
