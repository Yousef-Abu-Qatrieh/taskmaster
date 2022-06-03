package com.example.firsttry;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String body;
    private String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
//     setState(state);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

//    public void setState(String state) {
//        switch (state) {
//            case "new":
//                this.state = state;
//                break;
//            case "assigned" :
//                this.state = state;
//                break;
//            case "in progress" :
//                this.state=state;
//                break;
//            case "complete":
//                this.state=state;
//                break;
//            default:
//                Log.i("Warning", "Invalid Input ");
//                break;
//        }
//    }


}
