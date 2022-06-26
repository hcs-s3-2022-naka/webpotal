function isValidForGourmet(){

	if(document.getElementById("shopname") == null){
		swal('店舗名は必須項目です。入力してください。');
		return false;
	}
	var title = document.getElementById("shopname").value;

	if(title.length == "") {
		swal('店舗名は必須項目です。入力してください。');
		return false;
	}

	if(title.length > 50) {
		swal('50文字を超える入力はできません。再入力してください。');
		return false;
	}

	if(title.match(/[!"#$%&'()\*\+\-\.,\/:;<=>?@\[\\\]^_`{|}~]/g)) {
		swal('記号や特殊文字は入力できません。再入力してください。');
		return false;
	}

	return true;
}