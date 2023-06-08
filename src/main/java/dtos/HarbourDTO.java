package dtos;

import java.io.Serializable;
import java.util.Objects;

public class HarbourDTO implements Serializable {
    private final String name;
    private final String address;
    private final int capacity;

    public HarbourDTO(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarbourDTO entity = (HarbourDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.capacity, entity.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, capacity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "capacity = " + capacity + ")";
    }
}
