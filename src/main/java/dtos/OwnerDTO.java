package dtos;

import entities.Boat;
import entities.Owner;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OwnerDTO implements Serializable {
    private final String name;
    private final String address;
    private final int phone;
    private final List<BoatDTO> boats;

    public OwnerDTO(Owner o) {
        this.name = o.getName();
        this.address = o.getAddress();
        this.phone = o.getPhone();
        this.boats = o.getBoats().stream().map(b -> new BoatDTO(b)).collect(Collectors.toList());
    }

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDTO entity = (OwnerDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.phone, entity.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "phone = " + phone + ")";
    }
}
