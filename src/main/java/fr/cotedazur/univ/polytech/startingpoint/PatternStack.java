package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class PatternStack {
    ArrayList<Pattern> createdPattern = new ArrayList<>();

    public Pattern pickPattern(){
        return new Pattern(createdPattern);
    }
}
