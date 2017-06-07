package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by lhuet on 28/05/2017.
 */
public class Demo06_Resource {
    
    public static final Logger log = LoggerFactory.getLogger(Demo06_Resource.class);

    public static void main(String[] args) throws URISyntaxException, IOException {

        log.info("Create connectable observable");
        ConnectableObservable<String> observable = Observable.<String>create(subscriber -> {
            List<String> lines = getFileLine();
            lines.forEach(s -> subscriber.onNext(s));
        }).publish();

        log.info("Add 1st subscriber");
        observable.subscribe(s -> log.info("Subscriber 1 -> " + s));

        log.info("Add 2nd subscriber");
        observable.subscribe(s -> log.info("--- Subscriber 2 -> " + s));

        log.info("Connect");
        observable.connect();


    }

    public static List<String> getFileLine() throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(ClassLoader.getSystemResource("CHANGES.md").toURI()));
    }
}
