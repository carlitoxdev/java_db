package org.example.main;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;
import org.example.util.DatabaseConnection;
// import org.example.view.SwingApp;

import javax.swing.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        /* SwingUtilities.invokeLater(() -> {
            SwingApp app = new SwingApp();
            app.setVisible(true);
        }); */

        /* Video 18 */
        System.out.println("---- Listando Todos ----");
        Repository<Employee> repository = new EmployeeRepository();

        repository.findAll().forEach(System.out::println);

        System.out.println("---- Buscando por ID ----");
        System.out.println(repository.getById(2));

        /*
        try (Connection myConn = DatabaseConnection.getInstance()) {
            if (myConn.getAutoCommit()) {
                myConn.setAutoCommit((false));
            }

            try {
                Repository<Employee> repository = new EmployeeRepository(myConn);

                System.out.println("----- insertar nuevo cliente -----");
                Employee employee = new Employee();

                employee.setFirst_name("Americo");
                employee.setPa_surname("Marquez");
                employee.setMa_surname("Diaz");
                employee.setEmail("diazz@example.com");
                employee.setSalary(30000F);
                employee.setCurp("1MKI118982198MMSKW");

                repository.save(employee);

                myConn.commit();
            } catch (SQLException e) {
                myConn.rollback();
                throw new RuntimeException(e);
            }

            // repository.findAll().forEach(System.out::println);
            // System.out.println("---- Listando ----");
            // repository.findAll().forEach(System.out::println);

           //System.out.println("----- Actualizando un empleado -------");

           //employee.setId(7);


           // repository.save(employee);

            // System.out.println("---- Empleado eliminado! ----");
            // repository.delete(7);

            // System.out.println("---- Listando ----");
            // repository.findAll().forEach(System.out::println);
        } */
    }
}
