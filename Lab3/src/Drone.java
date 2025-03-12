public class Drone extends Aircraft implements CargoCapable {
    private int batteryCapacity;
    private int payload;

    public Drone(String model, String name, double wingSpan, int batteryCapacity, int payload) {
        super(model, name, wingSpan);
        this.batteryCapacity = batteryCapacity;
        this.payload = payload;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    @Override
    public String toString() {
        return "Drone: " + super.toString() + "BatteryCapacity: " + this.batteryCapacity;
    }

    @Override
    public int getCargoCapacity() {
        return this.payload;
    }
}

