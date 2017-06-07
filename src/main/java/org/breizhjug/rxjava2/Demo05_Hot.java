package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lhuet on 28/05/2017.
 */
public class Demo05_Hot {

    public static final Logger log = LoggerFactory.getLogger(Demo05_Hot.class);
    
    public static void main(String[] args) throws InterruptedException {

        Subject<String> subject = ReplaySubject.create();

        log.info("Emit value 1");
        subject.onNext("1");

        CountDownLatch latch = new CountDownLatch(1);
        subject.observeOn(Schedulers.newThread())
                .subscribe(s -> log.info("Subscriber 1 receive value : " + s),
                throwable -> log.error(throwable.getMessage()),
                () -> {
                    log.info("End subscriber 1");
                    latch.countDown();
                });

        log.info("Emit value 2");
        subject.onNext("2");

        subject.onComplete();

        subject.observeOn(Schedulers.newThread())
                .subscribe(s -> log.info("---- Subscriber 2 receive value : " + s));

        latch.await();
        log.info("The end !");

    }



}
