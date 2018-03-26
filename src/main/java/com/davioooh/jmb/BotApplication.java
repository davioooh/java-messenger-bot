package com.davioooh.jmb;

import com.davioooh.jmb.model.Callback;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.davioooh.jmb.model.Callback.PAGE;
import static spark.Spark.get;
import static spark.Spark.post;

public class BotApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotApplication.class);
    private static final Gson GSON = new Gson();

    // Your verify token. Should be a random string.
    private static final String VERIFY_TOKEN = "<YOUR_VERIFY_TOKEN>";
    private static final String MODE_SUBSCRIBE = "subscribe";


    public static void main(String[] args) {

        /**
         * Adds support for GET requests to our webhook
         */
        get("/webhook", (req, res) -> {

            // Parse the query params
            String mode = req.queryParamOrDefault("hub.mode", "");
            String token = req.queryParamOrDefault("hub.verify_token", "");
            String challenge = req.queryParams("hub.challenge");

            // Checks the mode and token sent is correct
            if (mode.equals(MODE_SUBSCRIBE)
                    && token.equals(VERIFY_TOKEN)) {

                // Responds with the challenge token from the request
                LOGGER.debug("WEBHOOK_VERIFIED");
                res.status(200);
                return challenge;
            }
            // Responds with '403 Forbidden' if verify tokens do not match
            res.status(403);
            return "";

        });

        /**
         * Creates the endpoint for our webhook
         */
        post("/webhook", (req, res) -> {
            Callback callback = GSON.fromJson(req.body(), Callback.class);

            // Checks this is an event from a page subscription
            if (callback.getObject().equals(PAGE)) {

                // Iterates over each entry - there may be multiple if batched
                callback.getEntry().forEach(e -> {
                    // Gets the message. entry.messaging is an array, but
                    // will only ever contain one message, so we get index 0
                    System.out.println(e.getMessaging().get(0));

                });

                // Returns a '200 OK' response to all requests
                res.status(200);
                return "EVENT_RECEIVED";
            }

            // Returns a '404 Not Found' if event is not from a page subscription
            res.status(404);
            return "";
        });
    }
}
