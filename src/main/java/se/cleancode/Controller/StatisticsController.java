package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.View.StatisticsView;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "statistics", produces = "application/json")
public class StatisticsController {

    @Autowired
    StatisticsView view;

    @RequestMapping(value = "", method = GET)
    public Map<Integer, Long> calculateStatistics() {
        return view.calculateStatistics();
    }


}
