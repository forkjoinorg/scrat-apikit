package org.forkjoin.scrat.apikit.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.forkjoin.TestModel;
import org.forkjoin.scrat.apikit.example.form.FilterListFrom;
import org.forkjoin.scrat.apikit.example.form.GenericForm;
import org.forkjoin.scrat.apikit.example.form.ObjectForm;
import org.forkjoin.scrat.apikit.example.form.ValidForm;
import org.forkjoin.scrat.apikit.example.form.child.TestArrayModel;
import org.forkjoin.scrat.apikit.example.model.GenericModel;
import org.forkjoin.scrat.apikit.example.model.ObjectModel;
import org.forkjoin.scrat.apikit.example.model.TestWrapperModel;
import org.forkjoin.scrat.apikit.example.model.ValidModel;
import org.forkjoin.scrat.apikit.example.model.child.GenericValue;
import org.forkjoin.scrat.apikit.example.type.StatusType;
import org.forkjoin.scrat.apikit.utils.ModelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestService {

    @GetMapping("/bytes")
    public Mono<byte[]> bytes() {
        return null;
    }


    @GetMapping("/filterListFrom")
    public Mono<FilterListFrom> filterListFrom() {
        return null;
    }

    @RequestMapping("/upload")
    public StatusType upload(
            @RequestPart("file1") Mono<FilePart> file,
            @RequestPart("file2") Mono<FilePart[]> file2,
            @RequestPart("file3") Mono<FilePart[]> file3,
            @RequestPart(value = "file2", required = false) Mono<FormFieldPart[]> formFieldPart
    ) {
        return StatusType.INIT;
    }

    /**
     * 你好
     *
     * @param sb     参数1
     * @param param
     * @param header
     * @return
     */
    @RequestMapping("/testObject/{sb}")
    public ObjectModel testObject(
            @PathVariable String sb,
            @RequestParam String param,
            @RequestHeader String header
    ) {
        return ModelUtils.newObjectModel();
    }

    //
    @RequestMapping("/testObjectDefault/{sb1}")
    public GenericValue<Date, ObjectModel> testObjectDefault(
            @Valid ValidForm form1,
            @Valid ValidForm form2,
            ValidForm form3,
            @PathVariable(name = "sb1", required = false) String sb,
            @RequestParam(name = "param1", defaultValue = "param1v", required = false) String param,
            @RequestParam(name = "param2", defaultValue = "1", required = false) Integer param2,
            @RequestParam(name = "param3", defaultValue = "1", required = false) Double param3,
            @RequestParam(name = "param4", defaultValue = "true", required = false) Boolean param4,
            @RequestParam(name = "param5", defaultValue = "1", required = false) Float param5,
            @RequestParam(name = "param6", defaultValue = "1", required = false) float intParam,
            @RequestParam(name = "status1", defaultValue = "INIT", required = true) StatusType status1,
            @RequestParam(name = "status2", defaultValue = "INIT", required = false) StatusType status2,
            @RequestParam(name = "status3", required = false) StatusType status3,
            @RequestParam(name = "status4", required = true) StatusType status4,
            @RequestParam(name = "date", defaultValue = "2010-01-01", required = false) LocalDate date,
            @RequestHeader(name = "header1", defaultValue = "header1v", required = false) String header

    ) {
        return null;
    }

//    @RequestMapping("/testObjectDefault2/{sb1}")
//    public GenericValue<Date, ObjectModel> testObjectDefault2(
//            @Valid ValidForm form1,
//            @Valid ValidForm form2,
//            ValidForm form3,
//            @PathVariable(name = "sb1", required = false) String sb,
//            @RequestParam(name = "param1", defaultValue = "param1v", required = false) String[] param,
//            @RequestParam(name = "param2", defaultValue = "1", required = false) Integer[] param2,
//            @RequestParam(name = "param3", defaultValue = "1", required = false) Double[] param3,
//            @RequestParam(name = "param4", defaultValue = "true", required = false) Boolean[] param4,
//            @RequestParam(name = "param5", defaultValue = "1", required = false) Float[] param5,
//            @RequestParam(name = "param6", defaultValue = "1", required = false) float intParam,
//            @RequestParam(name = "status1", defaultValue = "INIT", required = true)  StatusType[] status1,
//            @RequestParam(name = "status2", defaultValue = "INIT", required = false)  StatusType[] status2,
//            @RequestParam(name = "status3", required = false)  StatusType[] status3,
//            @RequestParam(name = "status4", required = true)  StatusType[] status4,
//            @RequestParam(name = "date", defaultValue = "2010-01-01", required = false) LocalDate[] date,
//            @RequestHeader(name = "header1", defaultValue = "header1v", required = false) String[] header
//
//            ) {
//        return null;
//    }

//    @RequestMapping("/testObjectDefault3/{sb1}")
//    public GenericValue<Date, ObjectModel> testObjectDefault3(
//            @Valid ValidForm form1,
//            @Valid ValidForm form2,
//            ValidForm form3,
//            @PathVariable(name = "sb1", required = false) List<String> sb,
//            @RequestParam(name = "param1", defaultValue = "param1v", required = false) List<String> param,
//            @RequestParam(name = "param2", defaultValue = "1", required = false) List<Integer> param2,
//            @RequestParam(name = "param3", defaultValue = "1", required = false) List<Double> param3,
//            @RequestParam(name = "param4", defaultValue = "true", required = false) List<Boolean> param4,
//            @RequestParam(name = "param5", defaultValue = "1", required = false) List<Float> param5,
//            @RequestParam(name = "param6", defaultValue = "1", required = false) List<Float> intParam,
//            @RequestParam(name = "status1", defaultValue = "INIT", required = true)  List<StatusType> status1,
//            @RequestParam(name = "status2", defaultValue = "INIT", required = false)  List<StatusType> status2,
//            @RequestParam(name = "status3", required = false)  List<StatusType> status3,
//            @RequestParam(name = "status4", required = true)  List<StatusType> status4,
//            @RequestParam(name = "date", defaultValue = "2010-01-01", required = false) List<LocalDate> date,
//            @RequestHeader(name = "header1", defaultValue = "header1v", required = false) List<String> header
//
//    ) {
//        return null;
//    }
//

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
        return new Instant[]{Instant.now(), Instant.now()};
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
