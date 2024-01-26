package com.example.hotel.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.hotel.form.HotelForm;

@Component
public class HotelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO 自動生成されたメソッド・スタブ
		return HotelForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO 自動生成されたメソッド・スタブ
		HotelForm form = (HotelForm) target;
		System.out.println("validateが動いた");
		System.out.println(form);
		
		if(form.getCheck_in() != null && form.getCheck_out() != null) {
			System.out.println("nullチェックが動いた");
			if(!(form.getCheck_out().compareTo(form.getCheck_in()) > 0)) {
				System.out.println("日付比較が動いた");
				System.out.println(errors);
				errors.reject("com.example.hotel.validator.HotelValidator.message");
				System.out.println("終わり");
			}
		}
	}

}
