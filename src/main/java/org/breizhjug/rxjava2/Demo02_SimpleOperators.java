package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by lhuet on 13/05/2017.
 */
public class Demo02_SimpleOperators {

    public static final Logger log = LoggerFactory.getLogger(Demo02_SimpleOperators.class);
    
    public static void main(String[] args) throws InterruptedException {

        // Simple operators to create data streams
        Observable.just("1", "2", "3", "4", 8, Arrays.asList("2", 3))
                .subscribe(val -> log.info(val.getClass().getName()));

        Observable.range(1, 10)
                .subscribe(val -> log.info("range value : " + val));

        Observable.fromArray(new String[] {"1", "2", "3"})
                .subscribe(val -> log.info("array value : " + val));

        Observable.fromIterable(Arrays.asList("one", "two", "three"))
                .subscribe(val -> log.info("List item : " + val));

        // Operator chain
        Observable<String> obs1 =  Observable.just("1", "2", "3", "4");

        obs1.map(item -> Integer.valueOf(item))
                .filter(i -> i > 2)
                .take(2)
                .subscribe(i -> log.info("Value : " + i));

    }
}
