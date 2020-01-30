package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.oauth.controller *
 * @since 1.0
 */
@Controller
@RequestMapping("/oauth")
public class LoginRedirectController {

    @RequestMapping("/login")
    public String login(String From, Model model) {
        model.addAttribute("from",From);
        return "login";
    }


}
