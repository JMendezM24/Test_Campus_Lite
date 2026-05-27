package com.campuslite.ui;

import com.campuslite.logic.CourseManager;
import com.campuslite.logic.StudentManager;
import com.campuslite.logic.EnrollmentManager;


import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema.
 */
public class MainFrame extends JFrame {

    /**
     * Managers globales.
     */
    private final StudentManager studentManager;
    private final CourseManager courseManager;
    private final EnrollmentManager enrollmentManager;
    private StudentsPanel studentsPanel;
    private CoursesPanel coursesPanel;
    private EvaluationsPanel evaluationsPanel;
    private ReportsPanel reportsPanel;
    private EnrollmentsPanel enrollmentsPanel;

    /**
     * Constructor principal.
     */
    public MainFrame(StudentManager studentManager,
            CourseManager courseManager,
            EnrollmentManager enrollmentManager) {

        this.studentManager = studentManager;
        this.courseManager = courseManager;
        this.enrollmentManager =
                enrollmentManager;

        initialize();
    }

    /**
     * Inicializa ventana.
     */
    private void initialize() {

        setTitle("Campus Lite");

        setSize(1200, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                UIStyles.BACKGROUND_COLOR
        );

        /**
         * Título superior.
         */
        JLabel titleLabel =
                new JLabel("Campus Lite");

        titleLabel.setFont(UIStyles.TITLE_FONT);

        titleLabel.setBorder(
                UIStyles.createPadding()
        );

        /**
         * Sistema de pestañas.
         */
        JTabbedPane tabbedPane =
                new JTabbedPane();

        /**
         * Inicialización de paneles.
         */
        studentsPanel =
                new StudentsPanel(studentManager);

        coursesPanel =
                new CoursesPanel(courseManager);

        evaluationsPanel =
                new EvaluationsPanel(courseManager);
        
        enrollmentsPanel =
                new EnrollmentsPanel(
                        studentManager,
                        courseManager,
                        enrollmentManager
                );

        reportsPanel =
                new ReportsPanel(
                        studentManager,
                        enrollmentManager
                );
        /**
         * Sincronización cursos.
         */
        coursesPanel.setOnCoursesChanged(() -> {

            evaluationsPanel.reloadCourses();

            enrollmentsPanel.reloadData();
        });

        /**
         * Sincronización estudiantes.
         */
        studentsPanel.setOnStudentsChanged(() -> {

            enrollmentsPanel.reloadData();

            reportsPanel.reloadStudents();
        });

        /**
         * Pestañas.
         */
        tabbedPane.addTab(
                "Estudiantes",
                studentsPanel
        );

        tabbedPane.addTab(
                "Cursos",
                coursesPanel
        );

        tabbedPane.addTab(
                "Evaluaciones",
                evaluationsPanel
        );
        
        tabbedPane.addTab(
                "Inscripciones",
                enrollmentsPanel
        );

        tabbedPane.addTab(
                "Reportes",
                reportsPanel
        );
        add(titleLabel, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);
        
    }

}