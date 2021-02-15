package data;

public class LocationDTO {

    private String name;
    private int loadsCount;

    public LocationDTO(String name, int loadsCount) {
        this.name = name;
        this.loadsCount = loadsCount;
    }

    public String getName() {
        return name;
    }

    public int getLoadsCount() {
        return loadsCount;
    }
}
