public class Freighter extends Aircraft implements CargoCapable{
    private int payload;

    public Freighter(String model, String name, double wingspan, int payload) {
        super(model, name, wingspan);
        this.payload = payload;
    }

    @Override
    public int getCargoCapacity() {
        return payload;
    }

    @Override
    public String toString() {
        return "Freighter: " + super.toString() + "  CargoCapacity: " + this.payload;
    }
}

