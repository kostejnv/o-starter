package com.example.o_starter.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.o_starter.database.converters.DateToLongConverter;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Entity for table :changed_runners" in database.
 *
 * It contains information about old record of runner and his/her ID
 */
@Entity(tableName = "changed_runners",
        foreignKeys = {@ForeignKey(entity = Runner.class, parentColumns = "id", childColumns = "runner_id", onDelete = CASCADE),
                        @ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competition_id", onDelete = CASCADE)})
public class ChangedRunner {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "runner_id", index = true)
    private int runnerId;
    @ColumnInfo(name = "competition_id", index = true)
    private int competitionId;

    private String oldName;
    private String oldSurname;
    @ColumnInfo(name = "old_start_time")
    @TypeConverters(DateToLongConverter.class)
    private Date oldStartTime;
    @ColumnInfo(name = "old_club_short")
    private String oldClubShort;
    @ColumnInfo(name = "old_card_number")
    private int oldCardNumber;
    @ColumnInfo(name = "old_start_number")
    private int oldStartNumber;
    @ColumnInfo(name = "old_registration_id")
    private String oldRegistrationId;
    @ColumnInfo(name = "old_category")
    private String oldCategory;

    public ChangedRunner(int runnerId, int competitionId, String oldName, String oldSurname, Date oldStartTime, String oldClubShort, int oldCardNumber, int oldStartNumber, String oldRegistrationId, String oldCategory) {
        this.runnerId = runnerId;
        this.competitionId = competitionId;
        this.oldName = oldName;
        this.oldSurname = oldSurname;
        this.oldStartTime = oldStartTime;
        this.oldClubShort = oldClubShort;
        this.oldCardNumber = oldCardNumber;
        this.oldStartNumber = oldStartNumber;
        this.oldRegistrationId = oldRegistrationId;
        this.oldCategory = oldCategory;
    }

    @Ignore
    public ChangedRunner(Runner runner){
        this.runnerId = runner.getId();
        this.competitionId = runner.getCompetitionId();
        this.oldName = runner.getName();
        this.oldSurname = runner.getSurname();
        this.oldStartTime = runner.getStartTime();
        this.oldClubShort = runner.getClubShort();
        this.oldCardNumber = runner.getCardNumber();
        this.oldStartNumber = runner.getStartNumber();
        this.oldRegistrationId = runner.getRegistrationId();
        this.oldCategory = runner.getCategory();
    }

    @Ignore
    public ChangedRunner() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getOldCategory() {
        return oldCategory;
    }

    public void setOldCategory(String oldCategory) {
        this.oldCategory = oldCategory;
    }

    public String getOldRegistrationId() {
        return oldRegistrationId;
    }

    public void setOldRegistrationId(String oldRegistrationId) {
        this.oldRegistrationId = oldRegistrationId;
    }
}
