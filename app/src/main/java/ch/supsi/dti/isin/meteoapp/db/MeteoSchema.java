package ch.supsi.dti.isin.meteoapp.db;

public class MeteoSchema {
    public static final class LocationTable {
        public static final String NAME = "locations";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
        }
    }
}
//TODO Verificare ulteriori campi necessari da persistere (accuratezza, coordinate, ...)
