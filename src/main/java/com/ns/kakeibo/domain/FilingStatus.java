package com.ns.kakeibo.domain;

public enum FilingStatus {
    single("Single"),
    marriedFilingSeparately("Married filing separately"),
    marriedFilingJointly("Married filing jointly");

    private String label;

    FilingStatus(String label) {
        this.label = label;
    }
    public static FilingStatus findByValue(String byLabel){
        for(FilingStatus f:  FilingStatus.values()) {
            if (f.label.equalsIgnoreCase(byLabel))
                return f;
        }
        return null;
    }
    public String getLabel() {
        return label;
    }
}
