function citycodeCheck(){
	
	var city = document.getElementById("citycode").value;
	
	if(city.length > 6 || city.length < 6) {
		swal("正しい文字コードが入力されていません。\r\n改ざんされている可能性があります。");
		return false;
	}
	
	if(city.match(/[!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]/g)) {
		swal(city);
		swal("特殊文字は入力できません。正しい文字コードを入力してください。");
		return false;
	}
	
	return true;
}