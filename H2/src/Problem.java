/**
 * Represents the student project allocation problem.
 * <p>
 * It maintains arrays of students, teachers, and projects and provides methods
 * to add participants and projects, retrieve all persons involved, and allocate projects
 * to students using a simple greedy algorithm.
 * </p>
 */
public class Problem {
    private Student[] students;
    private int studentCount;
    private Teacher[] teachers;
    private int teacherCount;
    private Project[] projects;
    private int projectCount;

    /**
     * Constructs a Problem instance with specified maximum capacities.
     *
     * @param maxStudents maximum number of students
     * @param maxTeachers maximum number of teachers
     * @param maxProjects maximum number of projects
     */
    public Problem(int maxStudents, int maxTeachers, int maxProjects) {
        students = new Student[maxStudents];
        studentCount = 0;
        teachers = new Teacher[maxTeachers];
        teacherCount = 0;
        projects = new Project[maxProjects];
        projectCount = 0;
    }

    /**
     * Adds a student to the problem if not already present.
     *
     * @param student the student to add
     * @return {@code true} if the student was added; {@code false} if duplicate or no space available
     */
    public boolean addStudent(Student student) {
        // Check for duplicates
        for (int i = 0; i < studentCount; i++) {
            if (students[i].equals(student)) {
                return false;
            }
        }
        if (studentCount >= students.length) {
            return false;
        }
        students[studentCount++] = student;
        return true;
    }

    /**
     * Adds a teacher to the problem if not already present.
     *
     * @param teacher the teacher to add
     * @return {@code true} if the teacher was added; {@code false} if duplicate or no space available
     */
    public boolean addTeacher(Teacher teacher) {
        // Check for duplicates
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].equals(teacher)) {
                return false;
            }
        }
        if (teacherCount >= teachers.length) {
            return false;
        }
        teachers[teacherCount++] = teacher;
        return true;
    }

    /**
     * Adds a project to the problem if not already present.
     *
     * @param project the project to add
     * @return {@code true} if the project was added; {@code false} if duplicate or no space available
     */
    public boolean addProject(Project project) {
        // Check for duplicates
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].equals(project)) {
                return false;
            }
        }
        if (projectCount >= projects.length) {
            return false;
        }
        projects[projectCount++] = project;
        return true;
    }

    /**
     * Returns an array of all persons involved in the problem (students and teachers).
     *
     * @return an array of {@code Person} objects
     */
    public Person[] getPersons() {
        Person[] persons = new Person[studentCount + teacherCount];
        int index = 0;
        for (int i = 0; i < studentCount; i++) {
            persons[index++] = students[i];
        }
        for (int i = 0; i < teacherCount; i++) {
            persons[index++] = teachers[i];
        }
        return persons;
    }

    /**
     * Allocates projects to students using a two-pass greedy algorithm.
     * <p>
     * In the first pass, each student is assigned the first acceptable project that is available.
     * In the second (fail safe) pass, any student who remains unallocated is assigned the first available
     * project from the list, regardless of whether it is acceptable.
     * </p>
     */
    public void allocateProjects() {
        // First pass: assign acceptable projects.
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            if (student.getAllocatedProject() != null) {
                continue;
            }
            Project[] acceptable = student.getAcceptableProjects();
            if (acceptable == null) {
                continue;
            }
            for (int j = 0; j < acceptable.length; j++) {
                Project project = acceptable[j];
                if (isProjectAvailable(project)) {
                    student.assignProject(project);
                    project.assignStudent(student);
                    break; // Move to the next student.
                }
            }
        }

        // Fail-safe pass: assign any available project to unallocated students.
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            if (student.getAllocatedProject() == null) {
                for (int k = 0; k < projectCount; k++) {
                    Project project = projects[k];
                    if (project.getAssignedStudent() == null) {
                        student.assignProject(project);
                        project.assignStudent(student);
                        break; // Stop once a project is assigned.
                    }
                }
            }
        }
    }


    /**
     * Checks if a given project is available for allocation.
     * <p>
     * A project is available if it is in the problem's project list and has not been assigned to any student.
     * </p>
     *
     * @param project the project to check
     * @return {@code true} if the project is available; {@code false} otherwise
     */
    private boolean isProjectAvailable(Project project) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].equals(project)) {
                return projects[i].getAssignedStudent() == null;
            }
        }
        return false;
    }
}
