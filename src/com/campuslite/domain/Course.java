package com.campuslite.domain;

/**
 * Clase Course.
 */
public class Course {

    private String courseCode;
    private String name;
    private int credits;
    private int capacity;

    /**
     * Constructor vacío.
     */
    public Course() {
    }

    /**
     * Constructor principal.
     */
    public Course(String courseCode,
                  String name,
                  int credits,
                  int capacity) {

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
            throw new IllegalArgumentException(
                    "El código del curso es obligatorio."
            );
        }

        this.courseCode =
                courseCode.trim().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre del curso es obligatorio."
            );
        }

        this.name = name.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {

        if (credits < 0) {

            throw new IllegalArgumentException(
                    "Los créditos no pueden ser negativos."
            );
        }

        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {

        if (capacity <= 0) {

            throw new IllegalArgumentException(
                    "El cupo debe ser mayor que cero."
            );
        }

        this.capacity = capacity;
    }

    @Override
    public String toString() {

        return courseCode + " - " + name;
    }
}