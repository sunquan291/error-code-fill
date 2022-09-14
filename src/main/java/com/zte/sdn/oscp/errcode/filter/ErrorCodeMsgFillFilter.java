package com.zte.sdn.oscp.errcode.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.sdn.oscp.errcode.bean.ErrorCodes;
import com.zte.sdn.oscp.errcode.condition.CheckErrorCodeCondition;
import com.zte.sdn.oscp.errcode.dto.Response;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//@Configuration
//@Order(2)
//@WebFilter(urlPatterns = "/*", filterName = "responseFilter")
public class ErrorCodeMsgFillFilter implements Filter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CustomizeResponseWrapper responseWrapper = new CustomizeResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, responseWrapper);
        final String content = responseWrapper.getTextContent();
        final Response response = objectMapper.readValue(content, Response.class);
        //check if need fill error code message
        if (response != null && !response.isSuccess() && Strings.isNotEmpty(response.getErrCode())) {
            final ErrorCodes.ErrorCode errorCode = CheckErrorCodeCondition.errorCodeMap.get(response.getErrCode());
            if (errorCode != null) {
                response.setErrMessage(errorCode.getLabel());
                final String newContent = objectMapper.writeValueAsString(response);
                servletResponse.setContentLength(newContent.length());
                servletResponse.getOutputStream().write(newContent.getBytes());
            }
        }
    }
}