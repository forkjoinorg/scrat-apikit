package org.forkjoin.scrat.apikit.example;


import org.apache.commons.lang3.RandomStringUtils;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.core.Account;
import org.forkjoin.scrat.apikit.core.Ignore;
import org.forkjoin.scrat.apikit.example.form.GenericForm;
import org.forkjoin.scrat.apikit.example.form.ObjectForm;
import org.forkjoin.scrat.apikit.example.form.ValidForm;
import org.forkjoin.scrat.apikit.example.form.child.TestArrayModel;
import org.forkjoin.scrat.apikit.example.model.GenericModel;
import org.forkjoin.scrat.apikit.example.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.model.TestWrapperModel;
import org.forkjoin.scrat.apikit.example.model.ValidModel;
import org.forkjoin.scrat.apikit.utils.ModelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * 测试flux
 * 测试flux
 */
@RestController
@RequestMapping("/testFlux/")
@Ignore
public class TestFluxService {
    /**
     * 获取当前时间
     */
    @GetMapping("/bytes/")
    public Flux<byte[]> bytes() {
        return null;
    }

    /**
     * 获取当前时间
     */
    @GetMapping("/instant/")
    public Flux<GenericModel<String>[]> instant() {
        return null;
    }

    /**
     * 测试Flux
     */
    @RequestMapping({"/instants", "/instantsArray"})
    public Flux<List<Instant>> instants() {
        return null;
    }
//
//    @RequestMapping("/longs")
//    public Flux<Long> longs() {
//        return Flux.just(Long.MAX_VALUE);
//    }
//
//    @RequestMapping("/long")
//    public Mono<Long> longValue() {
//        return Mono.just(Long.MAX_VALUE);
//    }

    /**
     * 测试返回model
     */
    @RequestMapping("/valid")
    public Flux<ValidModel> valid(@Valid ValidForm form) {
        ValidModel m = new ValidModel();
        BeanUtils.copyProperties(form, m);
        return Flux.just(m);
    }

    /**
     * 测试PathVariable
     * 呵呵
     * 呵呵2
     *
     *
     * @param account 帐号信息
     * @param form 表单
     * @param id2  第二PathVariable
     * @param id   第1PathVariable
     * @return 返回一个model
     */
    @RequestMapping("/valid/{id}-{id2}")
    public Flux<ValidModel> validById(Account account, @Valid ValidForm form, @PathVariable String id2, @PathVariable String id) {
        ValidModel m = new ValidModel();
        BeanUtils.copyProperties(form, m);
        return Flux.just(m);
    }

    /**
     *
     * @param form
     * @param id2
     * @param id
     * @return
     */
    @RequestMapping("/testGenericModel/{id}-{id2}")
    public Flux<GenericModel<ValidModel>> testGenericModel(@Valid ValidForm form, @PathVariable String id2, @PathVariable String id) {
        return Flux.empty();
    }

    @RequestMapping("/object")
    public Flux<ObjectModel> object(@Valid ObjectForm form) {
        ObjectModel m = new ObjectModel();
        TestArrayModel testArray = new TestArrayModel();
        TestModel test = new TestModel();
        TestWrapperModel testWrapper = new TestWrapperModel();

//        m.setTestArray(testArray);
//        m.setTest(test);
//        m.setTestWrapper(testWrapper);

        if (form.getTest() != null) {
            BeanUtils.copyProperties(form.getTest(), test);
        }
        if (form.getTestArray() != null) {
            BeanUtils.copyProperties(form.getTestArray(), testArray);
        }
        if (form.getTestWrapper() != null) {
            BeanUtils.copyProperties(form.getTestWrapper(), testWrapper);
        }

        return Flux.just(m);
    }

    @RequestMapping("/testObject")
    public Flux<ObjectModel> testObject() {
        return Flux.just(ModelUtils.newObjectModel());
    }


    /**
     * form实际上不支持泛型
     */
    @RequestMapping("/generic")
    public Flux<GenericModel<ValidModel>> generic(@Valid GenericForm<ValidForm> form) {
        GenericModel<ValidModel> model = new GenericModel<>();
        ValidModel data = new ValidModel();
        data.setName(RandomStringUtils.random(128));
        model.setData(data);
        return Flux.just(model);
    }
}
