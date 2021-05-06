package com.example.o_starter.import_startlists;

import android.Manifest;
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

public class XMLv3StartlistsConverter implements StartlistsConverter {

    private Uri fileUri;
    private Context context;
    private Competition competition;
    private ArrayList<Runner> runners;

    //xml tags
    public static final String TAG = "XMLConverter";
    public static final String XML_STARTLIST = "StartList";
    public static final String XML_EVENT = "Event";
    public static final String XML_NAME= "Name";
    public static final String XML_CLASSSTART= "ClassStart";
    public static final String XML_CLASS= "Class";
    public static final String XML_SHORTNAME= "ShortName";
    public static final String XML_GIVEN= "Given";
    public static final String XML_FAMILY= "Family";
    public static final String XML_PERSONSTART="PersonStart";
    public static final String XML_PERSON="Person";
    public static final String XML_ORGANISATION="Organisation";
    public static final String XML_START="Start";
    public static final String XML_STARTTIME="StartTime";
    public static final String XML_BIBNUMBER="BibNumber";
    public static final String XML_CONTROLCARD="ControlCard";



    public XMLv3StartlistsConverter(Uri fileUri, Context context) {
        this.fileUri = fileUri;
        this.context = context;
    }

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

        private void processXML() throws IOException, ParseException, XmlPullParserException {

            competition = new Competition();
            runners = new ArrayList<Runner>();

            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            initXMLPullparser(inputStream);


        }

        private void initXMLPullparser (InputStream istream) throws
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

        private void parseEvent (XmlPullParser parser) throws IOException, XmlPullParserException {
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

        private void parseClassStart (XmlPullParser parser) throws
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

        private String getCatNameFromClass (XmlPullParser parser) throws
        IOException, XmlPullParserException {
            String category = "";

            parser.require(XmlPullParser.START_TAG, null, XML_CLASS);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                if (parser.getName().equals(XML_SHORTNAME)) {
                    category = getContent(parser, XML_SHORTNAME);
                } else {
                    skipTag(parser);
                }
            }
            return category;
        }

        private Runner getRunnerFromPersonStart (XmlPullParser parser) throws
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

        private void addStartInfoToRunner (Runner runner, XmlPullParser parser) throws
        IOException, XmlPullParserException, ParseException {
            parser.require(XmlPullParser.START_TAG, null, XML_START);
            while (parser.next() != XmlPullParser.END_TAG) {

                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                if (parser.getName().equals(XML_STARTTIME)) {
                    String sdate = getContent(parser, XML_STARTTIME);
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS").parse(sdate);
                    runner.setStartTime(date);

                } else if (parser.getName().equals(XML_CONTROLCARD)) {
                    runner.setCardNumber(Integer.parseInt(getContent(parser, XML_CONTROLCARD)));

                } else if (parser.getName().equals(XML_BIBNUMBER)) {
                    runner.setCardNumber(Integer.parseInt(getContent(parser, XML_BIBNUMBER)));

                } else {
                    skipTag(parser);
                }
            }
        }

        private void addOrganisationInfoToRunner (Runner runner, XmlPullParser parser) throws
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

        private void addPersonInfoToRunner (Runner runner, XmlPullParser parser) throws
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

                } else {
                    skipTag(parser);
                }
            }
        }


        private void skipTag (XmlPullParser parser) throws XmlPullParserException, IOException {
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

        private String getContent (XmlPullParser parser, String tagName) throws
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
