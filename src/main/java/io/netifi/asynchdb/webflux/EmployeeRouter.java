package io.netifi.asynchdb.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EmployeeRouter {
  @Bean
  public RouterFunction<ServerResponse> route(EmployeeHandler handler) {
    return RouterFunctions.route(
            GET("/employees").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::getAllEmployees)
        .andRoute(
            GET("/employee/fn/{fn}/ln/{ln}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::getEmployee)
        .andRoute(
            PUT("/employee").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::createNewEmployee)
        .andRoute(
            DELETE("/employee/id/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::deleteEmployee)
        .andRoute(
            GET("/departments").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::getAllDepartments);
  }
}
