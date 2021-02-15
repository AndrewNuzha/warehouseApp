package data.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Objects;

@XmlType(propOrder={"name","id", "loads"})
public class Location {

    private Integer id;
    private String name;
    private List<Load> loads;

    public Location() {
    }

    public Location(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlElement(name="load")
    public List<Load> getLoads() {
        return loads;
    }

    public void setLoads(List<Load> loads) {
        this.loads = loads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id.equals(location.id) &&
                name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
