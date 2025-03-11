public class Student{
    String name;
    String[] project_choices;
    Student(){
        System.out.println("Student created");
    }
    public String toString(){
        return name;
    }
    public String[] getProjectChoices(){
        return project_choices;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setProjectChoices(String[] projects){
        this.project_choices = projects;
    }

}
