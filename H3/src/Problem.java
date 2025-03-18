import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem
{
    private Airport airport;
    private List<Flight> flights;

    public  Problem(Airport airport,  List<Flight> flights)
    {
        this.airport = airport;
        this.flights = flights;
    }

    public Map<Flight, String> scheduleFlights(){
        Map<Flight,String> assignment = new HashMap<>();

        Map<String, LocalTime> runwayAvailability = new HashMap<>();
        for(String runway : this.airport.getRunways()){
            runwayAvailability.put(runway, LocalTime.MIN);
        }

        flights.sort(Comparator.comparing(Flight::getStart));
        for(Flight flight : flights) {
            boolean assigned = false;
            for (String runway : airport.getRunways()) {
                if (!flight.getStart().isBefore(runwayAvailability.get(runway))) {
                    assignment.put(flight, runway);
                    runwayAvailability.put(runway, flight.getEnd());
                    assigned = true;
                    break;
                }
            }
            if(!assigned){
                System.out.println("Could not schedule flight: " + flight);
            }
        }
        return assignment;

    }
}
