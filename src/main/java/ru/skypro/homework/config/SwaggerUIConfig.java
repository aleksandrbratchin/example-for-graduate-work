package ru.skypro.homework.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.response.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerUIConfig {
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(UserResponse.class),
                        typeResolver.resolve(AdResponse.class),
                        typeResolver.resolve(AdsResponse.class),
                        typeResolver.resolve(CommentResponse.class),
                        typeResolver.resolve(CommentsResponse.class),
                        typeResolver.resolve(ExtendedAdResponse.class)
                )
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
