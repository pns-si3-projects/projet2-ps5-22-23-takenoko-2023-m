package fr.cotedazur.univ.polytech.startingpoint;

import com.beust.jcommander.Parameter;

public class Args {


    @Parameter(names = {"--demo"}, description = "Activer les sorties détaillées et Lancer un partie")
    public boolean demo;

    @Parameter(names = {"--2thousands"}, description = "Désactiver les sorties et lancer 2fois 1000 parties entre nos bots")
    public boolean twoThousands;

    @Parameter(names = {"--csv"}, description = "Désactiver les sorties et lancer des parties puis incrémenter les infos dans le csv")
    public boolean csv;

    @Parameter(names = {"--allBots"}, description = "Lancer plusieurs parties jouées entre nos 3 bots")
    public boolean allBots;
}
