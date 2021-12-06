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
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class OccupancySimulatorLogic {
    @Value("${app.service.domain}")
    private String serviceDomain = "localhost";
    @Value("${server.port}")
    private int servicePort = 8060;
    @Value("${app.service.api.path}")
    private String serviceApiPath = "occupancy/relative";

    private static final Logger LOG = LoggerFactory.getLogger(OccupancySimulatorLogic.class);
    private CloseableHttpClient httpclient;
    private HttpPost httppost;
    private final List<Long> placesId;
    private final Random randomGen;


    public OccupancySimulatorLogic(List<Long> placesId) {
        this.placesId = placesId;
        randomGen = new Random();
    }

    public void openConnections() {
        httpclient = HttpClients.createDefault();
        httppost = new HttpPost("http://" + serviceDomain + ":" + servicePort + "/" + serviceApiPath);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
    }

    public void sendSimulatedOccupancies() {
        System.out.println();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter().nullSafe()).create();
        int occupancyChange;
        int stdDeviation = 2;
        SimulateOccupancyTo simulateOccupancyTo;
        StringEntity entity;
        CloseableHttpResponse response;
        while (true) {
            for (Long placeId : placesId) {
                try {
                    LOG.info("Sending occupancy for place: " + placeId);
                    occupancyChange = (int) (stdDeviation * randomGen.nextGaussian());
                    simulateOccupancyTo = new SimulateOccupancyTo(occupancyChange, placeId, LocalDateTime.now());
                    entity = new StringEntity(gson.toJson(simulateOccupancyTo));
                    httppost.setEntity(entity);
                    response = httpclient.execute(httppost);
                    LOG.info("Place " + placeId + " changed with " + occupancyChange + " people. Received response code: " + response.getStatusLine().getStatusCode());
                    Thread.sleep(2 * 1000);
                } catch (IOException | InterruptedException e) {
                    LOG.warn(e.toString());
                } finally {
                    httppost.releaseConnection();
                }
            }
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                LOG.warn(e.toString());
            }
        }
    }
}
