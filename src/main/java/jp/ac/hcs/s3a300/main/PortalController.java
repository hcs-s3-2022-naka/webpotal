package jp.ac.hcs.s3a300.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
