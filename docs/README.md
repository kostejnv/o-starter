# O-starter Documentation

## Main part

### Purpose of project

There is a interval start in orienteering and 3-7 runners starts each minutes. It is nessesary check the data (card number, start number, time of her/his start,...) of each runner. Till now, the organizators print papper startlist and work with it. The changes in runner data can be given to IT centre after start of all runners that is unefficient. This application should improve this proccess with the help of technology. The goal is an expansion of the application to all czech orienteering events,

### Parts of application

The application consists of 2 parts:

+ Android Application
+ Web Server

**Android application** is used for communication with users on startplace. Users can import startlist, checking which runner are on startplace, editting them, and sending important data to Web server. **Web server** is a database in which the Android application can sent data and Web server shows them at the arena of events. Both parts will be described in details further.

### Communication of Server and Android application and Security

Web server and Android application communicate through HTTPS protocol with each other. The communication is so encrypted with SSL. The main issue was to provide that the data can be sent only from the Android application to server and not from other sources (hackers). The best solution would be to require login (authorisation) for each user and manage her/his permission. On the other hand, the simplicity of application is one of the most important pillar of project. Therefore, I chose a compromise, there is hard-coded a server key in all application (for all same) and comminucation is possible only with the key. This solution stops the random attacks on web api that I expected at most. However, the security is not complete - someone can read the structure of application and get the key - but this types of attacks have to be focused straight on this application because the data is not so sensitive (almost all are avaible on the internet), I do not expect that thease attacks will be caried out.

## Web Server

The Web server is created with Django which is high-level Python framework. It is a simply SQL database that receive data about runner changes and unstarted runners from Android application and display it in easy html form for end-user.

### Structure

The structure of Web server seems like following one:

```
.
├── manage.py
├── o_starter
│   ├── asgi.py
│   ├── __init__.py
│   ├── settings.py
│   ├── urls.py
│   └── wsgi.py
└── startlists
    ├── admin.py
    ├── apps.py
    ├── __init__.py
    ├── model_data_preprocessing.py
    ├── models.py
    ├── templates
    │   └── startlists
    │       ├── view_changes.html
    │       └── view_unstarted.html
    ├── urls.py
    ├── variables.py
    └── views.py
```

A lot of files is automatic generated with Django. Let's deal with some of them only.

Every Django project consists of project folder ("o_starter") and folders of its applications ("startlists").

Project folder consists of settings of server - *settings.py* (language, time-zone, type of connections, installed utillities,...) and script *urls.py* which redirects by first part of URL on next applications, in this case only on *startlists*.

Application folder *startlists* contains main server source code:

+ **urls.py** - contains method that redirect request to specific view function in *view.py*
+ **view.py** - contains methods that proccess specific url requests
+ **variables.py** - contains server key for communication with Android application
+ **models.py** - contains database entities
+ **model_data_preprocessing.py** - contains method that preproccess data from HTTPS request and store them into database
+ **templates** - contains HTML templates for displaying information about event

For more information about Web Server, you can visit detailed documentation of O-starter Django project.

## Android Application

The Android application part is created in Android Studio and written in Java. The application should serves organizators of orienteering event at start-place. Its goal is displaying and editing startlists in easy way in difficult conditions (sunshine, rain, snow, ...) and sending data about changes into server. The emphasis is placed on simplicity clarity of application.

### Structure of application

The source code contains following files and folder:

+ **AndroidManifest.xml** - main configuration of application
+ **java** - java source code
+ **res** - resources of application (layouts, icons, extracted strings, fonts, colors, themes, ...)

### "java" folder

The Java package of application has following structure

```
java/com/example/o_starter/
├── activities
│   ├── MainActivity.java
│   ├── SettingsStartlistActivity.java
│   ├── StartlistViewActivity.java
│   ├── ui
│   │   └── view_changes
│   │       ├── ChangesFragment.java
│   │       ├── UnstartedFragment.java
│   │       ├── UnstartedNotFinishedFragment.java
│   │       └── ViewChangesPagerAdapter.java
│   └── ViewChangesActivity.java
├── adapters
│   ├── ChangesRecViewAdapter.java
│   ├── CompetitionsRecViewAdapter.java
│   ├── MinutesRecViewAdapter.java
│   ├── RunnerRecViewAdapter.java
│   └── UnstartedRunnersRecViewAdapter.java
├── database
│   ├── converters
│   │   ├── DateToLongConverter.java
│   │   ├── ListDateToStringConverter.java
│   │   └── ListStringToJsonConverter.java
│   ├── dao
│   │   ├── ChangedRunnerDao.java
│   │   ├── CompetitionDao.java
│   │   ├── RunnerDao.java
│   │   ├── UnsentChangedDao.java
│   │   └── UnsentUnstartedDao.java
│   ├── entities
│   │   ├── abstracts
│   │   │   ├── AbstractCategoriesToShow.java
│   │   │   └── AbstractMinutesWithRunner.java
│   │   ├── ChangedRunner.java
│   │   ├── Competition.java
│   │   ├── CompetitionSettings.java
│   │   ├── Runner.java
│   │   ├── UnsentChange.java
│   │   └── UnsentUnstertedRunner.java
│   └── StartlistsDatabase.java
├── DatabaseUpdateListener.java
├── dialogs
│   ├── ChangeRunnerDialog.java
│   └── NewCompetitionDialog.java
├── EnviromentVariables.java
├── import_startlists
│   ├── StartlistsConverter.java
│   └── XMLv3StartlistsConverter.java
├── server_communication
│   ├── entities
│   │   ├── ChangeToServer.java
│   │   ├── CompetitionFromServer.java
│   │   ├── CompetitionToServer.java
│   │   ├── DataToServer.java
│   │   └── UnstartedToServer.java
│   ├── ServerCommunicator.java
│   └── URLs.java
└── startlist_settings
    ├── SettingsStartlistFragment.java
    └── StartlistsSettingsDataStore.java
```

There is a basic description of structure:

+ **activitites** - source code of used activities
+ **adapters** - source code of custom recycler view adapters used in application
+ **database** - source files defining database (entitties, daos, converters, ...)
+ **dialogs** - dialogs used in activities
+ **EnviromentalVariables.java** - variables for specific instance of application
+ **import_startlists** - import startlists code (converting from input formats)
+ **server_communication** - source code of server communication (sending formats, server connection, URLs of server)
+ **startlist_settings** - source code for configuration of event

For more information about classes, interfaces and methods, see javadoc documentation.

### Discusion about concepts of application

**Import of startlist**

I created intarface *StartlistsConverter* for importing startlists from variate formats. All systems work with format IOF XML v.3 that was developed by International Orienteering Association but I count with possibility that there will be other formats in the future.

For format IOF XML v.3, I used *XmlPullParser* from package *org.xmlpull.v1*. After long research, I chose the parser because it should be the fastest way how to parse only part of the startlists. there are also disadvatages, for example not sou structured and cler code.

**Startlist view**


**Updating UI after changes in database**

There are often situations in the application when the user change data in database (settings, edit runner). The changes can change content of previous (parent) activity - for example content of recycler view. I created *DatabaseUpdateListener* for the purpose. Every Activity (and Fragment) which has components with data from database implements the method *OnDBUpdate()* in which update the components that could change. The method is called when the database is changed.

This way can sometimes lead to updating more components than it is needed but the data is not so big and this concept is therefore sufficient.

**Server communication**



## Possible application improvements

Of course, there is a place for improvements. In the future, I would like to add extensions as finding runner by name in startlist view, button for scrolling on actual minutes in startlists view and possibility to manage event on more than one device.



