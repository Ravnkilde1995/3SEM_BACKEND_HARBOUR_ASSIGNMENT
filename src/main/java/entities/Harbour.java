package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harbour")
public class Harbour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String address;
    private int capacity;
    @OneToMany(mappedBy = "harbour", cascade = CascadeType.PERSIST)
    private List<Boat> boats;


    public Harbour() {
    }

    public Harbour(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.boats = new ArrayList<>();
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void addBoats(Boat boat) {
        this.boats.add(boat);
        if (boat != null){
            boat.setHarbour(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}