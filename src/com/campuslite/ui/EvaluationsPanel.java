package com.campuslite.ui;

import com.campuslite.domain.*;
import com.campuslite.logic.CourseManager;
import com.campuslite.logic.EvaluationManager;
import com.campuslite.logic.ValidationUtils;
import com.campuslite.persistence.EvaluationCSVRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de evaluaciones.
 */
public class EvaluationsPanel extends JPanel {

    private final CourseManager courseManager;

    private final EvaluationManager evaluationManager;

    private final EvaluationCSVRepository repository;

    /**
     * Componentes UI.
     */
    private JComboBox<Course> cmbCourses;

    private JComboBox<String> cmbType;

    private JTextField txtName;

    private JTextField txtScore;

    private JTextField txtPercentage;

    private ModernTable table;

    private DefaultTableModel model;

    public EvaluationsPanel(CourseManager courseManager) {

        this.courseManager = courseManager;

        evaluationManager = new EvaluationManager();

        repository = new EvaluationCSVRepository();

        initialize();

        loadCourses();

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
         * Campos.
         */
        cmbCourses = new JComboBox<>();

        cmbType = new JComboBox<>();

        cmbType.addItem("Examen");

        cmbType.addItem("Laboratorio");

        cmbType.addItem("Proyecto");

        txtName = new JTextField(15);

        txtScore = new JTextField(15);

        txtPercentage = new JTextField(15);

        /**
         * =========================
         * FILA 1 LABELS
         * =========================
         */
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Curso"), gbc);

        gbc.gridx = 1;
        topPanel.add(new JLabel("Tipo"), gbc);

        gbc.gridx = 2;
        topPanel.add(new JLabel("Nombre"), gbc);

        gbc.gridx = 3;
        topPanel.add(new JLabel("Nota"), gbc);

        gbc.gridx = 4;
        topPanel.add(new JLabel("Porcentaje"), gbc);

        /**
         * =========================
         * FILA 2 CAMPOS
         * =========================
         */
        gbc.gridy = 1;

        gbc.gridx = 0;
        topPanel.add(cmbCourses, gbc);

        gbc.gridx = 1;
        topPanel.add(cmbType, gbc);

        gbc.gridx = 2;
        topPanel.add(txtName, gbc);

        gbc.gridx = 3;
        topPanel.add(txtScore, gbc);

        gbc.gridx = 4;
        topPanel.add(txtPercentage, gbc);

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
                new ModernButton("Agregar");

        ModernButton btnDelete =
                new ModernButton("Eliminar");
        
        ModernButton btnUpdate =
                new ModernButton("Actualizar");

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

        gbc.gridwidth = 5;

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
                        "Curso",
                        "Tipo",
                        "Nombre",
                        "Nota",
                        "Porcentaje"
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
         * Evento agregar.
         */
        btnAdd.addActionListener(
                e -> addEvaluation()
        );

        /**
         * Evento eliminar.
         */
        btnDelete.addActionListener(
                e -> deleteEvaluation()
        );
        
        btnUpdate.addActionListener(
                e -> updateEvaluation()
        );
        table.getSelectionModel()
        .addListSelectionListener(
                e -> loadSelectedEvaluation()
        );

        /**
         * Refresca tabla al cambiar curso.
         */
        cmbCourses.addActionListener(
                e -> refreshTable()
        );

        add(topPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Agrega evaluación.
     */
    private void addEvaluation() {

        try {

            Course course =
                    (Course) cmbCourses.getSelectedItem();

            if (course == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe existir al menos un curso."
                );

                return;
            }

            String name = txtName.getText();

            String scoreText =
                    txtScore.getText();

            String percentageText =
                    txtPercentage.getText();

            validateFields(
                    name,
                    scoreText,
                    percentageText
            );

            double score =
                    Double.parseDouble(scoreText);

            double percentage =
                    Double.parseDouble(
                            percentageText
                    );

            String type =
                    cmbType.getSelectedItem()
                            .toString();

            Evaluation evaluation =
                    createEvaluation(
                            type,
                            name,
                            score,
                            percentage
                    );

            evaluationManager.addEvaluationToCourse(
                    course,
                    evaluation
            );

            repository.saveEvaluations(
                    courseManager.getCourses()
            );

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Evaluación agregada correctamente."
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
     * Actualiza evaluación.
     */
    private void updateEvaluation() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una evaluación."
            );

            return;
        }

        try {

            Course course =
                    (Course) cmbCourses
                            .getSelectedItem();

            if (course == null) {
                return;
            }

            Evaluation oldEvaluation =
                    course.getEvaluations()
                            .get(row);

            course.getEvaluations().remove(
                    oldEvaluation
            );

            /**
             * Nueva evaluación.
             */
            Evaluation updatedEvaluation =
                    createEvaluation(
                            cmbType.getSelectedItem()
                                    .toString(),
                            txtName.getText(),
                            Double.parseDouble(
                                    txtScore.getText()
                            ),
                            Double.parseDouble(
                                    txtPercentage.getText()
                            )
                    );

            course.addEvaluation(
                    updatedEvaluation
            );

            /**
             * Guarda CSV.
             */
            repository.saveEvaluations(
                    courseManager.getCourses()
            );

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Evaluación actualizada."
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
     * Elimina evaluación.
     */
    private void deleteEvaluation() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una evaluación."
            );

            return;
        }

