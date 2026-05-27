package com.campuslite.logic;

import com.campuslite.domain.Course;
import com.campuslite.domain.Enrollment;
import com.campuslite.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Maneja inscripciones.
 */
public class EnrollmentManager {

    private final List<Enrollment> enrollments;

    public EnrollmentManager() {

        enrollments = new ArrayList<>();
    }

    /**
     * Inscribe estudiante.
     */
    public void enrollStudent(Student student,
                              Course course) {

        if (isStudentEnrolled(student, course)) {

            throw new IllegalArgumentException(
                    "El estudiante ya está inscrito."
            );
        }

        enrollments.add(
                new Enrollment(student, course)
        );
    }

    /**
     * Verifica inscripción.
     */
    public boolean isStudentEnrolled(
            Student student,
            Course course) {

        for (Enrollment enrollment :
                enrollments) {

            if (enrollment.getStudent()
                    .getStudentCode()
                    .equals(
                            student.getStudentCode()
                    )
                    &&
                    enrollment.getCourse()
                            .getCourseCode()
                            .equals(
                                    course.getCourseCode()
                            )) {

                return true;
            }
        }

        return false;
    }

    /**
     * Cursos de estudiante.
     */
    public List<Course> getCoursesByStudent(
            Student student) {

        List<Course> courses =
                new ArrayList<>();

        for (Enrollment enrollment :
                enrollments) {

            if (enrollment.getStudent()
                    .getStudentCode()
                    .equals(
                            student.getStudentCode()
                    )) {

                courses.add(
                        enrollment.getCourse()
                );
            }
        }

        return courses;
    }

    /**
     * Obtiene inscripciones.
     */
    public List<Enrollment> getEnrollments() {

        return enrollments;
    }
}