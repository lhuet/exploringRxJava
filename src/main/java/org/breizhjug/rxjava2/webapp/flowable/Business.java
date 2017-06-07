package org.breizhjug.rxjava2.webapp.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by lhuet on 28/05/2017.
 */
@Service
public class Business {

    public static final Logger log = LoggerFactory.getLogger(Business.class);

    private ReplayProcessor<Long> flowable = ReplayProcessor.createWithSize(1);

    @PostConstruct
    private void init() {
        flowable.onBackpressureDrop(aLong -> log.info("drop " + aLong))
                .onErrorReturnItem(0L)
                .observeOn(Schedulers.single(), false, 1)

                .subscribe(
                        s -> {
                            log.info("Computation ... for value : " + new Date(s));
                            Thread.sleep(1000);
                            log.info("... done.");
                        },
                        throwable -> log.error(throwable.getMessage()),
                        () -> log.info("Stream finished"));
    }


    public void pushData(long data) {

        flowable.onNext(data);

    }



}
