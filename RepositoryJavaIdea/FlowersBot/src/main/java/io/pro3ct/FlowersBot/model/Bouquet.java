package io.pro3ct.FlowersBot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bouquet {

    private String name;
    private int price;
    private String description;

    private String ImagePath;

    public String toString(){
        return getName()+" "+ getPrice() + " " + getDescription();
    }
}

