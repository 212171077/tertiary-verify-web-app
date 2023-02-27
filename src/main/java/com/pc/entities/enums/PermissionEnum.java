package com.pc.entities.enums;

public enum PermissionEnum {

    Approved("Approved"),
    Edit("Edit"),
    Upload("Upload"),
    EditAndUpload("Edit And Upload"),
    View("View");

    private String displayName;

    PermissionEnum(String displayNameX) {
        displayName = displayNameX;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return displayName;
    }

    public String getFriendlyName() {
        return toString();
    }
}
