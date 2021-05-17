package com.example.o_starter;

/**
 * Variables for specific instance of application
 */
public class EnviromentVariables {
    /**
     * if the application is in development mode or not
     */
    public static final Mode MODE = Mode.TEST;

    /**
     * hardcoded key for communication with the specific server
     */
    public static final String SERVER_KEY = "ijcfzrhagf";

    public enum Mode {TEST,RUN}
}


