package com.example.samantha.androidclient;

public class Conexion {
    int portNumber = 4444;
    String hostName = "192.168.1.66";

    Conexion(){
        portNumber=4444;
        hostName="192.168.1.66";
    }
    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
