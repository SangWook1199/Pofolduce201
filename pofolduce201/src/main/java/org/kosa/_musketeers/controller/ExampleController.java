package org.kosa._musketeers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {
	
	@GetMapping("/")
	public String example() {
		return "index";
	}
}
