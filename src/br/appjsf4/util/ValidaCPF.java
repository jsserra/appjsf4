package br.appjsf4.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@SuppressWarnings("rawtypes")
@FacesValidator
public class ValidaCPF implements Validator {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) throws ValidatorException {
		String cpf = (String) object;
		if(!validaCPF(cpf)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "O CPF n�o � v�lido!", "");
			throw new ValidatorException(message);
		}
		
	}

	private boolean validaCPF(String cpf) {
		cpf = cpf.replace('.',' ').replace('-',' ');
		cpf = cpf.toString().replaceAll(" ","").trim();
		
		if(cpf.equals("00000000000")||cpf.equals("11111111111")||cpf.equals("33333333333")||cpf.equals("44444444444")
				||cpf.equals("55555555555")||cpf.equals("66666666666")||cpf.equals("77777777777")||cpf.equals("88888888888")
				||cpf.equals("99999999999")||cpf.length()!=11)
			return (false);
		
		char dig10, dig11;
		int sm, i, r, num, peso;
		
		try {
			//C�lculo do 1� d�gito verificador
			sm = 0;
			peso = 10;
			for(i=0; i<=9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if((r == 10) || (r ==11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);
			
			//C�lculo do 2� d�gito verificador
			sm = 0;
			peso = 11;
			for(i=0; i<=10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if((r == 10) || (r ==11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);
			
			if((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return true;
			else
				return false;
		}catch(Exception e) {
			return (false);
		}
	}

}
