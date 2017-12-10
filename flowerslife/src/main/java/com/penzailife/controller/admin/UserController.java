package com.penzailife.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penzailife.dao.model.TbUser;

@Controller
@RequestMapping("/admin")
public class UserController {

	@RequestMapping("/user/{userId}")
	public ModelAndView showUserInfo(@PathVariable int userId) {
		ModelAndView model = new ModelAndView("/user/showInfo");
		return model;
	}

	@RequestMapping("/showInfos")
	public @ResponseBody Object showUserInfos() {
		return new TbUser();
	}
}
