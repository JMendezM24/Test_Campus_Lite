package com.campuslite.ui;

import com.campuslite.domain.Course;
import com.campuslite.domain.Enrollment;
import com.campuslite.domain.Student;
import com.campuslite.logic.CourseManager;
import com.campuslite.logic.EnrollmentManager;
import com.campuslite.logic.StudentManager;
import com.campuslite.persistence.EnrollmentCSVRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de inscripciones.
 */
public class EnrollmentsPanel extends JPanel {

    private final StudentManager studentManager;

    private final CourseManager courseManager;

    private final EnrollmentManager enrollmentManager;
    
    private Runnable onEnrollmentsChanged;

    private final EnrollmentCSVRepository repository;

    /**
     * Componentes UI.
     */
    private JComboBox<Student> cmbStudents;

    private JComboBox<Course> cmbCourses;

    private DefaultTableModel model;

    private ModernTable table;

    public EnrollmentsPanel(StudentManager studentManager,
                            CourseManager courseManager,
                            EnrollmentManager enrollmentManager) {

        this.studentManager = studentManager;

        this.courseManager = courseManager;

        this.enrollmentManager = enrollmentManager;

        repository =
                new EnrollmentCSVRepository();

        initialize();

        loadData();

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

        topPanel.setLayout(
                new GridBagLayout()
        );

        topPanel.setBackground(
                UIStyles.BACKGROUND_COLOR
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        /**
         * Combos.
         */
        cmbStudents = new JComboBox<>();

        cmbCourses = new JComboBox<>();

        /**
         * Labels.
         */
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(
                new JLabel("Estudiante"),
                gbc
        );

        gbc.gridx = 1;
        topPanel.add(
                new JLabel("Curso"),
                gbc
        );

        /**
         * Combos.
         */
        gbc.gridy = 1;

        gbc.gridx = 0;
        topPanel.add(cmbStudents, gbc);

        gbc.gridx = 1;
        topPanel.add(cmbCourses, gbc);

        /**
         * Botón.
         */
        ModernButton btnEnroll =
                new ModernButton("📘 Inscribir");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(
                UIStyles.BACKGROUND_COLOR
        );

        buttonPanel.add(btnEnroll);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        topPanel.add(buttonPanel, gbc);

        /**
         * =========================
         * TABLA
         * =========================
         */
        model = new DefaultTableModel();

        model.setColumnIdentifiers(
                new Object[]{
                        "Carnet de Inscripción",
                        "Estudiante",
                        "Curso"
                }
        );

        table = new ModernTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        /**
         * Evento.
         */
        btnEnroll.addActionListener(
                e -> enrollStudent()
        );

        add(topPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Inscribe estudiante.
     */
    private void enrollStudent() {

        try {

            Student student =
                    (Student) cmbStudents
                            .getSelectedItem();

            Course course =
                    (Course) cmbCourses
                            .getSelectedItem();

            if (student == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un estudiante."
                );

                return;
            }

            if (course == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un curso."
                );

                return;
            }

            enrollmentManager.enrollStudent(
                    student,
                    course
            );

            repository.saveEnrollments(
                    enrollmentManager
                            .getEnrollments()
            );

            refreshTable();

            revalidate();

            repaint();
            
            if (onEnrollmentsChanged != null) {

                onEnrollmentsChanged.run();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante inscrito correctamente."
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
     * Refresca tabla.
     */
    private void refreshTable() {

        model.setRowCount(0);

        for (Enrollment enrollment :
                enrollmentManager
                        .getEnrollments()) {

            model.addRow(
                    new Object[]{
                            enrollment.getEnrollmentCode(),

                            enrollment.getStudent()
                                    .getFullInfo(),

                            enrollment.getCourse()
                                    .getName()
                    }
            );
        }

        model.fireTableDataChanged();

        revalidate();

        repaint();
    }

    /**
     * Carga datos.
     */
    private void loadData() {

        cmbStudents.removeAllItems();

        cmbCourses.removeAllItems();

        /**
         * Cargar estudiantes.
         */
        for (Student student :
                studentManager.getStudents()) {

            cmbStudents.addItem(student);
        }

        /**
         * Cargar cursos.
         */
        for (Course course :
                courseManager.getCourses()) {

            cmbCourses.addItem(course);
        }

        revalidate();

        repaint();
    }

    /**
     * Recarga datos en tiempo real.
     */
    public void reloadData() {

        loadData();

        refreshTable();

        revalidate();

        repaint();
    }
    
    /**
     * Evento cuando cambian inscripciones.
     */
    public void setOnEnrollmentsChanged(
            Runnable onEnrollmentsChanged
    ) {

        this.onEnrollmentsChanged =
                onEnrollmentsChanged;
    }
}