package assignment;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty studentName;
    private final StringProperty studentNumber;
    private final BooleanProperty mondayPresent;
    private final BooleanProperty tuesdayPresent;
    private final BooleanProperty wednesdayPresent;

    public Student(String studentName, String studentNumber) {
        this.studentName = new SimpleStringProperty(studentName);
        this.studentNumber = new SimpleStringProperty(studentNumber);
        this.mondayPresent = new SimpleBooleanProperty(false);
        this.tuesdayPresent = new SimpleBooleanProperty(false);
        this.wednesdayPresent = new SimpleBooleanProperty(false);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getStudentNumber() {
        return studentNumber.get();
    }

    public StringProperty studentNumberProperty() {
        return studentNumber;
    }

    public boolean isMondayPresent() {
        return mondayPresent.get();
    }

    public BooleanProperty mondayPresentProperty() {
        return mondayPresent;
    }

    public boolean isTuesdayPresent() {
        return tuesdayPresent.get();
    }

    public BooleanProperty tuesdayPresentProperty() {
        return tuesdayPresent;
    }

    public boolean isWednesdayPresent() {
        return wednesdayPresent.get();
    }

    public BooleanProperty wednesdayPresentProperty() {
        return wednesdayPresent;
    }

    // Optional: Setters if you want to update attendance directly
    public void setMondayPresent(boolean present) {
        this.mondayPresent.set(present);
    }

    public void setTuesdayPresent(boolean present) {
        this.tuesdayPresent.set(present);
    }

    public void setWednesdayPresent(boolean present) {
        this.wednesdayPresent.set(present);
    }

}

