import java.util.List;

public class Airport{
    private List<String> runways;

    public Airport(List<String> runways){
        this.runways = runways;
    }

    public List<String> getRunways(){
        return this.runways;
    }
}

