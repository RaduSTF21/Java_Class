public class Project
{
    String name;
    enum Type{
        PRACTICAL,
        THEORETICAL
    }
    String project_type;
    boolean is_occupied;
    String assigned_to;
    Project(){
        System.out.println("Project created");
        is_occupied = false;
    }
    public String toString(){
        return name;
    }
    public void getName(){
        System.out.println(name);
    }

    public Type setType(String type){
        if(type.equals("practical")){
            this.project_type = "practical";
            return Type.PRACTICAL;
        }
        else if(type.equals("theoretical")){
            this.project_type = "theoretical";
            return Type.THEORETICAL;
        }
        else return null;
    }
    public void getType(){
        String type = this.project_type;
        System.out.println(type + "\n");
    }
    public void setName(String name){
        this.name = name;
    }
    public void setOccupied(String name){
        this.is_occupied = true;
        this.assigned_to = name;
    }
    public void getOccupied(){
        if(is_occupied){
            System.out.println("Project is occupied by " + assigned_to);
        }
        else{
            System.out.println("Project is free");
        }
    }
}