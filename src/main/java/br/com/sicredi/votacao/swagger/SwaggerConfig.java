package br.com.sicredi.votacao.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {
        List<ResponseMessage> defaultResponses = new ArrayList<ResponseMessage>() {{
            add(buildResponseMessage(HttpStatus.PRECONDITION_FAILED, "Falha da pré-conidção"));
            add(buildResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno"));
        }};
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, defaultResponses)
                .globalResponseMessage(RequestMethod.DELETE, defaultResponses)
                .globalResponseMessage(RequestMethod.GET, defaultResponses)
                .globalResponseMessage(RequestMethod.PATCH, defaultResponses)
                .globalResponseMessage(RequestMethod.PUT, defaultResponses)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("br.com.sicredi"))
                .build();
    }

    private ResponseMessage buildResponseMessage(HttpStatus status, String message) {
        return new ResponseMessageBuilder()
                .code(status.value())
                .message(message)
                .build();
    }
}

