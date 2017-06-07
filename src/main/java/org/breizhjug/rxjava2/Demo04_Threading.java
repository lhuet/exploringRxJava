package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhuet on 22/05/2017.
 */
public class Demo04_Threading {
    
    public static final Logger log = LoggerFactory.getLogger(Demo04_Threading.class);

    public static void main(String[] args) throws InterruptedException {

        log.info("Main started ...");
        Observable<String> obs = Observable.defer(() -> Observable.just(blockingDataCreation()));

        log.info("Before listening data ...");
        obs.subscribe(s -> log.info("Received data : " + s));

        log.info("The end of listening data from blocking ops !");


        CountDownLatch latch = new CountDownLatch(1);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .subscribe(val -> log.info(" --> Value : " + val),
                        throwable -> log.info(throwable.getMessage()),
                        () -> {
                            log.info("End of the stream !");
                            latch.countDown();
                        });

        latch.await();

        Observable<String> observable = Observable.create(sub -> {
            log.info("Produce data");
            sub.onNext("2");
            sub.onNext("35");
            sub.onComplete();
        });

        CountDownLatch latch2 = new CountDownLatch(1);

        observable
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.newThread())
                .subscribe(s -> log.info(s),
                        throwable -> log.error(throwable.getMessage()),
                        () -> latch2.countDown());

        latch2.await();

        log.info("The end of main !");
    }


    public static String blockingDataCreation() throws InterruptedException {

        log.info("Blocking method ...");
        Thread.sleep(5000);
        log.info("Emit data");
        return "18";

    }
}
