package com.example.myway.Model;

public class ToDoModel {
    private int id;
    private int status;
    private int status_eat;
    private int status_no;
    private int id_no;
    private int id_eat;
    private int id_eat_table;

    public int getId_eat_table() {
        return id_eat_table;
    }

    public void setId_eat_table(int id_eat_table) {
        this.id_eat_table = id_eat_table;
    }

    public int getId_no_table() {
        return id_no_table;
    }

    public void setId_no_table(int id_no_table) {
        this.id_no_table = id_no_table;
    }

    private int id_no_table;
    private String task, eat, no,date;


    public int getStatus_eat() {
        return status_eat;
    }

    public void setStatus_eat(int status_eat) {
        this.status_eat = status_eat;
    }

    public int getStatus_no() {
        return status_no;
    }

    public void setStatus_no(int status_no) {
        this.status_no = status_no;
    }

    public int getId_no() {
        return id_no;
    }

    public void setId_no(int id_no) {
        this.id_no = id_no;
    }

    public int getId_eat() {
        return id_eat;
    }

    public void setId_eat(int id_eat) {
        this.id_eat = id_eat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEat() {
        return eat;
    }

    public void setEat(String eat) {
        this.eat = eat;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
