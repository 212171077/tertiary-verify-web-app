package com.pc.entities.enums;

public enum ApprovalStatusEnum {

    Approved("Approved"),
    Rejected("Rejected"),
    PendingApproval("Pending Approval"),
    Active("Active"),
    InActive("In-Active"),
    EmailNotConfirmed("Email not confirmed"),
    Expired("Expired"),
    EndDateExpired("End Date Expired");

    private String displayName;

    ApprovalStatusEnum(String displayNameX) {
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
