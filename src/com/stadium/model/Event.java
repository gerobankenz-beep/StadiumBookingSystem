package com.stadium.model;

public class Event {
    private int eventId;
    private String eventType;
    private String eventName;
    private String eventTime;
    private String eventDate;

    public Event(int eventId, String eventType, String eventName, String eventDate, String eventTime){
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventType(){
        return eventType;
    }

    public String getEventName(){
        return eventName;
    }

    public String getEventDate(){
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }
}
