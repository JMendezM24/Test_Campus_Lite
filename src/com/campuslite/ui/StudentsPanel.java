package com.campuslite.ui;

import com.campuslite.domain.Student;
import com.campuslite.logic.EnrollmentManager;
import com.campuslite.logic.StudentManager;
import com.campuslite.logic.ValidationUtils;
import com.campuslite.persistence.StudentCSVRepository;
import com.campuslite.persistence.EnrollmentCSVRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de estudiantes.
 */
public class StudentsPanel extends JPanel {

    private final StudentManager studentManager;
    private final EnrollmentManager enrollmentManager;

    private final StudentCSVRepository repository;
    private final EnrollmentCSVRepository
    enrollmentRepository;
    private Runnable onStudentsChanged;

    /**
     * Componentes UI.
     */
    private JTextField txtCode;
    private JTextField txtFirstName;
    private JTextField txtLastName;

    private ModernTable table;
    private DefaultTableModel model;

    public StudentsPanel(
            StudentManager studentManager,
            EnrollmentManager enrollmentManager
    ) {

        this.studentManager = studentManager;
        this.enrollmentManager = enrollmentManager;

        repository = new StudentCSVRepository();
        enrollmentRepository =
                new EnrollmentCSVRepository();

        initialize();

        refreshTable();
    }

    /**
     * Inicializa interfaz.
     */
    private void initialize() {

        setLayout(new BorderLayout());

        setBackground(UIStyles.BACKGROUND_COLOR);

        setBorder(UIStyles.createPadding());

        /**
         * =========================
         * PANEL SUPERIOR
         * =========================
         */
        JPanel topPanel = new JPanel();

        topPanel.setLayout(new GridBagLayout());

        topPanel.setBackground(
                UIStyles.BACKGROUND_COLOR
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        /**
         * Campos de texto más grandes.
         */
        txtCode = new JTextField(15);

        txtFirstName = new JTextField(15);

        txtLastName = new JTextField(15);

        /**
         * =========================
         * FILA 1 LABELS
         * =========================
         */
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Carnet"), gbc);

        gbc.gridx = 1;
        topPanel.add(new JLabel("Nombre"), gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Apellido"), gbc);

        /**
         * =========================
         * FILA 2 CAMPOS
         * =========================
         */
        gbc.gridy = 1;

        gbc.gridx = 0;
        topPanel.add(txtCode, gbc);

        gbc.gridx = 1;
        topPanel.add(txtFirstName, gbc);

        gbc.gridx = 2;
        topPanel.add(txtLastName, gbc);

        /**
         * =========================
         * PANEL BOTONES
         * =========================
         */
        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(
                UIStyles.BACKGROUND_COLOR
        );

        ModernButton btnAdd =
                new ModernButton("➕ Agregar");

        ModernButton btnUpdate =
                new ModernButton("🔄 Actualizar");

        ModernButton btnDelete =
                new ModernButton("❌ Eliminar");

        buttonPanel.add(btnAdd);

        buttonPanel.add(btnUpdate);

        buttonPanel.add(btnDelete);

        /**
         * =========================
         * FILA 3 BOTONES
         * =========================
         */
        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.gridwidth = 3;

        gbc.anchor = GridBagConstraints.CENTER;

        topPanel.add(buttonPanel, gbc);

        /**
         * =========================
         * TABLA
         * =========================
         */
        model = new DefaultTableModel();

        model.setColumnIdentifiers(
                new Object[]{
                        "Carnet",
                        "Nombre",
                        "Apellido",
                        "Correo"
                }
        );

        table = new ModernTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        /**
         * =========================
         * EVENTOS
         * =========================
         */

        /**
         * Evento agregar estudiante.
         */
        btnAdd.addActionListener(e -> addStudent());

        /**
         * Evento eliminar estudiante.
         */
        btnDelete.addActionListener(e -> deleteStudent());

        /**
         * Evento actualizar estudiante.
         */
        btnUpdate.addActionListener(e -> updateStudent());

        /**
         * Evento seleccionar fila.
         */
        table.getSelectionModel()
                .addListSelectionListener(e -> loadSelectedStudent());

        add(topPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Agrega estudiante.
     */
    private void addStudent() {

        try {

            String code = txtCode.getText();

            String firstName = txtFirstName.getText();

            String lastName = txtLastName.getText();

            validateFields(code, firstName, lastName);

            Student student =
                    new Student(
                            code,
                            firstName,
                            lastName
                    );

            studentManager.addStudent(student);

            repository.saveStudents(
                    studentManager.getStudents()
            );

            refreshTable();
            
            if (onStudentsChanged != null) {

                onStudentsChanged.run();
            }

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante agregado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina estudiante.
     */
    private void deleteStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estudiante."
            );

            return;
        }

        String code =
                model.getValueAt(row, 0).toString();
        
        enrollmentManager.removeEnrollmentsByStudent(
                code
        );

        studentManager.removeStudent(code);
        
        enrollmentRepository.saveEnrollments(
                enrollmentManager.getEnrollments()
        );

        repository.saveStudents(
                studentManager.getStudents()
        );

        refreshTable();
        
        if (onStudentsChanged != null) {

            onStudentsChanged.run();
        }

        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Estudiante eliminado."
        );
    }

    /**
     * Actualiza estudiante.
     */
    private void updateStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estudiante."
            );

            return;
        }

        try {

            String originalCode =
                    model.getValueAt(row, 0).toString();

            String newCode = originalCode;            
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();

            validateFields(newCode, firstName, lastName);

            Student updatedStudent =
                    new Student(
                            newCode,
                            firstName,
                            lastName
                    );

            studentManager.updateStudent(
                    originalCode,
                    updatedStudent
            );

            repository.saveStudents(
                    studentManager.getStudents()
            );

            refreshTable();

            if (onStudentsChanged != null) {
                onStudentsChanged.run();
            }

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga estudiante seleccionado.
     */
    private void loadSelectedStudent() {

        int row = table.getSelectedRow();

        if (row != -1) {
        	
        	txtCode.setEditable(false);

            txtCode.setText(
                    model.getValueAt(row, 0).toString()
            );

            txtFirstName.setText(
                    model.getValueAt(row, 1).toString()
            );

            txtLastName.setText(
                    model.getValueAt(row, 2).toString()
            );
        }
    }
    /**
     * Evento cuando cambian estudiantes.
     */
    public void setOnStudentsChanged(
            Runnable onStudentsChanged) {

        this.onStudentsChanged =
                onStudentsChanged;
    }

    /**
     * Refresca tabla.
     */
    private void refreshTable() {

        model.setRowCount(0);

        for (Student student :
                studentManager.getStudents()) {

            model.addRow(
                    new Object[]{
                            student.getStudentCode(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getEmail()
                    }
            );
        }
    }

    /**
     * Limpia campos.
     */
    private void clearFields() {

        txtCode.setText("");

        txtFirstName.setText("");

        txtLastName.setText("");
        
        /**
         * Volver a habilitar carnet
         * para nuevos registros.
         */
        txtCode.setEditable(true);
    }

    /**
     * Valida campos vacíos.
     */
    private void validateFields(String code,
                                String firstName,
                                String lastName) {

        if (ValidationUtils.isEmpty(code)
                || ValidationUtils.isEmpty(firstName)
                || ValidationUtils.isEmpty(lastName)) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios."
            );
        }
    }

    

}