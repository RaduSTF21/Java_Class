/**
 * Represents the student project allocation proble,.
 *<p>
 *     It maintains arrays of students, teachers and projects, and provides methods
 *     to add participants and projects, retrieve all persons involved and allocate projects to
 *     students using a simple greedy algorithm
 *</p>
 */
public class Problem {
    private Student[] students;
    private int studentCount;
    private Teacher[] teachers;
    private int teacherCount;
    private Project[] projects;
    private int projectCount;

    /**
     * Constructs a Problem instance with specified maximum capacities
     *
     * @param maxStudents : the maximum number of students
     * @param maxTeachers : the maximum number of teachers
     * @param maxProjects : the maximum number of projects
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
     * Adds a student to the problem if not already present
     *
     * @param student : the student to add
     * @return {@code true} if the student was added; {@code false} if duplicate or no space available
     */
    public boolean addStudent(Student student){
        //Check for duplicates
        for(int i=0; i<studentsCount;i++)
        {
            if(students[i].equals(student))
            {
                return false;
            }
        }
        if(studentCount >= students.length)
            return false;
        students[studentCount] = student;
        studentCount++;
        return true;
    }

    /**
     * Adds a teacher to the problem if not already present
     *
     * @param teacher : the teacher that has to be added
     * @return {@code true} if the teacher was added; {@code false} if duplicate or no space available
     */
    public boolean addTeacher(Teacher teacher)
    {
        //Check for duplicates
        for(int i = 0; i < teacherCount;i++)
        {
            if(teachers[i].equals(teacher))
            {
                return false;
            }
        }
        if(teacherCount >= teachers.length)
            return false;
        teachers[teacherCount] = teacher;
        teacherCount++;
        return true;
    }
    /**
     * Adds a project to the problem if not already added
     *
     * @param project : the project to be added
     * @return {@code true} if the project is added; {@code false} if duplicate or no space available
     */

    public boolean addProject(Project project)
    {
        for(int i = 0; i < projectCount; i++)
        {
            if(projects[i].equals(project))
            {
                return false;
            }
        }
        if(projectCount >= projects.length)
            return false;
        projects[projectCount] = project;
        projectCount++;
        return true;
    }

    /**
     * Returns an array of all the persons involved in the problem (students and teachers)
     *
     * @return an array of {@code Person} objects
     */

    public Person[] getPersons()
    {
        Person[] persons = new Person[studentCount+teacherCount];
        int index = 0;
        for(int i = 0; i<studentCount; i++)
        {
            persons[index++] = students[i];
        }
        for(int i = 0;i< teacherCount;i++)
        {
            persons[index++] = teachers[i];
        }
        return persons;
    }

    /**
     * Allocates projects to students using a simple greedy algorithm
     * <p>
     *     For each student the algorithm assigns the first acceptable project that is available.
     * </p>
     */

    public void allocateProjects()
    {
        for(int i = 0; i<studentCount; i++)
        {
            Student student = students[i];
            //Skip if the student already has a project
            if(student.getAllocatedProject() != null)
            {
                continue;
            }
            Project[] acceptable = student.getAcceptableProblems(;
            if(acceptable == null){
                continue;
            }
            //For each acceptable project, assign it to the student if it is available
            for(int j = 0; j< acceptable.length; j++){
                Project project = acceptable[j];
                if(isProjectAvailable(project)){
                    student.assignProject(project);
                    project.assignStudent(student);
                    break;
                }
            }
        }
    }

    /**
     * Checks if a given project is available for allocation
     * <p>
     *     A projectis available if it is in the problem's project list and has not been assigned yet
     * </p>
     *
     * @param project : the project to check
     * @return {@code true} if the project is available; {@code false} otherwise
     */

    private boolean isProjectAvailable(Project project)
    {
        for(int i = 0; i<projectCount;i++)
        {
            if(projects[i].equals(project))
            {
                return projects[i].assignedStudent()==null;
            }
        }
        return false;
    }

}