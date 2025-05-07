package org.example;


public class Country extends Entity {
    private String code;
    private Integer continentId;

    public Country() {
        super();
    }

    public Country(Integer id, String name, String code, Integer continentId) {
        super(id, name);
        this.code = code;
        this.continentId = continentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getContinentId() {
        return continentId;
    }

    public void setContinentId(Integer continentId) {
        this.continentId = continentId;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", continentId=" + continentId +
                '}';
    }
}