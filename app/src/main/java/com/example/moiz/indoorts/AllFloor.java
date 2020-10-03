package com.example.moiz.indoorts;

class AllFloor {
    private String room;
    private String floor;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public AllFloor(){

    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public AllFloor(String floor, String room) {
        this.floor = floor;
        this.room = room;
    }
}

