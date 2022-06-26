function isValid(){

	if(document.getElementById("title") == null){
		alert('タスク名は必須項目です。入力してください。');
		return false;
	}
	var title = document.getElementById("title").value;

	if(!document.getElementById("comment")) {
		alert('コメントは必須項目です。入力してください。');
		return false;
	}
	var comment = document.getElementById("comment").value;

	if(!document.getElementById("priority")) {
		alert('優先度は必須項目です。入力してください。');
		return false;
	}
	var priority = document.getElementById("priority").value;

	if(!document.getElementById("limitday")) {
		alert('期限日は必須項目です。入力してください。');
		return false;
	}
	var limitday = document.getElementById("limitday").value;

	if(!title) {
		alert('タスク名は必須項目です。入力してください。');
		return false;
	}

	if(!comment) {
		alert('コメントは必須項目です。入力してください。');
		return false;
	}

	if(title.length > 50) {
		alert('50文字以上のタスク名は入力できません。再入力してください。');
		return false;
	}

	if(comment.length > 200) {
		alert('200文字以上のタスクは入力できません。再入力してください。');
		return false;
	}

	if(!priority.match(/^[1-3]$/m)) {
		alert('大・中・小以外の優先度は設定できません。再入力してください。');
		return false;
	}

    // 年/月/日の形式のみ許容する
    if(!limitday.match(/^\d{4}\-\d{1,2}\-\d{1,2}$/m)){
    	alert('日付の形式が不正です。再入力してください。');
        return false;
    }

    var now = new Date();
    var specifiedDate = new Date(limitday);
    //インスタンス生成時の時刻よりも先になるように設定
    specifiedDate.setHours(23, 59, 59);
    if (now.getTime() > specifiedDate.getTime()) {
        alert("過去の日付が指定されています。再入力してください。");
        return false;
    } else {
    	if((specifiedDate.getFullYear() - now.getFullYear()) > 99) {
    		alert("日付が不適切です。再入力してください。");
    		return false;
    	}
    }


    return true;
}