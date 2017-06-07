package org.breizhjug.rxjava2;

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lhuet on 23/05/2017.
 */
public class Demo01_Basics {
    
    public static final Logger log = LoggerFactory.getLogger(Demo01_Basics.class);

    public static void main(String[] args) {

        Observable<String> stream = Observable.create(subscribe -> {

            log.info("---Start producing data ...");
            log.info("---Emit 1st data");
            subscribe.onNext("1st value");

            log.info("---Emit Error !");
            subscribe.onError(new RuntimeException("My dirty onError"));

            log.info("---Emit 2nd data");
            subscribe.onNext("2nd value");

            log.info("---The end !");
            subscribe.onComplete();
        });

        log.info("Stream defined !!");
        log.info("==========================================");
        stream
                .doOnNext(s -> log.info("OnNext -> " + s))
                .doOnError(throwable -> log.error("OnError -> " + throwable.getMessage()))
                .doOnComplete(() -> log.info("OnComplete"))
                .onErrorReturn(throwable -> "Error + " + throwable.getMessage())
                .subscribe(
                    val -> log.info("Receiving data : " + val),
                    throwable -> {log.info("Received an exception -> " + throwable.getMessage());},
                    () -> log.info("Receiving data is finished !")
        );

        log.info("==========================================");
        stream.subscribe(val -> log.info("Receiving data 2nd time : " + val));

    }
}
