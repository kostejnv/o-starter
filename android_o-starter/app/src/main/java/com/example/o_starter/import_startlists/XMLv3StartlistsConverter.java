package com.example.o_starter.import_startlists;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.nfc.FormatException;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.example.o_starter.database.entities.Competition;
import com.example.o_starter.database.entities.Runner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.prefs.InvalidPreferencesFormatException;

import static androidx.core.app.ActivityCompat.requestPermissions;

/**
 * Class that convert startlist from XML IOF ver.3
 */
public class XMLv3StartlistsConverter implements StartlistsConverter {

    private final Uri fileUri;
    private final Context context;
    private Competition competition;
    private ArrayList<Runner> runners;

    //xml tags
    private static final String TAG = "XMLConverter";
    private static final String XML_STARTLIST = "StartList";
    private static final String XML_EVENT = "Event";
    private static final String XML_NAME = "Name";
    private static final String XML_CLASSSTART = "ClassStart";
    private static final String XML_CLASS = "Class";
    private static final String XML_SHORTNAME = "ShortName";
    private static final String XML_GIVEN = "Given";
    private static final String XML_FAMILY = "Family";
    private static final String XML_PERSONSTART = "PersonStart";
    private static final String XML_PERSON = "Person";
    private static final String XML_ORGANISATION = "Organisation";
    private static final String XML_START = "Start";
    private static final String XML_STARTTIME = "StartTime";
    private static final String XML_BIBNUMBER = "BibNumber";
    private static final String XML_CONTROLCARD = "ControlCard";
    private static final String XML_ID = "Id";


    public XMLv3StartlistsConverter(Uri fileUri, Context context) {
        this.fileUri = fileUri;
        this.context = context;
    }

    /**
     * Return Competition from XML if XML was proccess, otherwise proccess XML before it
     *
     * @throws FormatException if the XML does not have valid format
     */
    @Override
    public Competition getCompetition() throws FormatException {
        if (competition == null)
            try {
                processXML();
            } catch (IOException | ParseException | XmlPullParserException e) {
                throw new FormatException();
            }
        return competition;
    }

    /**
     * Return Runners given competition from XML if XML was proccess, otherwise proccess XML before it
     *
     * @throws FormatException if the XML does not have valid format
     */
    @Override
    public ArrayList<Runner> getRunners() throws FormatException {
        if (runners == null) {
            try {
                processXML();
            } catch (IOException | ParseException | XmlPullParserException e) {
                throw new FormatException();
            }
        }
        return runners;

    }

    /**
     * Self-documenting
     */
    private void processXML() throws IOException, ParseException, XmlPullParserException {

        competition = new Competition();
        runners = new ArrayList<Runner>();

        InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
        initXMLPullparser(inputStream);
        competition.SetInfoByRunners(runners);


    }

    /**
     * process head of XML a divide Event part and classstart part of XML to be proccess in smaller method
     * @param istream inputStream of XML
     */
    private void initXMLPullparser(InputStream istream) throws
            XmlPullParserException, IOException, ParseException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(istream, null);

        parser.next();

        parser.require(XmlPullParser.START_TAG, null, XML_STARTLIST);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();

