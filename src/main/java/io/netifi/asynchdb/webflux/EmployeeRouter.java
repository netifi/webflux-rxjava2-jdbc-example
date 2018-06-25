/**
 * Copyright 2018 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
