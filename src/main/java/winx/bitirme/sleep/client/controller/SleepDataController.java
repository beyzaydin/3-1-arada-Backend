package winx.bitirme.sleep.client.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.sleep.client.model.SleepChartData;

@RestController
@RequestMapping("/sleep")
@CrossOrigin("http://localhost:3000")
public class SleepDataController {

    @PostMapping(value = "/deneme", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deneme(@RequestBody SleepChartData sleepChartData) {
        return "{\n" +
                "                label: \"Monday\",\n" +
                "                data: [2, 9, 3, 5, 2, 3],\n" +
                "                borderWidth: 1\n" +
                "            },\n" +
                "            {\n" +
                "                label: \"Tuesday\",\n" +
                "                data: [1, 8, 10, 7, 6, 3, 5, 6, 1, 0, 0, 0, 0],\n" +
                "                borderWidth: 1\n" +
                "            }";
    }

    @GetMapping(value = "/deneme", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deneme() {
        return "{\n" +
                "    \"timeData\": [\n" +
                "        \"6/12\",\n" +
                "        \"6/13\",\n" +
                "        \"6/14\",\n" +
                "        \"6/15\",\n" +
                "        \"6/15\",\n" +
                "        \"6/15\",\n" +
                "        \"6/18\",\n" +
                "        \"6/15\",\n" +
                "        \"6/20\",\n" +
                "        \"6/21\",\n" +
                "        \"6/22\",\n" +
                "        \"6/23\",\n" +
                "        \"6/24\",\n" +
                "        \"6/25\",\n" +
                "        \"6/26\",\n" +
                "        \"6/27\",\n" +
                "        \"6/28\",\n" +
                "        \"6/29\",\n" +
                "        \"6/30\",\n" +
                "        \"7/1\",\n" +
                "        \"7/2\",\n" +
                "        \"7/3\",\n" +
                "        \"7/4\",\n" +
                "        \"7/5\",\n" +
                "        \"7/6\",\n" +
                "        \"7/7\",\n" +
                "        \"7/8\",\n" +
                "        \"7/9\",\n" +
                "        \"7/10\",\n" +
                "        \"7/11\",\n" +
                "        \"7/12\",\n" +
                "        \"7/13\",\n" +
                "        \"7/14\",\n" +
                "        \"7/15\",\n" +
                "        \"7/16\",\n" +
                "        \"7/17\",\n" +
                "        \"7/18\",\n" +
                "        \"7/19\",\n" +
                "        \"7/20\",\n" +
                "        \"7/21\",\n" +
                "        \"7/22\",\n" +
                "        \"7/23\",\n" +
                "        \"7/24\",\n" +
                "        \"7/25\",\n" +
                "        \"7/26\",\n" +
                "        \"7/27\",\n" +
                "        \"7/28\",\n" +
                "        \"7/29\",\n" +
                "        \"7/30\",\n" +
                "        \"7/31\",\n" +
                "        \"8/1\",\n" +
                "        \"9/1\",\n" +
                "        \"10/1\"\n" +
                "    ],\n" +
                "    \"data\": [\n" +
                "        3.5,\n" +
                "        6.7,\n" +
                "        3.6,\n" +
                "        1.3,\n" +
                "        7.8,\n" +
                "        9.7,\n" +
                "        11.3,\n" +
                "        5.7,\n" +
                "        5.5,\n" +
                "        3.5,\n" +
                "        6.7,\n" +
                "        3.6,\n" +
                "        1.3,\n" +
                "        7.8,\n" +
                "        9.7,\n" +
                "        11.3,\n" +
                "        5.7,\n" +
                "        5.5,\n" +
                "        9.9,\n" +
                "        3.5,\n" +
                "        6.7,\n" +
                "        3.6,\n" +
                "        1.3,\n" +
                "        7.8,\n" +
                "        9.7,\n" +
                "        11.3,\n" +
                "        5.7,\n" +
                "        5.5,\n" +
                "        9.9,\n" +
                "        3.5,\n" +
                "        6.7,\n" +
                "        3.6,\n" +
                "        1.3,\n" +
                "        7.8,\n" +
                "        9.7,\n" +
                "        11.3,\n" +
                "        5.7,\n" +
                "        5.5,\n" +
                "        9.9,\n" +
                "        3.5,\n" +
                "        6.7,\n" +
                "        3.6,\n" +
                "        1.3,\n" +
                "        7.8,\n" +
                "        9.7,\n" +
                "        11.3,\n" +
                "        5.7,\n" +
                "        5.5,\n" +
                "        9.9,\n" +
                "        11.3,\n" +
                "        19.3,\n" +
                "        24.0,\n" +
                "        24.0\n" +
                "    ]\n" +
                "}";
    }
}
