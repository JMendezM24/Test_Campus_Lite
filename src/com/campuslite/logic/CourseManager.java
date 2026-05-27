package com.campuslite.logic;

import com.campuslite.domain.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Maneja la lógica de cursos.
 */
public class CourseManager {

    private final List<Course> courses;

    public CourseManager() {

        courses = new ArrayList<>();
    }

    /**
     * Agrega un curso.
     */
    public void addCourse(Course course) {

        validateDuplicateCode(course.getCourseCode());

        courses.add(course);
    }

    /**
     * Elimina curso.
     */
    public void removeCourse(String courseCode) {

        Course course = findCourseByCode(courseCode);

        if (course != null) {
            courses.remove(course);
        }
    }

    /**
     * Busca curso por código.
     */
    public Course findCourseByCode(String code) {

        for (Course course : courses) {

            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }

        return null;
    }

    /**
     * Actualiza curso.
     */
    public void updateCourse(String originalCode,
                             Course updatedCourse) {

        for (Course current : courses) {

            if (current.getCourseCode()
                    .equals(originalCode)) {

                /**
                 * Validar duplicados.
                 */
                for (Course other : courses) {

                    if (other != current
                            &&
                            other.getCourseCode()
                                    .equalsIgnoreCase(
                                            updatedCourse
                                                    .getCourseCode()
                                    )) {

                        throw new IllegalArgumentException(
                                "Ya existe un código."
                        );
                    }
                }

                /**
                 * Actualizar MISMO objeto.
                 */
                current.setCourseCode(
                        updatedCourse.getCourseCode()
                );

                current.setName(
                        updatedCourse.getName()
                );

                return;
            }
        }

        throw new IllegalArgumentException(
                "Curso no encontrado."
        );
    }

    /**
     * Lista de cursos.
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Valida código duplicado.
     */
    private void validateDuplicateCode(String code) {

        if (findCourseByCode(code) != null) {
            throw new IllegalArgumentException("Ya existe un curso con ese código.");
        }
    }

}