package com.example.GameApp.model;


import com.example.GameApp.validator.GameValidation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@GameValidation
public class Game {

    private String name;

    private String creationDate;

    private boolean isActive;

    public Game(){};

    public Game(String name, String creationDate, boolean isActive) {
        this.name = name;
        this.creationDate = creationDate;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
