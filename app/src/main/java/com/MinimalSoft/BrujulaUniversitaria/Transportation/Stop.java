package com.MinimalSoft.BrujulaUniversitaria.Transportation;

/**
 * Created by David on 22/07/2016.
 */
public class Stop {

        public final String id, agency, route, routeName, stopSequence, stopName, stopLat, stopLong,stopIcon;

        public Stop(String id, String agency, String route, String routeName,
                    String stopSequence, String stopName, String stopLat, String stopLong, String stopIcon)
        {
            this.id = id;
            this.agency = agency;
            this.route = route;
            this.routeName = routeName;
            this.stopSequence = stopSequence;
            this.stopName = stopName;
            this.stopLat = stopLat;
            this.stopLong = stopLong;
            this.stopIcon = stopIcon;
        }
}
