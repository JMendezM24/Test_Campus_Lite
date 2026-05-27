package com.campuslite.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Course.
 */
public class Course {

    private String courseCode;
    private String name;
    private int credits;
    private int capacity;

    /**
     * Lista de evaluaciones asociadas al curso.
     */
    private List<Evaluation> evaluations;

    public Course() {

        evaluations = new ArrayList<>();
    }

    /**
     * Constructor principal.
     */
    public Course(String courseCode,
                  String name,
                  int credits,
                  int capacity) {

        this();

        setCourseCode(courseCode);
        setName(name);
        setCredits(credits);
        setCapacity(capacity);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {

        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso es obligatorio.");
        }

        this.courseCode = courseCode.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio.");
        }

        this.name = name.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {

        if (credits < 0) {
            throw new IllegalArgumentException("Los créditos no pueden ser negativos.");
        }

        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("El cupo debe ser mayor que cero.");
        }

        this.capacity = capacity;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    /**
     * Agrega una evaluación al curso.
     */
    public void addEvaluation(Evaluation evaluation) {

        if (evaluation == null) {
            throw new IllegalArgumentException("La evaluación no puede ser nula.");
        }

        evaluations.add(evaluation);
    }

    /**
     * Calcula el porcentaje total acumulado.
     */
    public double getTotalPercentage() {

        double total = 0;

        for (Evaluation evaluation : evaluations) {
            total += evaluation.getPercentage();
        }

        return total;
    }
    
    /**
     * Calcula promedio final del curso.
     */
    public double calculateCourseAverage() {

        double total = 0;

        for (Evaluation evaluation :
                evaluations) {

            total +=
                    evaluation.calculateContribution();
        }

        return total;
    }

    @Override
    public String toString() {

        return courseCode + " - " + name;
    }

}