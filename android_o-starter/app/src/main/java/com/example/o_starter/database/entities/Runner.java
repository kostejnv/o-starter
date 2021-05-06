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


@Entity(tableName = "runners",
        foreignKeys = {@ForeignKey(entity = Competition.class, parentColumns = "id", childColumns = "competition_id", onDelete = CASCADE)})
public class Runner {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String surname;
    @TypeConverters(DateToLongConverter.class)
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
    private boolean checked;
    @ColumnInfo(name = "registration_id")
    private String registrationId;


    public Runner(String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category, boolean checked, String registrationId) {
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
        this.checked = checked;
        this.registrationId = registrationId;
    }

    @Ignore
    public Runner(int id, String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category, boolean checked, String registrationId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
        this.checked = checked;
        this.registrationId = registrationId;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