        Course course =
                (Course) cmbCourses.getSelectedItem();

        if (course == null) {
            return;
        }

        Evaluation evaluation =
                course.getEvaluations().get(row);

        evaluationManager.removeEvaluation(
                course,
                evaluation
        );

        repository.saveEvaluations(
                courseManager.getCourses()
        );

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Evaluación eliminada."
        );
    }

    /**
     * Refresca tabla.
     */
    private void refreshTable() {

        model.setRowCount(0);

        Course selectedCourse =
                (Course) cmbCourses.getSelectedItem();

        if (selectedCourse == null) {
            return;
        }

        /**
         * Mostrar TODAS las evaluaciones
         * del curso seleccionado.
         */
        for (Evaluation evaluation :
                selectedCourse.getEvaluations()) {

            model.addRow(
                    new Object[]{
                            selectedCourse.getCourseCode(),
                            evaluation.getTypeName(),
                            evaluation.getEvaluationName(),
                            evaluation.getScore(),
                            evaluation.getPercentage()
                    }
            );
        }

        /**
         * Fuerza refresco visual.
         */
        model.fireTableDataChanged();
    }

    /**
     * Crea evaluación según tipo.
     */
    private Evaluation createEvaluation(String type,
                                        String name,
                                        double score,
                                        double percentage) {

        switch (type) {

            case "Examen":

                return new WrittenExam(
                        name,
                        score,
                        percentage
                );

            case "Laboratorio":

                return new Laboratory(
                        name,
                        score,
                        percentage
                );

            case "Proyecto":

                return new ProjectEvaluation(
                        name,
                        score,
                        percentage
                );

            default:
                return null;
        }
    }
    /**
     * Carga evaluación seleccionada.
     */
    private void loadSelectedEvaluation() {

        int row = table.getSelectedRow();

        if (row == -1) {
            return;
        }

        txtName.setText(
                model.getValueAt(row, 2)
                        .toString()
        );

        txtScore.setText(
                model.getValueAt(row, 3)
                        .toString()
        );

        txtPercentage.setText(
                model.getValueAt(row, 4)
                        .toString()
        );

        cmbType.setSelectedItem(
                model.getValueAt(row, 1)
                        .toString()
        );
    }

    /**
     * Valida campos.
     */
    private void validateFields(String name,
                                String score,
                                String percentage) {

        if (ValidationUtils.isEmpty(name)
                || ValidationUtils.isEmpty(score)
                || ValidationUtils.isEmpty(percentage)) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios."
            );
        }

        if (!ValidationUtils.isDouble(score)) {

            throw new IllegalArgumentException(
                    "La nota debe ser numérica."
            );
        }

        if (!ValidationUtils.isDouble(percentage)) {

            throw new IllegalArgumentException(
                    "El porcentaje debe ser numérico."
            );
        }
    }

    /**
     * Limpia campos.
     */
    private void clearFields() {

        txtName.setText("");

        txtScore.setText("");

        txtPercentage.setText("");
    }

    /**
     * Carga cursos en combo.
     */
    /**
     * Carga cursos en combo.
     */
    private void loadCourses() {

        Course previousSelection =
                (Course) cmbCourses.getSelectedItem();

        cmbCourses.removeAllItems();

        for (Course course :
                courseManager.getCourses()) {

            cmbCourses.addItem(course);
        }

        /**
         * Mantiene selección anterior.
         */
        if (previousSelection != null) {

            for (int i = 0;
                 i < cmbCourses.getItemCount();
                 i++) {

                Course current =
                        cmbCourses.getItemAt(i);

                if (current.getCourseCode()
                        .equals(
                                previousSelection.getCourseCode()
                        )) {

                    cmbCourses.setSelectedIndex(i);

                    break;
                }
            }
        }

        refreshTable();
    }

    
    /**
     * Recarga cursos en tiempo real.
     */
    public void reloadCourses() {

        loadCourses();
        

        refreshTable();
    }

}