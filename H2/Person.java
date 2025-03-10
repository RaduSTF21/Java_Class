import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represents a person with name and birth date
 * Also it is the base class for {@code Student} and {@code Teacher}
 * */
public class Person {
    private String name;
    private LocalDate birthDate;

    /**
    * Constructs a Person with the given name and date of birth.
     * @param name : the name of the person
     * @param birthDate : the date of birth of the person
     */
    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
    /**
     * Returns the name of the person
     * @return the name
     */

    public String getName() {
        return name;
    }
    /**
     * Returns the date of birth of the person
     *
     * @return the date of birth
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Returns a string representation of the person
     * @return a string describing a person
     */
    @Override
    public String toString(){
        return "Person: " + name + ", born on " + birthDate + ".";
    }
    /**
     * Checks if two persons are the same.
     * Two persnons are equal if they have the same name and birth date
     *
     * @param obj the object to compare with
     * @return {@code true} if the persons are equal; {@code false} otherwise
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return name.equals(other.name) && birthDate.equals(other.birthDate);
    }

}

