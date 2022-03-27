package winx.bitirme.todo.service.entity;

import java.util.Date;

public class Task {
    private String name;
    private int repeatByDay;
    private Date dueDate;
    private String note;
    public Task(String name){
        this.name = name;
        this.repeatByDay = -1;
    }
    public Task(String name, int repeatByDay, Date dueDate, String note) {
        this.name = name;
        this.repeatByDay = repeatByDay;
        this.dueDate = dueDate;
        this.note = note;
    }
    public Task(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepeatByDay() {
        return repeatByDay;
    }

    public void setRepeatByDay(int repeatByDay) {
        this.repeatByDay = repeatByDay;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
