package SSTT.Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.swaggerInfo()) //swagger 정보 등록
                .select()
                .apis(RequestHandlerSelectors.any()) //모든 controller 패키지 정보 탐색해서 API 문서 만듬
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(true); //기본으로 세팅되는 200,400,403,404 메시지 표시
    }

    private ApiInfo swaggerInfo(){
        return new ApiInfoBuilder()
                .title("S-STT")
                .description("spring boot API Documentation")
                .version("1.0.0")
                .build();
    }
}