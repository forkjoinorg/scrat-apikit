package org.forkjoin.scrat.apikit.example;


import org.apache.commons.lang3.RandomStringUtils;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.form.GenericForm;
import org.forkjoin.scrat.apikit.example.form.ObjectForm;
import org.forkjoin.scrat.apikit.example.form.ValidForm;
import org.forkjoin.scrat.apikit.example.model.GenericModel;
import org.forkjoin.scrat.apikit.example.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.model.TestArrayModel;
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

@RestController
@RequestMapping("/testFlux/")
public class TestFluxService {

    @GetMapping("/instant/")
    public Mono<Instant> instant() {
        return Mono.just(Instant.now());
    }

    @RequestMapping({"/instants", "/instantsArray"})
    public Flux<Instant> instants() {
        return Flux.just(Instant.now());
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

    @RequestMapping("/valid")
    public Mono<ValidModel> valid(@Valid ValidForm form) {
        ValidModel m = new ValidModel();
        BeanUtils.copyProperties(form, m);
        return Mono.just(m);
    }

    @RequestMapping("/valid/{id}-{id2}")
    public Mono<ValidModel> validById(@Valid ValidForm form, @PathVariable String id2,@PathVariable String id) {
        ValidModel m = new ValidModel();
        BeanUtils.copyProperties(form, m);
        return Mono.just(m);
    }
//
    @RequestMapping("/object")
    public Mono<ObjectModel> object(@Valid ObjectForm form) {
        ObjectModel m = new ObjectModel();
        TestArrayModel testArray = new TestArrayModel();
        TestModel test = new TestModel();
        TestWrapperModel testWrapper = new TestWrapperModel();

        m.setTestArray(testArray);
        m.setTest(test);
        m.setTestWrapper(testWrapper);

        if (form.getTest() != null) {
            BeanUtils.copyProperties(form.getTest(), test);
        }
        if (form.getTestArray() != null) {
            BeanUtils.copyProperties(form.getTestArray(), testArray);
        }
        if (form.getTestWrapper() != null) {
            BeanUtils.copyProperties(form.getTestWrapper(), testWrapper);
        }

        return Mono.just(m);
    }

    @RequestMapping("/testObject")
    public Mono<ObjectModel> testObject() {
        return Mono.just(ModelUtils.newObjectModel());
    }


    /**
     * form实际上不支持泛型
     */
    @RequestMapping("/generic")
    public Mono<GenericModel<ValidModel>> generic(@Valid GenericForm<ValidForm> form) {
        GenericModel<ValidModel> model = new GenericModel<>();
        ValidModel data = new ValidModel();
        data.setName(RandomStringUtils.random(128));
        model.setData(data);
        return Mono.just(model);
    }
}
