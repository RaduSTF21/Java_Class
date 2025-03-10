/**
 *  Represents a student with a registration number and a given project
 *  Each student can be assigned at most one project
 */
public class Student extends Person {
    private String registrationNumber;
    private Project[] acceptableProjects;
    private Project allocatedProject;

    /**
     *Constructs a student with the given parameters
     */
    public Student(String name, java.time.LocalDate birthDate, String registrationNumber, Project[] acceptableProjects){
        super(name, birthDate)
        this.registrationNumber = registrationNumber;
        this.acceptableProjects = acceptableProjects;
        this.allocatedProject = null;
    }
    /**
     * Returns the registration number of a student
     */
    public String getRegistrationNumber(){
        return registrationNumber;
    }

    /**
     * Retruns the array of acceptable projects
     */
    public Project[] getAcceptableProjects() {
        return acceptableProjects;
    }

    /**
     * Returns the project allocated to the student
     */
    public Project getAllocatedProject() {
        return allocatedProject;
    }

    /**
     * Assigns a project to a student
     */
    public void assignProject(Project project)
    {
        this.allocatedProject = project;
    }

    /**
     * Returns a string representation of the student
     */
    @Override
    public String toString(){
        String allocated = ()
    }
}