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
     *
     * @param name  : the name of the student
     * @param birthDate : the date of birt
     * @param registrationNumber : the registration number of the student
     * @param acceptableProjects : an array of acceptable projects for the student
     */
    public Student(String name, java.time.LocalDate birthDate, String registrationNumber, Project[] acceptableProjects){
        super(name, birthDate)
        this.registrationNumber = registrationNumber;
        this.acceptableProjects = acceptableProjects;
        this.allocatedProject = null;
    }
    /**
     * Returns the registration number of a student
     * @return the registration number
     */
    public String getRegistrationNumber(){
        return registrationNumber;
    }

    /**
     * Retruns the array of acceptable projects
     * @return the acceptable projects
     */
    public Project[] getAcceptableProjects() {
        return acceptableProjects;
    }

    /**
     * Returns the project allocated to the student
     * @return the allocated project, or {@code null} if none assigned
     */
    public Project getAllocatedProject() {
        return allocatedProject;
    }

    /**
     * Assigns a project to a student
     * @param project : the project to assign
     */
    public void assignProject(Project project)
    {
        this.allocatedProject = project;
    }

    /**
     * Returns a string representation of the student
     * @return a string describing the student
     */
    @Override
    public String toString() {
        String allocated = (allocatedProject != null) ? allocatedProject.getProjectName : "None";
    return "Student[" + super.toString() +", registration number= " + registrationNumber + ", allocated Project= " + allocated +"]";
    }
    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(obj instanceof Student){
            Student other = (Student) obj;
            return registrationNumber.equals(other.registrationNumber);
        }
        return false;
    }
}