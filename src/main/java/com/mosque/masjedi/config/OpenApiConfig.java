// package com.mosque.masjedi.config;

// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// /**
//  * Swagger configuration for the project.
//  */
// @Configuration
// public class OpenApiConfig {

//         /**
//          * Creates an OpenAPI bean to configure Swagger.
//          *
//          * @return OpenAPI
//          */
//         @Bean
//         public OpenAPI customOpenAPI() {
//                 return new OpenAPI()
//                                 .components(new Components())
//                                 .info(new Info()
//                                                 .title("Mosque Management API")
//                                                 .description("Spring Boot REST API for Mosque Management System")
//                                                 .version("1.0")
//                                                 .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//         }
// }