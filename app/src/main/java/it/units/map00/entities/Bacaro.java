package it.units.map00.entities;

public class Bacaro {

    private String address;
    private double averageRate;
    private String description;
    private double food;
    private String imageUrl;
    private double lat;
    private double lng;
    private double location;
    private String name;
    private double price;
    private double service;
    private String shortDescription;
    private double wine;

    public Bacaro(String address, double averageRate, String description, double food, String imageUrl, double lat, double lng, double location, String name, double price, double service, String shortDescription, double wine) {
        this.address = address;
        this.averageRate = averageRate;
        this.description = description;
        this.food = food;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
        this.name = name;
        this.price = price;
        this.service = service;
        this.shortDescription = shortDescription;
        this.wine = wine;
    }

    public Bacaro() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public double getWine() {
        return wine;
    }

    public void setWine(double wine) {
        this.wine = wine;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }
}
