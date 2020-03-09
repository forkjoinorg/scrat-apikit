package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.plugin.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ExecUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static int exec(String command, Log log) {
        return exec(command, log, null, null, null);
    }

    public static int exec(String command, Log log, @Nullable String dir) {
        return exec(command, log, dir, null, null);
    }

    public static int exec(String command, Log log, @Nullable String dir, @Nullable StringBuffer stringBuffer, @Nullable StringBuffer errStringBuffer) {
        try {
            Process exec;
            if (dir == null) {
                exec = Runtime.getRuntime().exec(command);
            } else {
                exec = Runtime.getRuntime().exec(command, new String[]{}, new File(dir));
            }

            if (errStringBuffer == null) {
                errStringBuffer = new StringBuffer();
            }
            StringBuffer finalErrStringBuffer = errStringBuffer;
            Flux<String> inputFlux = Flux
                    .<String>create(fluxSink -> {
                        BufferedReader input = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                        String line = null;
                        try {
                            while ((line = input.readLine()) != null) {
                                fluxSink.next(line);
                            }
                            fluxSink.complete();
                        } catch (Throwable e) {
                            fluxSink.error(e);
                        }
                    })
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(log::info)
                    .doOnNext(finalErrStringBuffer::append);


            Flux<String> inputErrorFlux = Flux
                    .<String>create(fluxSink -> {
                        BufferedReader input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                        String line = null;
                        try {
                            while ((line = input.readLine()) != null) {
                                fluxSink.next(line);
                            }
                            fluxSink.complete();
                        } catch (Throwable e) {
                            fluxSink.error(e);
                        }
                    })
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(log::info)
                    .doOnNext(str -> {
                        if (stringBuffer != null) {
                            stringBuffer.append(str);
                        }
                    });

            return Mono.zip(inputFlux.all(r -> true), inputErrorFlux.all(r -> true))
                    .then(Mono.<Integer>create(r -> {
                        try {
                            r.success(exec.waitFor());
                            log.info("finalErrStringBuffer:" + finalErrStringBuffer);
                        } catch (InterruptedException e) {
                            r.error(e);
                        }
                    })).blockOptional().orElse(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
