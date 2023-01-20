package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IrrigationTest {
    Irrigation verticalIrrigation;
    Tile x0y0;
    Tile x1y0;
    Irrigation fSlashIrrigation;
    Tile x1y1;
    Irrigation bSlashIrrigation;
    Tile xn1y1;

    @BeforeEach
    void setup() {
        x0y0 = new Tile(new Coordinate(0,0));
        x1y0 = new Tile(new Coordinate(1,0));
        x1y1 = new Tile(new Coordinate(1,1));
        xn1y1 = new Tile(new Coordinate(-1,1));
        verticalIrrigation = new Irrigation(x0y0,x1y0);
        fSlashIrrigation = new Irrigation(x1y0,x1y1);
        bSlashIrrigation = new Irrigation(x0y0,xn1y1);
    }

    @Test
    void testTypeAttribution() {
        assertEquals(TypeOfIrrigation.vertical,verticalIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.fSlash,fSlashIrrigation.getIrrigationType());
        assertEquals(TypeOfIrrigation.bSlash,bSlashIrrigation.getIrrigationType());

    }

}