package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import com.example.o_starter.database.Competition;
import com.example.o_starter.database.Runner;

import java.sql.Date;

@Entity(tableName = "changed_runners",
        primaryKeys = {"runnerId", "competitionId", "change"},
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runnerId"),
                        @ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competitionId")})
public class ChangedRunner {

    @ColumnInfo(name = "runner_id")
    private int runnerId;
    @ColumnInfo(name = "competition_id")
    private int competitionId;
    private int change;

    private String oldName;
    private String oldSurname;
    @ColumnInfo(name = "old_start_time")
    private Date oldStartTime;
    @ColumnInfo(name = "old_club_short")
    private String oldClubShort;
    @ColumnInfo(name = "old_card_number")
    private int oldCardNumber;
    @ColumnInfo(name = "old_start_number")
    private int oldStartNumber;
    @ColumnInfo(name = "old_competition_id")
    private int oldCompetitionId;
    private String category;

    public ChangedRunner(int runnerId, int competitionId, int change, String oldName, String oldSurname, Date oldStartTime, String oldClubShort, int oldCardNumber, int oldStartNumber, int oldCompetitionId, String category) {
        this.runnerId = runnerId;
        this.competitionId = competitionId;
        this.change = change;
        this.oldName = oldName;
        this.oldSurname = oldSurname;
        this.oldStartTime = oldStartTime;
        this.oldClubShort = oldClubShort;
        this.oldCardNumber = oldCardNumber;
        this.oldStartNumber = oldStartNumber;
        this.oldCompetitionId = oldCompetitionId;
        this.category = category;
    }

    @Ignore
    public ChangedRunner() {
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldSurname() {
        return oldSurname;
    }

    public void setOldSurname(String oldSurname) {
        this.oldSurname = oldSurname;
    }

    public Date getOldStartTime() {
        return oldStartTime;
    }

    public void setOldStartTime(Date oldStartTime) {
        this.oldStartTime = oldStartTime;
    }

    public String getOldClubShort() {
        return oldClubShort;
    }

    public void setOldClubShort(String oldClubShort) {
        this.oldClubShort = oldClubShort;
    }

    public int getOldCardNumber() {
        return oldCardNumber;
    }

    public void setOldCardNumber(int oldCardNumber) {
        this.oldCardNumber = oldCardNumber;
    }

    public int getOldStartNumber() {
        return oldStartNumber;
    }

    public void setOldStartNumber(int oldStartNumber) {
        this.oldStartNumber = oldStartNumber;
    }

    public int getOldCompetitionId() {
        return oldCompetitionId;
    }

    public void setOldCompetitionId(int oldCompetitionId) {
        this.oldCompetitionId = oldCompetitionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
