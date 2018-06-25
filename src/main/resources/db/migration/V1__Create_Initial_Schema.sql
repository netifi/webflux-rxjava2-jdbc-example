--
-- Copyright 2018 Netifi Inc.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--    http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE TABLE public.department
(
  department_id   INT           AUTO_INCREMENT PRIMARY KEY,
  department_name VARCHAR(100)  NOT NULL
);

CREATE TABLE public.employee (
  employee_id         INT           AUTO_INCREMENT PRIMARY KEY,
  employee_firstname  VARCHAR (100) NOT NULL,
  employee_lastname   VARCHAR (100) NOT NULL,
  department_id       INT           NOT NULL,

  FOREIGN KEY (department_id) REFERENCES public.department (department_id)
);

INSERT INTO public.department (department_id, department_name) VALUES (1, 'Sales');
INSERT INTO public.department (department_id, department_name) VALUES (2, 'Marketing');
INSERT INTO public.department (department_id, department_name) VALUES (3, 'Human Resources');
INSERT INTO public.department (department_id, department_name) VALUES (4, 'Manufacturing');
INSERT INTO public.department (department_id, department_name) VALUES (5, 'Accounting');

INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (1, 'Bob', 'Smith', 4);
INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (2, 'John', 'Green', 1);
INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (3, 'Sally', 'Wilson', 3);
INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (4, 'Harold', 'Smith', 2);
INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (5, 'Joe', 'White', 5);
INSERT INTO public.employee (employee_id, employee_firstname, employee_lastname, department_id) VALUES (6, 'Arnold', 'Shoemaker', 4);