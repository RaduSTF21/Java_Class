import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Student[] students = new Student[4];
        Project[] projects = new Project[4];
        Random rand = new Random();
        for(int i = 0; i < 4; i++){
            students[i] = new Student();
            projects[i] = new Project();
            students[i].setName("Student " + i);
            projects[i].setName("Project " + i);
            students[i].setProjectChoices(new String[]{"Project " + rand.nextInt(4) , "Project " + rand.nextInt(4)});
            if(i%2 == 0)
            {
                projects[i].setType("practical");
            }
            else{
                projects[i].setType("theoretical");
            }
            students[i].getProjectChoices();
            projects[i].getType();
        }
    }

}
