function zipcodeCheck(){
	
	var errorzip = document.getElementById("zip");
	var zipcode = document.getElementById("zipcode").value;
	
	
	if(zipcode.length > 7 || zipcode.length < 7) {
		swal("入力された郵便番号に間違いがあります。\r\n正しい郵便番号を入力してください。");
		return false;
	}
	
	if(zipcode.match(/[!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]/g)) {
		swal("特殊文字は入力できません。\r\n正しい郵便番号を入力してください。");
		return false;
	}
	return true;
}