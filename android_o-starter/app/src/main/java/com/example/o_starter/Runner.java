package com.example.o_starter;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "runners")
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

    @Ignore
    public Runner(int id, String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
    }

    public Runner(String name, String surname, Date startTime, String clubShort, int cardNumber, int startNumber, int competitionId, String category) {
        this.name = name;
        this.surname = surname;
        this.startTime = startTime;
        this.clubShort = clubShort;
        this.cardNumber = cardNumber;
        this.startNumber = startNumber;
        this.competitionId = competitionId;
        this.category = category;
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
}
