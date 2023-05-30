package com.skillCentre.Exception;

public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String resourceField;
    private int resourceValue;

    public ResourceNotFoundException(String resourceName, String resourceField, int resourceValue) {
        super(resourceName + " with this " + resourceField + " of " + resourceValue + " is not found.");
        this.resourceName = resourceName;
        this.resourceField = resourceField;
        this.resourceValue = resourceValue;
    }

}
