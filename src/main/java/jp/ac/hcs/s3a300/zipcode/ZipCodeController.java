package jp.ac.hcs.s3a300.zipcode;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ZipCodeController {

	@Autowired
	ZipCodeService zipCodeService;

	/**
	 * 郵便番号から住所を検索し、結果画面を表示する
	 * @param zipcode 検索する郵便番号(ハイフン無し)
	 * @param principal ログイン画面
	 * @param model モデル
	 * @return 結果画面 ー 郵便番号
	 */
	@PostMapping("/zip")
	public String getZipCode(@RequestParam("zipcode") String zipcode, @RequestParam(required=false, name="start") String start,
			Principal principal, Model model) {

		ZipCodeEntity zipCodeEntity = zipCodeService.getZip(zipcode);
		model.addAttribute("zipCodeEntity", zipCodeEntity);
		
		
		return "zipcode/zipcode";
	}
}
