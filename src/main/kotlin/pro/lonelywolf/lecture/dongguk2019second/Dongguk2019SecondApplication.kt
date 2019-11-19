package pro.lonelywolf.lecture.dongguk2019second

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.servlet.ServletContext

@SpringBootApplication
class Dongguk2019SecondApplication

fun main(args: Array<String>) {
  runApplication<Dongguk2019SecondApplication>(*args)
}

@Configuration
@EnableSwagger2
class SwaggerConfiguration(val servletContext: ServletContext) {

  @Bean
  fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
  }

  private fun apiInfo(): ApiInfo {
    return ApiInfoBuilder()
        .title("API Documentation")
        .description("API Documentation for Dongguk University C programming assignment.")
        .license("lonelywolf.pro")
        .licenseUrl("http://lonelywolf.pro")
        .version("1")
        .build()
  }
}