package ru.geekbrains.DiplomGBProject.entity;

public enum Status {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETE;

    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(text.replace(" ", ""))) {
                return status;
            }
        }
        return null;
    }
}