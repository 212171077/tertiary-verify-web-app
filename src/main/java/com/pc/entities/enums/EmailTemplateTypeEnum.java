package com.pc.entities.enums;

public enum EmailTemplateTypeEnum {

    Approval("Approval"),
    Reject("Reject"),
    FinalApproval("Final Approval"),
    FinalReject("Final Reject"),
    Acknowlegement("Acknowlegement");

    private String displayName;

    EmailTemplateTypeEnum(String displayNameX) {
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
