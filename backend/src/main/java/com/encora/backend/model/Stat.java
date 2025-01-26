package com.encora.backend.model;

public class Stat {
    private String  allDoneStats;
    private String highDoneStats;
    private String mediumDoneStats;
    private String lowDoneStats;

    public Stat(String allDoneStats, String highDoneStats, String mediumDoneStats, String lowDoneStats) {
        this.allDoneStats = allDoneStats;
        this.highDoneStats = highDoneStats;
        this.mediumDoneStats = mediumDoneStats;
        this.lowDoneStats = lowDoneStats;
    }

    public String getAllDoneStats() {
        return allDoneStats;
    }

    public String getHighDoneStats() {
        return highDoneStats;
    }

    public String getMediumDoneStats() {
        return mediumDoneStats;
    }

    public String getLowDoneStats() {
        return lowDoneStats;
    }

    public void setHighDoneStats(String highDoneStats) {
        this.highDoneStats = highDoneStats;
    }

    public void setAllDoneStats(String allDoneStats) {
        this.allDoneStats = allDoneStats;
    }

    public void setMediumDoneStats(String mediumDoneStats) {
        this.mediumDoneStats = mediumDoneStats;
    }

    public void setLowDoneStats(String lowDoneStats) {
        this.lowDoneStats = lowDoneStats;
    }
}
