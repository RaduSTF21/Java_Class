/**
 * Represents a project proposed by a teacher.
 * A project may be assigned to at most one student.
 */
public class Project {
    private String projectName;
    private Teacher proposedBy;
    private Student assignedStudent;

    /**
     * Constructs a Project with the given name and proposing teacher.
     *
     * @param projectName the name of the project
     * @param proposedBy  the teacher who proposed the project
     */
    public Project(String projectName, Teacher proposedBy) {
        this.projectName = projectName;
        this.proposedBy = proposedBy;
        this.assignedStudent = null;
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the teacher who proposed the project.
     *
     * @return the proposing teacher
     */
    public Teacher getProposedBy() {
        return proposedBy;
    }

    /**
     * Returns the student assigned to this project.
     *
     * @return the assigned student, or {@code null} if none is assigned
     */
    public Student getAssignedStudent() {
        return assignedStudent;
    }

    /**
     * Assigns a student to this project.
     *
     * @param student the student to assign
     */
    public void assignStudent(Student student) {
        this.assignedStudent = student;
    }

    /**
     * Returns a string representation of the project.
     *
     * @return a string describing the project
     */
    @Override
    public String toString() {
        String assigned = (assignedStudent != null) ? assignedStudent.getRegistrationNumber() : "None";
        return "Project[projectName=" + projectName + ", proposedBy=" + proposedBy.getName()
                + ", assignedStudent=" + assigned + "]";
    }

    /**
     * Checks if this project is equal to another object.
     * Two projects are considered equal if they have the same name and are proposed by the same teacher.
     *
     * @param obj the object to compare with
     * @return {@code true} if the projects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {

            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Project other = (Project) obj;
        return projectName.equals(other.projectName) && proposedBy.equals(other.proposedBy);
    }
}
