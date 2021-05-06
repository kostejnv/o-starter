package com.example.o_starter.import_startlists;

import android.nfc.FormatException;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;

import java.util.ArrayList;

public interface StartlistsConverter {
    Competition getCompetition() throws FormatException;
    ArrayList<Runner> getRunners() throws FormatException;
}
