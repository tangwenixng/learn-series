package com.twx.smalltools.tool;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditCard {
    /**
     * @param amount       分期金额
     * @param number       期数
     * @param moneyOfMonth 每期还多少
     */
    public static Map<String, Object> calRate(double amount, int number, double moneyOfMonth) {
        //, double interestOfMonth
        double principal = amount / number;
        double interestOfMonth = moneyOfMonth - principal;

        Map<String, Object> result = new HashMap<>();
        double totalUsingAmount = 0;
        List<String> details = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            double usingAmount = amount - (principal * i);
            double monthInterest = interestOfMonth / usingAmount * 100;
            String order = "";
            if (i < 9) {
                order += "0" + (i + 1);
            } else {
                order = String.valueOf(i + 1);
            }
            details.add(StrUtil.format("第{}期 使用本金:{} 月利率:{}% 年利率:{}%", order,
                    String.format("%.2f", usingAmount),
                    String.format("%.2f", monthInterest),
                    String.format("%.2f", monthInterest * 12)));
            totalUsingAmount += usingAmount;
        }
        result.put("details", details);
        //评价使用本金
        double avgUsingAmount = totalUsingAmount / number;
        //总利息
        double totalInterest = interestOfMonth * number;
        //真实利率
        double realRate = (totalInterest / avgUsingAmount) * 100 / (number / 12.0);
        //总还款金额
        double totalAmount = moneyOfMonth * number;
        result.put("realRate", StrUtil.format("还款总金额:{} 总利息:{} 真实利率:{}%",
                String.format("%.2f", totalAmount),
                String.format("%.2f", totalInterest),
                String.format("%.2f", realRate)));
        return result;
    }

}
