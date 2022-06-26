package jp.ac.hcs.s3a300.weather;

import java.security.Principal;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@PostMapping("/weather")
	public String postWeather(@RequestParam("citycode")String citycode, @RequestParam(required=false, name="start") String start, Principal principal, Model model) {
		
		if(citycode.equals("")) {
			log.info("[" + principal.getName() + "]天気予報検索:" + citycode + "[ 都市名不適切：空文字 ]");
			return "redirect:/";
		}

		Pattern pa = Pattern.compile("[a-zA-Z]");
		if(pa.matcher(citycode).find()) {
			log.info("[" + principal.getName() + "]天気予報検索:" + citycode + "[ 都市名不適切：数字以外の文字 ]");
			return "redirect:/";
		}
		Pattern p = Pattern.compile("[!\"#$%&'()\\*\\+\\-\\.,\\/:;<=>?@\\[\\\\\\]^_`{|}~]");
		if(p.matcher(citycode).find()) {
			log.info("[" + principal.getName() + "]天気予報検索:" + citycode + "[ 都市名不適切：記号、特殊文字 ]");
			return "redirect:/";
		}

		WeatherEntity weatherEntity = weatherService.getWeather(citycode);
		model.addAttribute("weatherEntity", weatherEntity);

		return "weather/weather";
	}
}
