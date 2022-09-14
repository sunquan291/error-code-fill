package com.zte.sdn.oscp.errcode.config;

import com.zte.sdn.oscp.errcode.condition.CheckErrorCodeCondition;
import com.zte.sdn.oscp.errcode.filter.ErrorCodeMsgFillFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrCodeAutoConfigure {

    @Conditional(CheckErrorCodeCondition.class)
    @Bean
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ErrorCodeMsgFillFilter());
        registrationBean.setName("responseFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}