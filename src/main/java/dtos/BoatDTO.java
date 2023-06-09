package dtos;

import entities.Boat;
import entities.Owner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BoatDTO implements Serializable {
    private long id;
    private final String brand;
    private final String make;
    private final String name;
    private final String image;
    private final List<String> owners;


    public BoatDTO(Boat b) {
        this.id = b.getId();
        this.brand = b.getBrand();
        this.make = b.getMake();
        this.name = b.getName();
        this.image = b.getImage();
        this.owners = b.getOwners().stream().map(o -> o.getName()).collect(Collectors.toList());
    }

    public static List<BoatDTO> getDtos(List<Boat> boats){
        List<BoatDTO> boatDTOS = new ArrayList();
        boats.forEach(boat->boatDTOS.add(new BoatDTO(boat)));
        return boatDTOS;
    }

    public List<String> getOwners() {
        return owners;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDTO entity = (BoatDTO) o;
        return Objects.equals(this.brand, entity.brand) &&
                Objects.equals(this.make, entity.make) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.image, entity.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, make, name, image);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "brand = " + brand + ", " +
                "make = " + make + ", " +
                "name = " + name + ", " +
                "image = " + image + ")";
    }
}
