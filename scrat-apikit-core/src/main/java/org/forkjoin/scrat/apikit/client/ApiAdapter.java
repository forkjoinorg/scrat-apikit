package org.forkjoin.scrat.apikit.client;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;


/*
 * http 请求实现接口，客户端自行实现
 * <b>ui适配器应该保证回调一定是在ui线程,服务器和测试代码则不需保证</b>
 *
 * @author zuoge85 on 15/6/15.
 */
public interface ApiAdapter {
    <T> Mono<T> request(RequestInfo requestInfo);
    <T> T parseDate(String str,Class<T> cls) throws IOException;
    void close() throws IOException;
}