package com.pc.entities.enums;

public enum DocConfigActionEnum {

    View("View"),
    Download("Download"),
    Upload("Upload");

    private String displayName;

    DocConfigActionEnum(String displayNameX) {
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
