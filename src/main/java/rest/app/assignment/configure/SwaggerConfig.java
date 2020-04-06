package rest.app.assignment.configure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	Contact contact = new Contact("Das Balvinder Das", "https://github.com/DasBalvinderDas/TechBooks-Bookshelf",
			"bdas@sapient.com");

	List<VendorExtension> vendorExtensions = new ArrayList<>();

	ApiInfo apiInfo = new ApiInfo("Book Shelf Web Service documentation",
			"This pages documents Book Shelf app RESTful Web Service endpoints", "1.0",
			"https://github.com/DasBalvinderDas/TechBooks-Bookshelf", contact, "",
			"https://github.com/DasBalvinderDas/TechBooks-Bookshelf", vendorExtensions);

	@Bean
	public Docket apiDocket() {

		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.protocols(new HashSet<>(Arrays.asList("HTTP","HTTPS")))
				.apiInfo(apiInfo)
				.select()
				.apis(RequestHandlerSelectors.basePackage("rest.app.assignment")).paths(PathSelectors.any())
				.build();

		return docket;

	}

}
