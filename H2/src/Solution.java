/**
 * Demonstrates the Student Project Allocation Problem.
 * <p>
 * This class creates students, teachers, and projects, then performs the project allocation.
 * </p>
 */
public class Solution {

    /**
     * The main method to run the allocation demonstration.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Problem instance with capacity for students, teachers, and projects
        Problem problem = new Problem(10, 5, 10);

        // Create teachers with an array to hold their proposed projects
        Teacher teacher1 = new Teacher("Alice Smith", java.time.LocalDate.of(1975, 5, 20), new Project[5]);
        Teacher teacher2 = new Teacher("Bob Johnson", java.time.LocalDate.of(1980, 8, 15), new Project[5]);

        // Add teachers to the problem
        problem.addTeacher(teacher1);
        problem.addTeacher(teacher2);

        // Create projects proposed by teachers
        Project project1 = new Project("AI Research", teacher1);
        Project project2 = new Project("Machine Learning", teacher1);
        Project project3 = new Project("Data Mining", teacher2);
        Project project4 = new Project("Computer Vision", teacher2);

        // Add projects to the problem
        problem.addProject(project1);
        problem.addProject(project2);
        problem.addProject(project3);
        problem.addProject(project4);

        // Update teacher's proposed projects (using arrays, manually assign positions)
        teacher1.getProposedProjects()[0] = project1;
        teacher1.getProposedProjects()[1] = project2;
        teacher2.getProposedProjects()[0] = project3;
        teacher2.getProposedProjects()[1] = project4;

        // Create students with their acceptable projects
        Student student1 = new Student("Charlie Brown", java.time.LocalDate.of(2000, 2, 10), "S001", new Project[]{project1, project3});
        Student student2 = new Student("Daisy Miller", java.time.LocalDate.of(1999, 11, 30), "S002", new Project[]{project2, project4});
        Student student3 = new Student("Ethan Hunt", java.time.LocalDate.of(2001, 7, 25), "S003", new Project[]{project3, project1});
        Student student4 = new Student("Fiona Gallagher", java.time.LocalDate.of(2000, 3, 15), "S004", new Project[]{project4, project2});

        // Add students to the problem
        problem.addStudent(student1);
        problem.addStudent(student2);
        problem.addStudent(student3);
        problem.addStudent(student4);

        // Allocate projects to students using the greedy algorithm
        problem.allocateProjects();

        // Print allocation results
        System.out.println("Project Allocations:");
        Student[] students = {student1, student2, student3, student4};
        for (Student student : students) {
            Project allocated = student.getAllocatedProject();
            String projectName = (allocated != null) ? allocated.getProjectName() : "None";
            System.out.println(student.getName() + " (Reg: " + student.getRegistrationNumber() + ") -> " + projectName);
        }

        // Print all persons involved in the problem
        System.out.println("\nAll persons involved:");
        for (Person person : problem.getPersons()) {
            System.out.println(person);
        }
    }
}
