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