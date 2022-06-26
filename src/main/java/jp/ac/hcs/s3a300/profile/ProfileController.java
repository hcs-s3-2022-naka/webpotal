package jp.ac.hcs.s3a300.profile;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	/**  取得資格チェックボックス用変数 */
	private Map<String,String> qualification;
	
	private Map<String,String> getCheckQualification() {
		Map<String,String> checkQualification = new LinkedHashMap<String, String>();
		checkQualification.put("ITパスポート", "ITパスポート");
		checkQualification.put("基本情報技術者", "基本情報技術者");
		checkQualification.put("情報セキュリティマネジメント", "情報セキュリティマネジメント");
		checkQualification.put("応用情報技術者", "応用情報技術者");
		checkQualification.put("情報処理安全確保支援士", "情報処理安全確保支援士");
		checkQualification.put("データベーススペシャリスト", "データベーススペシャリスト");
		checkQualification.put("ネットワークスペシャリスト", "ネットワークスペシャリスト");
		checkQualification.put("情報検定1級", "情報検定1級");
		checkQualification.put("情報検定2級", "情報検定2級");
		checkQualification.put("情報検定3級", "情報検定3級");
		checkQualification.put("情報検定プログラマ認定", "情報検定プログラマ認定");
		checkQualification.put("情報検定システムエンジニア認定", "情報検定システムエンジニア認定");
		checkQualification.put("Python3基礎認定", "Python3基礎認定");
		checkQualification.put("SEA/J", "SEA/J");
		checkQualification.put("LPIC", "LPIC");
		return checkQualification;
	}
	
	@GetMapping("/change")
	public String getUserProfile(@ModelAttribute ProfileForm form,Principal principal, Model model) {
		
		qualification = getCheckQualification();
		model.addAttribute("qualification",qualification);
		
		ProfileData data = profileService.selectOne(principal.getName());

		form.setUser_id(data.getUser_id());
		form.setUser_name(data.getUser_name());
		form.setQualification(data.getQualification());
		form.setNickname(data.getNickname());
		form.setComment(data.getComment());
		model.addAttribute("userProfile", form);
		model.addAttribute("nickname", data.getNickname());

		
		return "profile/profile";
	}
	
	@GetMapping("/profile")
	public String getProfile(@ModelAttribute ProfileForm form,Principal principal, Model model) {

		qualification = getCheckQualification();
		model.addAttribute("qualification",qualification);
		
		ProfileData data = profileService.selectOne(principal.getName());
		form.setUser_id(data.getUser_id());
		form.setUser_name(data.getUser_name());
		form.setQualification(data.getQualification());
		form.setNickname(data.getNickname());
		form.setComment(data.getComment());
		model.addAttribute("userProfile", form);
		return "profile/userProfile";
	}
	
	@PostMapping("/userProfile")
	public String postUserProfile(@ModelAttribute @Validated ProfileForm profile,
			BindingResult bindingResult,
			Principal principal, Model model) {
		
		if (bindingResult.hasErrors()) {
			return getUserProfile(profile, principal, model);
		}

		log.info("[" + principal.getName() + "]ユーザプロフィール更新:" + profile.toString());
		System.out.println(profile.getQualification());
		ProfileData data = new ProfileData();
		data.setUser_id(profile.getUser_id());
		data.setQualification(profile.getQualification());
		data.setNickname(profile.getNickname());
		data.setComment(profile.getComment());
		
		boolean result = profileService.updateProfile(data);
		
		if (result) {
			log.info("[" + principal.getName() + "]ユーザプロフィール更新成功");
			model.addAttribute("result", "ユーザプロフィール更新成功");
		} else {
			log.warn("[" + principal.getName() + "]ユーザプロフィール更新失敗");
			model.addAttribute("result", "ユーザプロフィール更新失敗");
		}

		return "redirect:/change";
	} 
}
