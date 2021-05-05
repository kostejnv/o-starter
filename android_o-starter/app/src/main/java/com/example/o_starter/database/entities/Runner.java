package com.example.o_starter.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.o_starter.database.Competition;

import java.sql.Date;

@Entity(tableName = "runners",
        foreignKeys = {@ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competitionId")})
public class Runner {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String surname;
    @ColumnInfo(name = "start_time")
    private Date startTime;
    @ColumnInfo(name = "club_short")
    private String clubShort;
    @ColumnInfo(name = "card_number")
    private int cardNumber;
    @ColumnInfo(name = "start_number")
    private int startNumber;
    @ColumnInfo(name = "competition_id")
    private int competitionId;
    private String category;
    private boolean shown;


    public Runner(String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category, boolean shown) {
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
        this.shown = shown;
    }

    @Ignore
    public Runner(int id, String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category, boolean shown) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
        this.shown = shown;
    }

    @Ignore
    public Runner() {  }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getClubShort() {
        return clubShort;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setClubShort(String clubShort) {
        this.clubShort = clubShort;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }
}
