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
import io.reactivex.Flowable;
import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.NonBlockingConnectionPool;
import org.davidmoten.rx.jdbc.pool.Pools;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class EmployeeRepository {
  private Database db;

  public EmployeeRepository() throws Exception {
    Connection connection = DriverManager.getConnection("jdbc:h2:./build/mydatabase", "sa", "sa");
    NonBlockingConnectionPool pool =
        Pools.nonBlocking()
            .maxPoolSize(Runtime.getRuntime().availableProcessors() * 5)
            .connectionProvider(ConnectionProvider.from(connection))
            .build();

    this.db = Database.from(pool);
  }

  Flux<Employee> getAllEmployees() {
    String sql = "SELECT * FROM employee e JOIN department d ON e.department_id = d.department_id";

    Flowable<Employee> employeeFlowable =
        db.select(sql)
            .get(
                rs -> {
                  Employee employee = new Employee();
                  employee.setId(rs.getInt("employee_id"));
                  employee.setFirstName(rs.getString("employee_firstname"));
                  employee.setLastName(rs.getString("employee_lastname"));
                  employee.setDepartment(rs.getString("department_name"));

                  return employee;
                });

    return Flux.from(employeeFlowable);
  }

  Mono<Employee> getEmployee(String firstName, String lastName) {
    String sql =
        "SELECT employee_id, employee_firstname, employee_lastname, department_name FROM employee e "
            + "JOIN department d ON e.department_id = d.department_id "
            + "WHERE employee_firstname = ? AND "
            + "employee_lastname = ?";

    Flowable<Employee> employeeFlowable =
        db.select(sql)
            .parameters(firstName, lastName)
            .get(
                rs -> {
                  Employee employee = new Employee();
                  employee.setId(rs.getInt("employee_id"));
                  employee.setFirstName(rs.getString("employee_firstname"));
                  employee.setLastName(rs.getString("employee_lastname"));
                  employee.setDepartment(rs.getString("department_name"));

                  return employee;
                });

    return Mono.from(employeeFlowable);
  }

  Mono<Employee> createNewEmployee(Mono<Employee> employeeMono) {

    String createSql =
        "INSERT INTO employee (employee_firstname, employee_lastname, department_id) VALUES (?, ?, ?)";
    String selectDepartmentId = "SELECT department_id from department where department_name = ?";
    String selectSql =
        "SELECT employee_id, employee_firstname, employee_lastname, department_name FROM employee e "
            + "JOIN department d ON e.department_id = d.department_id "
            + "WHERE employee_id = ?";

    return employeeMono.flatMap(
        newEmployee -> {
          Flowable<Integer> employeeIds =
              db.select(selectDepartmentId)
                  .parameters(newEmployee.getDepartment())
                  .getAs(Integer.class)
                  .flatMap(
                      departmentId ->
                          db.update(createSql)
                              .parameters(
                                  newEmployee.getFirstName(),
                                  newEmployee.getLastName(),
                                  departmentId)
                              .returnGeneratedKeys()
                              .getAs(Integer.class));

          Flowable<Employee> employeeFlowable =
              db.select(selectSql)
                  .parameterStream(employeeIds)
                  .get(
                      rs -> {
                        Employee employee = new Employee();
                        employee.setId(rs.getInt("employee_id"));
                        employee.setFirstName(rs.getString("employee_firstname"));
                        employee.setLastName(rs.getString("employee_lastname"));
                        employee.setDepartment(rs.getString("department_name"));

                        return employee;
                      });

          return Mono.from(employeeFlowable);
        });
  }

  Mono<Void> deleteEmployee(String id) {
    String sql = "DELETE FROM employee WHERE employee_id = ?";
    Flowable<Integer> counts = db.update(sql).parameter(id).counts();
    return Flux.from(counts).then();
  }

  Flux<Department> getAllDepartments() {
    return Flux.from(db.select(Department.class).get());
  }
}
