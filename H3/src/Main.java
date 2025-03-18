import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Airport airport = new Airport(Arrays.asList("Runway1", "Runway2"));

        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(new Flight(LocalTime.of(8,0),LocalTime.of(8,30) ));
        flights.add(new Flight(LocalTime.of(8,15),LocalTime.of(8,45) ));
        flights.add(new Flight(LocalTime.of(8,30),LocalTime.of(9,0) ));
        flights.add(new Flight(LocalTime.of(9,0),LocalTime.of(9,30) ));

        Problem problem = new Problem(airport, flights);
        Map<Flight,String> assignment = problem.scheduleFlights();

        for(Map.Entry<Flight,String> entry: assignment.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}

