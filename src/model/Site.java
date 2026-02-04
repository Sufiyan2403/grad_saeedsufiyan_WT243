package model;

public class Site {
    private int siteId;
    private int siteNumber;
    private String siteType;
    private String dimensions;
    private int areaSqft;
    private String status;
    private Integer ownerId;
    private double maintenanceRate;
    private String ownerName; // For display purposes

    public Site() {
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public int getAreaSqft() {
        return areaSqft;
    }

    public void setAreaSqft(int areaSqft) {
        this.areaSqft = areaSqft;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public double getMaintenanceRate() {
        return maintenanceRate;
    }

    public void setMaintenanceRate(double maintenanceRate) {
        this.maintenanceRate = maintenanceRate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double calculateMaintenance() {
        return areaSqft * maintenanceRate;
    }
}
