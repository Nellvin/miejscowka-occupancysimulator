package org.example.miejscowka.occupancysimulator.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.miejscowka.occupancysimulator.logic.OccupancySimulatorLogic;
import org.example.miejscowka.occupancysimulator.model.LocalDateAdapter;
import org.example.miejscowka.occupancysimulator.model.SimulateOccupancyTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class OccupancySimulatorServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(OccupancySimulatorLogic.class);

    @Value("${app.service.domain}")
    private String serviceDomain ;
    @Value("${app.service.port}")
    private int servicePort;
    @Value("${app.service.api.path}")
    private String serviceApiPath;

    private CloseableHttpClient httpclient;
    private HttpPost httppost;
    private Gson gson;

    public void openConnections() {
        httpclient = HttpClients.createDefault();
        httppost = new HttpPost("http://" + serviceDomain + ":" + servicePort + "/" + serviceApiPath);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter().nullSafe()).create();
    }

    public void sendRequest(SimulateOccupancyTo simulateOccupancyTo) {
        try {
            LOG.info("Sending occupancy for place: " + simulateOccupancyTo.getPlaceId());
            StringEntity entity = new StringEntity(gson.toJson(simulateOccupancyTo));
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            LOG.info("Place " + simulateOccupancyTo.getPlaceId() + " changed with " + simulateOccupancyTo.getNumberOfPeople() + " people. Received response code: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            LOG.warn(e.toString());
        } finally {
            httppost.releaseConnection();
        }
    }


}
