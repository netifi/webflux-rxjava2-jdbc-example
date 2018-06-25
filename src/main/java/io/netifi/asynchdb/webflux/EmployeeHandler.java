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

import io.netifi.asynchdb.webflux.model.Department;
import io.netifi.asynchdb.webflux.model.Employee;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeHandler {
  private final EmployeeRepository repository;

  public EmployeeHandler(EmployeeRepository repository) {
    this.repository = repository;
  }

  public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
    Flux<Employee> employees = repository.getAllEmployees();
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(employees, Employee.class);
  }

  public Mono<ServerResponse> getEmployee(ServerRequest request) {
    String firstName = request.pathVariable("fn");
    String lastName = request.pathVariable("ln");

    Mono<Employee> employee = repository.getEmployee(firstName, lastName);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(employee, Employee.class);
  }

  public Mono<ServerResponse> createNewEmployee(ServerRequest request) {
    Mono<Employee> employeeMono = request.bodyToMono(Employee.class);

    Mono<Employee> employee = repository.createNewEmployee(employeeMono);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(employee, Employee.class);
  }

  public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Void> employee = repository.deleteEmployee(id);

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build(employee);
  }

  public Mono<ServerResponse> getAllDepartments(ServerRequest request) {
    Flux<Department> allDepartments = repository.getAllDepartments();
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(allDepartments, Department.class);
  }
}
