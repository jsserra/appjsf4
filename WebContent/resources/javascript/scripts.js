function passwordOK(form){
	var password1 = form[form.id + ":senha1"].value;
	var password2 = form[form.id + ":senha2"].value;
	
	if(password1 != password2){
		alert("Senha e Confirma Senha não são compatíveis");
	}else{
		return true;
	}
	return false;
}