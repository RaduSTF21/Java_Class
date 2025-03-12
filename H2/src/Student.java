/**
 * Represents a student who is a person with a registration number and acceptable projects.
 * Each student can be assigned at most one project.
 */
public class Student extends Person {
    private String registrationNumber;
    private Project[] acceptableProjects;
    private Project allocatedProject;

    /**
     *
     * Constructs a Student with the given parameters.
     *
     * @param name                the name of the student
     * @param dateOfBirth         the date of birth of the student
     * @param registrationNumber  the registration number of the student
     * @param acceptableProjects  an array of acceptable projects for the student
     */
    public Student(String name, java.time.LocalDate dateOfBirth, String registrationNumber, Project[] acceptableProjects) {
        super(name, dateOfBirth);
        this.registrationNumber = registrationNumber;
        this.acceptableProjects = acceptableProjects;
        this.allocatedProject = null;
    }

    /**
     * Returns the registration number of the student.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Returns the array of acceptable projects.
     *
     * @return the acceptable projects
     */
    public Project[] getAcceptableProjects() {
        return acceptableProjects;
    }

    /**
     * Returns the project allocated to the student.
     *
     * @return the allocated project, or {@code null} if none is assigned
     */
    public Project getAllocatedProject() {
        return allocatedProject;
    }

    /**
     * Assigns a project to the student.
     *
     * @param project the project to assign
     */
    public void assignProject(Project project) {
        this.allocatedProject = project;
    }

    /**
     * Returns a string representation of the student.
     *
     * @return a string describing the student
     */
    @Override
    public String toString() {
        String allocated = (allocatedProject != null) ? allocatedProject.getProjectName() : "None";
        return "Student[" + super.toString() + ", registrationNumber=" + registrationNumber
                + ", allocatedProject=" + allocated + "]";
    }

    /**
     * Checks if this student is equal to another object.
     * Two students are considered equal if they are the same person and have the same registration number.
     *
     * @param obj the object to compare with
     * @return {@code true} if the students are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Student) {
            Student other = (Student) obj;
            return registrationNumber.equals(other.registrationNumber);
        }
        return false;
    }

    /**
     * Returns a hash code value for the student.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), registrationNumber);
    }
}
