package data.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"name","id"})
public class Load {

    private Integer id;
    private String name;
    private Integer locationId;

    public Load() {
    }

    public Load(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    //    @XmlAttribute
//    public String getName() {
//        return name;
//    }

}
