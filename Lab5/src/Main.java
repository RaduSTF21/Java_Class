import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        Repository img = new Repository("img");
        LocalDate date = LocalDate.now();
        Image first = new Image("datsun", date, Collections.singletonList("#cars"), new File("/home/Stefan/Downloads/1963_chevrolet_corvette-coupe_1963-Corvette-Split-window-077-04568-scaled.jpg"));
        Image second = new Image("corvette", date,Collections.singletonList("#cars"), new File("/home/Stefan/Downloads/1963_chevrolet_corvette-coupe_1963-Corvette-Split-window-077-04568-scaled.jpg"));
        Image third = new Image("rarri", date,Collections.singletonList("#race-cars"), new File("/home/Stefan/Downloads/ferrari-499p-hypercar-diecast-model-bburago-18-16301-b.jpg"));

        img.addImage(first);
        img.addImage(second);
        img.addImage(third);

        Desktop desktop = Desktop.getDesktop();
        desktop.open(img.getPath(first));
    }
}

