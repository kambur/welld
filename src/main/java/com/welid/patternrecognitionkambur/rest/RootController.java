package com.welid.patternrecognitionkambur.rest;

import com.welid.patternrecognitionkambur.domain.Line;
import com.welid.patternrecognitionkambur.domain.Point;
import com.welid.patternrecognitionkambur.dto.PointDTO;
import com.welid.patternrecognitionkambur.repo.PointRepository;
import com.welid.patternrecognitionkambur.service.LineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RootController {

    private final Logger log = LoggerFactory.getLogger(RootController.class);
    private final PointRepository pointRepo;
    private final LineService lineService;

    public RootController(PointRepository pointRepoPar, LineService lineServicePar) {
        this.pointRepo = pointRepoPar;
        this.lineService = lineServicePar;
    }

    @PostMapping(value = "/point", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addPoint(@RequestBody @Valid PointDTO point) {

        log.info("addPoint(): x={}, y={} call", point.getX(), point.getY());
        if (pointRepo.getPoints().stream().anyMatch(point1 -> (point1.getX() == point.getX() && point1.getX() == point.getY()))) {
            log.info("Point x={}, y={} already exists.", point.getX(), point.getY());
            return Collections.singletonMap("message", "Point already exists!");
        }

        pointRepo.getPoints().add(new Point(point.getX(), point.getY()));
        log.info("New point: x={}, y={} added.", point.getX(), point.getY());

        return Collections.singletonMap("message", "Success!");
    }

    @GetMapping(value = "/lines/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Line>> getLines(HttpServletRequest request, @PathVariable("number") @Min(1) Integer n) {

        log.info("getLines(): N={} call", n);
        List<Line> lines = lineService.findLines(n);

        return ResponseEntity.ok(lines);
    }

    @GetMapping(value = "/space", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PointDTO>> getAllPoints() {

        log.info("getAllPoints() call");

        if (pointRepo.getPoints() != null){
            List<PointDTO> pointDTOS = pointRepo.getPoints().stream().map(point -> new PointDTO(point.getX(), point.getY())).collect(Collectors.toList());
            log.info("All points: {}", pointDTOS.toString());
            return ResponseEntity.ok(pointDTOS);
        } else
            return ResponseEntity.ok(Collections.emptyList());
    }

    @DeleteMapping(value = "/space")
    public Map<String, Object> clear() {
        log.info("clear() call");

        pointRepo.setPoints(new HashSet<Point>());
        return Collections.singletonMap("message", "Space cleared!");
    }

}
