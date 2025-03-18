import java.time.LocalTime;

public class Flight{
    private LocalTime start;
    private LocalTime end;

    public Flight(LocalTime start, LocalTime end){
        this.start = start;
        this.end = end;
    }
    public LocalTime getStart(){
        return this.start;
    }

    public LocalTime getEnd(){
        return this.end;
    }

    @Override
    public String toString(){
        return "Flight["+this.start+"-"+this.end+"]";
    }
}
