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

    public String toString(){
        return getName()+"\n\n\uD83D\uDCB0"+ getPrice() + "\n\n" + getDescription() + "\n\n\uD83D\uDE9A Товар с доставкой";
    }
}

