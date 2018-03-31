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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestService {

        /**
     * 呵呵
     *
     * @return 返回当前时间
     */
    @RequestMapping("/instant")
    public Instant instant() {
        return Instant.now();
    }

    @RequestMapping("/instantArray")
    public Instant[] instantsArray() {
        return new Instant[]{Instant.now(),Instant.now()};
    }

    @RequestMapping("/instants")
    public List<Instant> instants() {
        return Arrays.asList(Instant.now());
    }

    @RequestMapping("/longs")
    public List<Long> longs() {
        return Arrays.asList(Long.MAX_VALUE);
    }

    @RequestMapping("/long")
    public Long longValue() {
        return Long.MAX_VALUE;
    }

    @RequestMapping("/valid")
    public ValidModel valid(@Valid ValidForm form) {
        ValidModel m = new ValidModel();
        BeanUtils.copyProperties(form, m);
        return m;
    }

    @RequestMapping("/object")
    public ObjectModel object(@Valid ObjectForm form) {
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

        return m;
    }

    @RequestMapping("/testObject")
    public ObjectModel testObject() {
        return ModelUtils.newObjectModel();
    }

    @RequestMapping("/testModel")
    public TestModel<Date> testModel() {
        return new TestModel();
    }

    /**
     * form实际上不支持泛型
     */
    @RequestMapping("/generic")
    public GenericModel<ValidModel> generic(@Valid GenericForm<ValidForm> form) {
        GenericModel<ValidModel> model = new GenericModel<>();
        ValidModel data = new ValidModel();
        data.setName(RandomStringUtils.random(128));
        model.setData(data);
        return model;
    }
}
