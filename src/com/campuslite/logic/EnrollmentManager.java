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
     * Busca inscripción.
     */
    public Enrollment findEnrollment(
            Student student,
            Course course) {

        for (Enrollment enrollment :
                enrollments) {

            if (enrollment.getStudent()
                    .getStudentCode()
                    .equals(student.getStudentCode())
                    &&
                    enrollment.getCourse()
                            .getCourseCode()
                            .equals(course.getCourseCode())) {

                return enrollment;
            }
        }

        return null;
    }

    /**
     * Obtiene estudiantes inscritos.
     */
    public List<Student> getStudents() {

        List<Student> students =
                new ArrayList<>();

        for (Enrollment enrollment :
                enrollments) {

            if (!students.contains(
                    enrollment.getStudent())) {

                students.add(
                        enrollment.getStudent()
                );
            }
        }

        return students;
    }

    /**
     * Obtiene cursos inscritos.
     */
    public List<Course> getCourses() {

        List<Course> courses =
                new ArrayList<>();

        for (Enrollment enrollment :
                enrollments) {

            if (!courses.contains(
                    enrollment.getCourse())) {

                courses.add(
                        enrollment.getCourse()
                );
            }
        }

        return courses;
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
     * Estudiantes por curso.
     */
    public List<Student> getStudentsByCourse(
            Course course) {

        List<Student> students =
                new ArrayList<>();

        for (Enrollment enrollment :
                enrollments) {

            if (enrollment.getCourse()
                    .getCourseCode()
                    .equals(
                            course.getCourseCode()
                    )) {

                students.add(
                        enrollment.getStudent()
                );
            }
        }

        return students;
    }

    /**
     * Elimina inscripciones por estudiante.
     */
    public void removeEnrollmentsByStudent(
            String studentCode
    ) {

        enrollments.removeIf(
                enrollment ->
                        enrollment.getStudent()
                                .getStudentCode()
                                .equals(studentCode)
        );
    }

    /**
     * Elimina inscripciones por curso.
     */
    public void removeEnrollmentsByCourse(
            String courseCode
    ) {

        enrollments.removeIf(
                enrollment ->
                        enrollment.getCourse()
                                .getCourseCode()
                                .equals(courseCode)
        );
    }

    /**
     * Obtiene inscripciones.
     */
    public List<Enrollment> getEnrollments() {

        return enrollments;
    }
}