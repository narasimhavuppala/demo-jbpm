package com.codegans.demo.jbpm.model;

/**
 * JavaDoc here
 *
 * @author Victor Polischuk
 * @since 17.10.2017 10:08
 */
public class Survey implements java.io.Serializable {
    private Boolean satisfied;
    private Boolean deliveredOnTime;
    private String missingEquipment;
    private String comment;

    public Survey() {
    }

    public Survey(Boolean satisfied,
                  Boolean deliveredOnTime,
                  String missingEquipment, String comment) {
        this.satisfied = satisfied;
        this.deliveredOnTime = deliveredOnTime;
        this.missingEquipment = missingEquipment;
        this.comment = comment;
    }

    public Boolean getSatisfied() {
        return this.satisfied;
    }

    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
    }

    public Boolean getDeliveredOnTime() {
        return this.deliveredOnTime;
    }

    public void setDeliveredOnTime(Boolean deliveredOnTime) {
        this.deliveredOnTime = deliveredOnTime;
    }

    public String getMissingEquipment() {
        return this.missingEquipment;
    }

    public void setMissingEquipment(String missingEquipment) {
        this.missingEquipment = missingEquipment;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}