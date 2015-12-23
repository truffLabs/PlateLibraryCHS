package com.trufflabs.troy.platelibrary;

/**
 * Data structure to hold a drivers info.
 * Created by Troy on 12/23/15.
 */
public class Driver {
    /**
     * Only two fields currently, want to expand to more.
     */
    private String name;
    private String licencePlate;
    //TODO : implement other data about driver
//    private String makeModel;
//    private int year;
//    private String[] schedule;

    /**
     * Creates a Driver object
     * @param theLicence licence plate of the driver
     * @param theName name of the driver
     */
    public Driver(String theLicence, String theName){
        name = theName;
        licencePlate = theLicence;
    }

    /**
     * Gets the plate number for this driver
     * @return plate number
     */
    public String getLicencePlate() {
        return licencePlate;
    }

    /**
     * Gets the name for this driver
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * A string of information about the driver
     * @return info about this driver.
     */
    public String toString() {
        return licencePlate + " - " + name;
    }


}
