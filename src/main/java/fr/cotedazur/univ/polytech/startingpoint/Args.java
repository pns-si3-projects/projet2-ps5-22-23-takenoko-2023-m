package fr.cotedazur.univ.polytech.startingpoint;

import com.beust.jcommander.Parameter;

public class Args {


    @Parameter(names = {"--demo"}, description = "Activer les sorties détaillées pour la démo")
    public boolean demo;

    @Parameter(names = {"--2thousands"}, description = "Désactiver les sorties et lancer 2000 parties")
    public boolean twoThousands;

    @Parameter(names = {"--csv"}, description = "Désactiver les sorties et lancer des parties puis la sortie sur le csv")
    public boolean csv;
}
