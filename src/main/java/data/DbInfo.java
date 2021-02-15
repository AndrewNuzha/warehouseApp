package data;

import data.entity.Location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement( name = "dbinfo" )
public class DbInfo {

    private List<Location> locations;

    @XmlElement(name = "location")
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void add(Location location) {
        if (this.locations == null) {
            this.locations = new ArrayList<Location>();
        }
        this.locations.add(location);
    }

}
