package org.example.view;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.example.repository.Repository;

public class SwingApp extends JFrame {
  private final Repository<Employee> employeeRepository;
  private final JTable employeeTable;

  public SwingApp() {
    //Configurar la ventana
    setTitle("Gestion de Empleados");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 340);

    //Tabla para mostrar los empleados
    employeeTable = new JTable();
    JScrollPane scrollPane = new JScrollPane(employeeTable);
    add(scrollPane, BorderLayout.CENTER);

    // Botones para acciones
    JButton agregarButton = new JButton("Agregar");
    JButton actualizarButton = new JButton("Actualizar");
    JButton eliminarButton = new JButton("Eliminar");

    // Configurar el panel de botones
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(agregarButton);
    buttonPanel.add(actualizarButton);
    buttonPanel.add(eliminarButton);
    add(buttonPanel, BorderLayout.SOUTH);

    // Establecer estilos para los botones
    agregarButton.setBackground(new Color(46, 204, 113));    
    agregarButton.setForeground(Color.WHITE);
    agregarButton.setFocusPainted(false);

    actualizarButton.setBackground(new Color(52, 152, 219));    
    actualizarButton.setForeground(Color.WHITE);
    actualizarButton.setFocusPainted(false);

    eliminarButton.setBackground(new Color(231, 76, 60));    
    eliminarButton.setForeground(Color.WHITE);
    eliminarButton.setFocusPainted(false);

    // Crear el objeto Repository para acceder a la DB
    employeeRepository = new EmployeeRepository();

    // Cargar los empleados inciales en la tabla
    refreshEmployeeTable();

    // Agregar ActionListener para los botones
    agregarButton.addActionListener(e -> {
      try {
        agregarEmpleado();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    });

    actualizarButton.addActionListener(e -> actualizarEmpleado());
    
    eliminarButton.addActionListener(e -> eliminarEmpleado());
  }

  private void refreshEmployeeTable() {
    // Obtener la lista actualizada de empleados desde la DB
    try {
      List<Employee> employees = employeeRepository.findAll();

      // Crear un modelo de tabla y establecer los datos de los empleados
      DefaultTableModel model = new DefaultTableModel();

      model.addColumn("ID");
      model.addColumn("Nombre");
      model.addColumn("Apellido Paterno");
      model.addColumn("Apellido Materno");
      model.addColumn("Email");
      model.addColumn("Salario");

      for (Employee employee : employees) {
        Object[] rowData = {
          employee.getId(),
          employee.getFirst_name(),
          employee.getPa_surname(),
          employee.getMa_surname(),
          employee.getEmail(),
          employee.getSalary()
        };

        model.addRow(rowData);
      }

      // Establecer el modelo de la tabla actualizada
      employeeTable.setModel(model);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Error al obterner los empleados de la DB", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void agregarEmpleado() throws SQLException {
    // Crear un formulario para agregar al empleado
    JTextField nombreField = new JTextField();
    JTextField paternoField = new JTextField();
    JTextField maternoField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField salarioField = new JTextField();

    Object[] fields = {
      "Nombre:", nombreField,
      "Apellido Paterno:", paternoField,
      "Apellido Materno:", maternoField,
      "Email:", emailField,
      "Salario:", salarioField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Agregar Empleado", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      // Crear un nuevo objeto Employee con los datos ingresados
      Employee employee = new Employee();

      employee.setFirst_name(nombreField.getText());
      employee.setPa_surname(paternoField.getText());
      employee.setMa_surname(maternoField.getText());
      employee.setEmail(emailField.getText());
      employee.setSalary(Float.parseFloat(salarioField.getText()));

      // guardar el nuevo empleado en la DB
      employeeRepository.save(employee);

      // actualizar la tabla con los empleados actualizados
      refreshEmployeeTable();

      JOptionPane.showMessageDialog(this, "Empleado agregado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void actualizarEmpleado() {
    // Obterne el id del empleado a actualizar
    String empleadoIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del empleado a actualizar", "Actualizar Empleado", JOptionPane.QUESTION_MESSAGE);

    if (empleadoIdStr != null) {
      try {
        int empleadoId = Integer.parseInt(empleadoIdStr);

        // Obtener el empleado desde la base de datos
        Employee empleado = employeeRepository.getById(empleadoId);

        if (empleado != null) {
          // Crear un formulario con los datos del empleado
          JTextField nombreField = new JTextField(empleado.getFirst_name());
          JTextField apellidoPaternoField = new JTextField(empleado.getPa_surname());
          JTextField apellinoMAternoField = new JTextField(empleado.getMa_surname());
          JTextField emailField = new JTextField(empleado.getEmail());
          JTextField salarioField = new JTextField(String.valueOf(empleado.getSalary()));

          Object[] fields = {
            "Nombre:", nombreField,
            "Apellido Paterno:", apellidoPaternoField,
            "Apellido Materno:", apellinoMAternoField,
            "Email:", emailField,
            "Salario:", salarioField
          };

          int confirmResult = JOptionPane.showConfirmDialog(this, fields, "Actualizar Empleado", JOptionPane.OK_CANCEL_OPTION);

          if (confirmResult == JOptionPane.OK_OPTION) {
            // Actualizar los datos del empleado con los valores ingresados
            empleado.setFirst_name(nombreField.getText());
            empleado.setPa_surname(apellidoPaternoField.getText());
            empleado.setMa_surname(apellinoMAternoField.getText());
            empleado.setEmail(emailField.getText());
            empleado.setSalary(Float.parseFloat(salarioField.getText()));

            //Guardar los cambios en la DB
            employeeRepository.save(empleado);

            // Actualizar la tabla de empleados en la interfaz
            refreshEmployeeTable();
          }
        } else {
          JOptionPane.showMessageDialog(this, "No se encontro ningun empleado con el ID especificado", "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch(NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese un valor numerico valido para el ID", "Erorr", JOptionPane.ERROR_MESSAGE);
      } catch(SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al obterner los datos del empleado en la DB", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void eliminarEmpleado() {
    // Obterner el ID del empleado a eliminar
    String empleadoIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del empleado a eliminar", "Eliminar Empleado", JOptionPane.QUESTION_MESSAGE);

    if(empleadoIdStr != null) {
      try {
        int empleadoId = Integer.parseInt(empleadoIdStr);

        // Confirmar la eliminacion del empleado
        int confirmResult = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar el, empleado", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);

        if(confirmResult == JOptionPane.YES_OPTION) {
          // Eliminar el empleado de la DB
          employeeRepository.delete(empleadoId);

          // Actualizar la tabla de empleados en la interfaz
          refreshEmployeeTable();
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese un valor numerico valido para el ID del empleado", "Error", JOptionPane.ERROR_MESSAGE);
      } catch(SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
