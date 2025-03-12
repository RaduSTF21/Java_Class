
public class Main {
    public static void main(String[] args) {

        Airliner airliner1 = new Airliner("Boeing 737", "N737AA",35.8 , 160);
        Freighter freighter1 = new Freighter("Boeing 747-8F", "N747FX", 68.4, 137000);
        Drone drone1 = new Drone("DJI Matrice 300", "DRN300", 45, 15, 30);


        CargoCapable[] cargoAircraft = new CargoCapable[] { freighter1, drone1 };


        System.out.println("Aircraft that can transport goods:");
        for (CargoCapable cargo : cargoAircraft) {
            System.out.println(cargo.toString());
        }
    }
}
