package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhuet on 23/05/2017.
 */
public class Demo03_MoreOperators {

    public static final Logger log = LoggerFactory.getLogger(Demo03_MoreOperators.class);

    public static void main(String[] args) throws InterruptedException {

        // Chaining operators
        Observable.just(2, 8, 3, 4, 5, 6, 7)
                .sorted()
                .take(4)
                .map(item -> "Value = " + item)
                .map(item -> item.toUpperCase())
                .flatMap(s -> Observable.fromArray(s.split("")))
                .zipWith(Observable.range(1, Integer.MAX_VALUE), (str, val) -> String.format("%2d. %s", val, str))
                .subscribe(it -> log.info(it));

        // Composition
        Observable<String> obs1 = Observable.just("item1", "item2", "item3", "item4");
        Observable<String> obs2 = Observable.just("it1", "it2", "it3", "it4");

        Observable.concat(obs1, obs2)
                .subscribe(s -> log.info(s));

        Observable<String> colors = Observable.just("red", "green", "blue");
        Observable<Long> timer = Observable.interval(3, TimeUnit.SECONDS);
        Observable<String> periodicEmitter = Observable.zip(colors, timer, (key, val) -> key);

        CountDownLatch latch = new CountDownLatch(2);
        periodicEmitter.subscribe(
                s -> log.info(s),
                throwable -> log.error(throwable.getMessage()),
                () -> {log.info("Complete"); latch.countDown();});


        Observable.merge(periodicEmitter, obs1)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(
                        s -> log.info(s),
                        throwable -> log.error(throwable.getMessage()),
                        () -> {log.info("complete"); latch.countDown();});

        latch.await();
        log.info("The end !");

    }
}
