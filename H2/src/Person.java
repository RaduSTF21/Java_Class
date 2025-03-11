import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a person with a name and a date of birth.
 * <p>
 * This is the base class for {@code Student} and {@code Teacher}.
 * </p>
 *
 * @author
 */
public class Person {
    private String name;
    private LocalDate dateOfBirth;

    /**
     * Constructs a Person with the given name and date of birth.
     *
     * @param name        the name of the person
     * @param dateOfBirth the date of birth of the person
     */
    public Person(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the date of birth of the person.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns a string representation of the person.
     *
     * @return a string describing the person
     */
    @Override
    public String toString() {
        return "Person[name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
    }

    /**
     * Checks if this person is equal to another object.
     * Two persons are considered equal if they have the same name and date of birth.
     *
     * @param obj the object to compare with
     * @return {@code true} if the persons are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;

        }
        Person other = (Person) obj;
        return name.equals(other.name) && dateOfBirth.equals(other.dateOfBirth);
    }

}

