package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import webservice_v2.config.ServiceConfig;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControllTest {

    public static List<String> tests=new ArrayList<>();
    public ReplayProcessor<String> testNotifications = ReplayProcessor.create(0, false);

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> getTests()
    {
        //System.out.println(testNotifications.toString());
        Flux<String> flux=Flux.fromIterable(tests);
        return flux;
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> UpdateInvitationWithResponse(@RequestBody String test)
    {
        tests.add(test);
        //testNotifications.onNext(test);
        System.out.println(test);
        return ResponseEntity.ok().body(test);
    }



}
