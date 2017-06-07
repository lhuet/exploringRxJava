package org.breizhjug.rxjava2.webapp.subject;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
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

    private Subject<Long> subject = ReplaySubject.create();

    @PostConstruct
    private void init() {
        subject.observeOn(Schedulers.single())
                .onErrorReturnItem(0L)
                .subscribe(
                        s -> {
                            log.info("Computation to be done for value : " + new Date(s));
                            Thread.sleep(1000);
                        },
                        throwable -> log.error(throwable.getMessage()),
                        () -> log.info("Stream finished"));
    }


    public void pushData(long data) {

        subject.onNext(data);

    }



}
