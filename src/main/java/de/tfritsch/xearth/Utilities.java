package de.tfritsch.xearth;

import static javax.xml.bind.DatatypeConverter.parseDateTime;
import static javax.xml.bind.DatatypeConverter.parseFloat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

public final class Utilities {

    /**
     * Don't let anyone instantiate this class.
     */
    private Utilities() {
    }

    public static List<Marker> getInitialMarkers() {
        try {
            return readMarkers(Settings.class.getResource("markers.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Marker> readMarkers(final File file) throws IOException {
        return readMarkers(file.toURI().toURL());
    }

    private static List<Marker> readMarkers(final URL url) throws IOException {
        List<Marker> markers = new ArrayList<Marker>();
        CSVParser parser = CSVParser.parse(url, Charset.forName("US-ASCII"),
                CSVFormat.DEFAULT);
        for (CSVRecord record : parser) {
            Marker marker = new Marker();
            marker.setLatitude(parseFloat(record.get(0)));
            marker.setLongitude(parseFloat(record.get(1)));
            marker.setName(record.get(2));
            markers.add(marker);
        }
        parser.close();
        return markers;
    }

    public static void writeMarkers(final List<Marker> markers, final File file)
            throws IOException {
        CSVPrinter printer = new CSVPrinter(new FileWriter(file),
                CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC));
        for (Marker marker : markers) {
            printer.printRecord(marker.getLatitude(), marker.getLongitude(),
                    marker.getName());
        }
        printer.close();

    }

    public static List<Quake> readQuakes(final String url) throws IOException {
        List<Quake> quakes = new ArrayList<Quake>();
        CSVParser parser = CSVParser.parse(new URL(url), Charset
                .forName("US-ASCII"), CSVFormat.DEFAULT.withHeader()
                .withRecordSeparator("\n"));
        for (CSVRecord record : parser) {
            Quake quake = new Quake();
            quake.setTime(parseDateTime(record.get("time")).getTime());
            quake.setLatitude(parseFloat(record.get("latitude")));
            quake.setLongitude(parseFloat(record.get("longitude")));
            quake.setMagnitude(parseFloat(record.get("mag")));
            quake.setPlace(record.get("place"));
            quakes.add(quake);
        }
        parser.close();
        return quakes;
    }
}
