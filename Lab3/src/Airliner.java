public class Airliner extends Aircraft implements PassengerCapable{
    private int passengerCapacity;

    public Airliner(String model, String name, double wingSpan,int passengerCapacity) {
        super(model, name, wingSpan);
        this.passengerCapacity = passengerCapacity;
    }
    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public String toString() {
        return "Airliner: " + super.toString() + "  PassengerCapacity: " + this.passengerCapacity;
    }
}

