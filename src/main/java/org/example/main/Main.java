package org.example.main;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection myConn = DatabaseConnection.getInstance()) {
            Repository<Employee> repository = new EmployeeRepository();
            // repository.findAll().forEach(System.out::println);
            System.out.println("---- Listando ----");
            repository.findAll().forEach(System.out::println);

           //System.out.println("----- Actualizando un empleado -------");
           //Employee employee = new Employee();
           //employee.setId(7);
           //employee.setFirst_name("Diego");
           //employee.setPa_surname("Pimentel");
           //employee.setMa_surname("Villanueva");
           //employee.setEmail("diego@example.com");
           // employee.setSalary(3000F);

           // repository.save(employee);

            System.out.println("---- Empleado eliminado! ----");
            repository.delete(7);

            System.out.println("---- Listando ----");
            repository.findAll().forEach(System.out::println);
        }
    }
}
