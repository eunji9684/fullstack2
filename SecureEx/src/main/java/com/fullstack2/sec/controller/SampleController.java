package com.fullstack2.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
	
	@GetMapping("/all")
	public void exAll() {
		log.info("/all 요청됨");
	}
	@GetMapping("/member")
	public void exMember() {
		log.info("/member 요청됨");
	}
	@GetMapping("/admin")
	public void exAdmin() {
		log.info("/admin 요청됨");
	}

}
