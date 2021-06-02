# O-starter Documentation

## Main part

### Purpose of project

There is an interval start with 3-7 runners in every minutes at orienteering event. It is necessary to check the data (card number, start number, time of her/his start,...) of each runner. Till now, the organizers printed paper startlist and worked with it. The changes in runner data were given to the IT center after start of all runners. This process is highly inefficient. This application should improve this process with the help of technology. The goal is an expansion of the application to all Czech orienteering events.

### Parts of application

The application consists of 2 parts:

+ Android Application
+ Web Server

**Android application** is used for communication with users on the start point. Users can import startlist, check which runners are on the start point, edit them, and send important data to the Web server. **Web server** is a database which the Android application can send data in and the Web server shows them at the arena of event. Both parts will be described in details further.

### Communication of Server and Android application and Security

Web server and Android application communicate through HTTPS protocol with each other. The communication is so encrypted with SSL. The main issue was to provide that the data can be sent only from the Android application to server and not from other sources (hackers). The best solution would be to require login (authorization) for each user and manage her/his permission. On the other hand, the simplicity of application is one of the most important pillar of the project. Therefore, I chose a compromise. There is a hard-coded server key in all application (for all same key) and communication is possible only with the key. This solution stops the random attacks on web API that I expected as the most common attacks. However, the security is not complete - someone can read the structure of application and get the key - but this types of attacks have to be focused straight on this application and because the data is not so sensitive (almost all of them are available on the internet), I do not expect that these attacks will happen.

## Web Server

The Web server is created with Django which is high-level Python framework. It is a simply SQL database that receives data about runner changes and unstarted runners from Android application and displays it in easy HTML form for end-user.

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

A lot of files is generated with Django automatically. Let's deal with some of them only.

Every Django project consists of project folder ("o_starter") and folders of its applications ("startlists").

Project folder consists of settings of server - *settings.py* (language, time-zone, type of connection, installed utilities,...) and script *urls.py* which redirects by first part of URL on next applications, in this case only on single application *startlists*.

Application folder *startlists* contains main source code of server:

+ **urls.py** - contains method that redirect request to specific view function in *view.py*
+ **view.py** - contains methods that process specific URL requests
+ **variables.py** - contains server key for communication with Android application
+ **models.py** - contains database entities
+ **model_data_preprocessing.py** - contains method that preprocess data from HTTPS request and store them into database
+ **templates** - contains HTML templates for displaying information about event

For more information about Web Server, you can visit [detailed documentation](https://kostejnv.github.io/o-starter/server_docs/ "Server Documentation") of O-starter Django project.

## Android Application

The Android application part is created in Android Studio and written in Java. The application should serves for organizers of orienteering event at start-place. Its goal is displaying and editing startlists in easy way in difficult conditions (sunshine, rain, snow, ...) and sending data about changes into server. The emphasis is placed on simplicity and clarity of application.

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

+ **activities** - source code of used activities
+ **adapters** - source code of custom recycler view adapters used in application
+ **database** - source files defining database (entities, DAOs, converters, ...)
+ **dialogs** - dialogs used in activities
+ **EnviromentalVariables.java** - variables for specific instance of application
+ **import_startlists** - importing startlists files (converting from input formats)
+ **server_communication** - source code of server communication (sending formats, server connection, URLs of server)
+ **startlist_settings** - source code for configuration of event

For more information about classes, interfaces and methods, see [javadocs documentation](https://kostejnv.github.io/o-starter/android_docs/ "Android App Documentation").

### Discussion about concepts of application

**Import of startlist**

I created interface *StartlistsConverter* for importing startlists from various formats. All orienteering systems work with format IOF XML v.3 that was developed by International Orienteering Federation but I count with possibility that there will be other formats in the future.

For parsing format IOF XML v.3, I used *XmlPullParser* from package *org.xmlpull.v1*. After long research, I chose the parser because it should be the fastest way how to parse only part of the startlists. There are also disadvantages, for example not so structured and clear code.

**Database**

The application has to store data about events. I used database for this purpose, especially Room Database which is an abstraction over SQL database.


**Updating UI after changes in database**

There are often situations in the application when the user change data in database (settings, edit runner). The changes can change content of previous (parent) activity - for example content of recycler view. I created *DatabaseUpdateListener* for the purpose. Every Activity (and Fragment), which has components with data from database, implements the method *OnDBUpdate()* in which the components that could change are updated. The method is called when the database is changed.

This way can sometimes lead to updating more components than it is needed but the data is not so big and this concept is therefore sufficient.

**Server communication**

The communication is provided through HTTPS protocol. The data are sent in the body of request in JSON format. I created special entities for this purpose that convert changed runner and unstarted runner to the right format for exchange. In the entities is also a attribute for *server-key* which is need for authentication that request is sent by Android application. 



## Possible application improvements

Of course, there is a space for improvements. In the future, I would like to add extensions as finding runner by name in startlist view, button for scrolling on actual minutes in the startlist view and possibility to manage event on more than one device.



