package com.twx.smalltools;

import com.twx.smalltools.tool.CreditCard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class DemoController {

    @PostMapping("/cal")
    @ResponseBody
    public Map<String,Object> cal(@RequestParam("amount") Double amount,
                                  @RequestParam("number") Integer number,
                                  @RequestParam("principal") Double principal) {
        return CreditCard.calRate(amount, number, principal);
    }
}
