package net.hythe.projects.database.model;

import java.util.Date;

public class PlanningApplication implements JSONCodeable {
    private final String name;
    private final Date validDate;
    private final String address;
    private final String proposal;
    private final String type;
    private final String status;
    private final String ward;
    private final String parish;

    public PlanningApplication(String name, Date validDate, String address, String proposal, String type, String status, String ward, String parish) {
        this.name = name;
        this.validDate = validDate;
        this.address = address;
        this.proposal = proposal;
        this.type = type;
        this.status = status;
        this.ward = ward;
        this.parish = parish;
    }

    public String getName() {
        return name;
    }

    public Date getValidDate() {
        return validDate;
    }

    public String getAddress() {
        return address;
    }

    public String getProposal() {
        return proposal;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getWard() {
        return ward;
    }

    public String getParish() {
        return parish;
    }

    @Override
    public String toString() {
        return String.format("Planning application %1$s\n\tdate: %2$s\n\taddress: %3$s\n\tproposal: %4$s\n\ttype: %5$s\n\tstatus: %6$s\n\tward: %7$s\n\tparish: %8$s\n", name, validDate, address, proposal, type, status, ward, parish);
    }

    public String toJSON() {
        return String.format("{ name: \"%1$s\", address: \"%2$s\", type:\"%3$s\", status: \"%4$s\" }", name, address, type, status);
    }
}
