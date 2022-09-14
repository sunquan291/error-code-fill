package com.zte.sdn.oscp.errcode.condition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.sdn.oscp.errcode.bean.ErrorCodes;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckErrorCodeCondition implements Condition {
    public static Map<String, ErrorCodes.ErrorCode> errorCodeMap = new HashMap<>();
    private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        for (String location : DEFAULT_SEARCH_LOCATIONS.split(",")) {
            try {
                final Resource[] resources = resolver.getResources(location + "*-errorcode-en_US.json");
                for (Resource resource : resources) {
                    if (resource.exists()) {
                        load(resource.getInputStream());
                        return true;
                    }
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                throw new RuntimeException("read errorcode-en_US.json error", e);
            }
        }
        return false;
    }

    private void load(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorCodes errorCode = objectMapper.readValue(inputStream, ErrorCodes.class);
        errorCodeMap = errorCode.errcodes.stream().collect(Collectors.toMap(ErrorCodes.ErrorCode::getCode, v -> v));
    }
}