            switch (tagName) {
                case (XML_EVENT):
                    parseEvent(parser);
                    break;
                case (XML_CLASSSTART):
                    parseClassStart(parser);
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }
    }

    /**
     * Process Event part of XML
     */
    private void parseEvent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, XML_EVENT);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals(XML_NAME)) {
                competition.setName(getContent(parser, XML_NAME));
            } else {
                skipTag(parser);
            }
        }
    }

    /**
     * proccess part of XML with runners from "Classstart" tag
     */
    private void parseClassStart(XmlPullParser parser) throws
            IOException, XmlPullParserException, ParseException {
        ArrayList<Runner> category_runner = new ArrayList<Runner>();

        parser.require(XmlPullParser.START_TAG, null, XML_CLASSSTART);
        String category = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();

            switch (tagName) {
                case (XML_CLASS):
                    category = getCatNameFromClass(parser);
                    break;
                case (XML_PERSONSTART):

                    Runner runner = getRunnerFromPersonStart(parser);
                    category_runner.add(runner);
                    break;
                default:
                    skipTag(parser);
                    break;
            }

        }
        for (Runner runner : category_runner) {
            runner.setCategory(category);
            runners.add(runner);
        }
    }

    /**
     * Parse category name from from "Class" tag
     */
    private String getCatNameFromClass(XmlPullParser parser) throws
            IOException, XmlPullParserException {
        String category = "";

        parser.require(XmlPullParser.START_TAG, null, XML_CLASS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals(XML_NAME)) {
                category = getContent(parser, XML_NAME);
            } else {
                skipTag(parser);
            }
        }
        return category;
    }

    /**
     * proccess part of XML with runner from "Personstart" tag
     */
    private Runner getRunnerFromPersonStart(XmlPullParser parser) throws
            IOException, XmlPullParserException, ParseException {
        Runner runner = new Runner();

        parser.require(XmlPullParser.START_TAG, null, XML_PERSONSTART);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();

            switch (tagName) {
                case (XML_PERSON):
                    addPersonInfoToRunner(runner, parser);
                    break;
                case (XML_ORGANISATION):
                    addOrganisationInfoToRunner(runner, parser);
                    break;
                case (XML_START):
                    addStartInfoToRunner(runner, parser);
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }

        return runner;
    }


    /**
     * proccess "Starttime" tag and add information to runner
     */
    private void addStartInfoToRunner(Runner runner, XmlPullParser parser) throws
            IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, null, XML_START);
        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals(XML_STARTTIME)) {
                String sdate = getContent(parser, XML_STARTTIME);
                @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(sdate);
                runner.setStartTime(date);

            } else if (parser.getName().equals(XML_CONTROLCARD)) {
                runner.setCardNumber(Integer.parseInt(getContent(parser, XML_CONTROLCARD)));

            } else if (parser.getName().equals(XML_BIBNUMBER)) {
                runner.setStartNumber(Integer.parseInt(getContent(parser, XML_BIBNUMBER)));

            } else {
                skipTag(parser);
            }
        }
    }

    /**
     * proccess "Organisation" -club- tag and add information to runner
     */
    private void addOrganisationInfoToRunner(Runner runner, XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, XML_ORGANISATION);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals(XML_SHORTNAME)) {
                runner.setClubShort(getContent(parser, XML_SHORTNAME));
            } else {
                skipTag(parser);
            }
        }
    }

    /**
     * proccess "Personstart" tag and add information to runner
     */
    private void addPersonInfoToRunner(Runner runner, XmlPullParser parser) throws
            IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, XML_PERSON);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }


            if (parser.getName().equals(XML_NAME)) {
                parser.require(XmlPullParser.START_TAG, null, XML_NAME);

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String tagName = parser.getName();
                    switch (tagName) {
                        case (XML_FAMILY):
                            runner.setSurname(getContent(parser, XML_FAMILY));
                            break;
                        case (XML_GIVEN):
                            runner.setName(getContent(parser, XML_GIVEN));
                            break;
                        default:
                            skipTag(parser);
                            break;
                    }
                }

            } else if (parser.getName().equals(XML_ID)) {
                runner.setRegistrationId(getContent(parser, XML_ID));
            } else {

                skipTag(parser);
            }
        }
    }


    /**
     * skip unimportant tag
     */
    private void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException("It is not start tag");
        }

        int index = 1;

        while (index != 0) {
            switch (parser.next()) {
                case XmlPullParser.START_TAG:
                    index++;
                    break;
                case XmlPullParser.END_TAG:
                    index--;
                default:
                    break;
            }
        }
    }

    /**
     * Extract string from next tag with tagName
     */
    private String getContent(XmlPullParser parser, String tagName) throws
            IOException, XmlPullParserException {
        String content = "";
        parser.require(XmlPullParser.START_TAG, null, tagName);

        if (parser.next() == XmlPullParser.TEXT) {
            content = parser.getText();
            parser.next();
        }

        return content;
    }
}
