package org.example.miejscowka.occupancysimulator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class OccupancySimulatorLogic {
    private static final Logger LOG = LoggerFactory.getLogger(OccupancySimulatorLogic.class);
    CloseableHttpClient httpclient;
    HttpPost httppost;
    private List<Long> placesId;

    public OccupancySimulatorLogic(List<Long> placesId) {
        this.placesId = placesId;
    }

    public void openConnections() {
        httpclient = HttpClients.createDefault();
        httppost = new HttpPost("http://localhost:8090/occupancy/absolute");
    }

    public void sendSimulatedOccupancies() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter().nullSafe()).create();
        while (true) {
            httppost = new HttpPost("http://localhost:8090/occupancy/relative");
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            for (Long placeId : placesId) {
                try {
                    LOG.info("Sending occupancy for place: " + placeId);
                    SimulateOccupancyTo simulateOccupancyTo = new SimulateOccupancyTo(2, 1000000L, LocalDateTime.now());
                    StringEntity entity = new StringEntity(gson.toJson(simulateOccupancyTo));
                    httppost.setEntity(entity);
                    CloseableHttpResponse response = httpclient.execute(httppost);
                    LOG.info("Place " + placeId + " response with code: " + response.getStatusLine().getStatusCode());
                    Thread.sleep(2 * 1000);
                } catch (IOException | InterruptedException e) {
                    LOG.warn(e.toString());
                    e.printStackTrace();
                } finally {
                    httppost.releaseConnection();
                }
            }
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
