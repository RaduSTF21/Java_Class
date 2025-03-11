/**
 * Represents a teacher who is a person that proposes projects.
 */
public class Teacher extends Person {
    private Project[] proposedProjects;

    /**
     * Constructs a Teacher with the given parameters.
     *
     * @param name             the name of the teacher
     * @param dateOfBirth      the date of birth of the teacher
     * @param proposedProjects an array of projects proposed by the teacher
     */
    public Teacher(String name, java.time.LocalDate dateOfBirth, Project[] proposedProjects) {
        super(name, dateOfBirth);
        this.proposedProjects = proposedProjects;
    }

    /**
     * Returns the array of projects proposed by the teacher.
     *
     * @return the proposed projects
     */
    public Project[] getProposedProjects() {
        return proposedProjects;
    }

    /**
     * Returns a string representation of the teacher.
     *
     * @return a string describing the teacher
     */
    @Override
    public String toString() {
        return "Teacher[" + super.toString() + "]";
    }

    /**
     * Checks if this teacher is equal to another object.
     * Two teachers are considered equal if they represent the same person.
     *
     * @param obj the object to compare with
     * @return {@code true} if the teachers are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
