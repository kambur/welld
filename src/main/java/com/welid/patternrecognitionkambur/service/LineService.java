package com.welid.patternrecognitionkambur.service;

import com.welid.patternrecognitionkambur.domain.Line;
import com.welid.patternrecognitionkambur.domain.Point;
import com.welid.patternrecognitionkambur.repo.PointRepository;
import com.welid.patternrecognitionkambur.rest.RootController;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service @NoArgsConstructor
@Setter @Getter
public class LineService {

    private final Logger log = LoggerFactory.getLogger(LineService.class);
    @Autowired
    private PointRepository pointRepo;

    /***
     * Given a set of N feature points in the plane (pointRepo), determine every line that contains N or more of the points, and
     * return all points involved.
     *
     * Sorting-based algorithm used.
     *
     * @param minPoints - N or more points
     * @return  - line list
     */
    public List<Line> findLines(int minPoints) {
        List<Line> lines = new ArrayList<>();

        log.info("inside findLines()");
        Point[] points = pointRepo.pointArray();

        log.info("points received from repo: {}", points == null ? "0" : points.length);

        // sort points based on their natural order (Point -> comparable<Point>)
        Arrays.sort(points);

        // iterate through each pair of points as reference points
        for (int i = 0; i < points.length - 1; i++) {
            Point p = points[i];
            log.info("root point x:{}, y:{}", p.getX(), p.getY());

            // sort the remaining points based on the slope
            Arrays.sort(points, i + 1, points.length, Comparator.comparingDouble(q -> p.slopeTo(q)));

            // initialize variables to find points with the same slope
            List<Point> currentLinePoints = new ArrayList<>();
            double currentSlope = Double.NaN;

            // iterate through the sorted points and identify points with the same slope
            for (int j = i + 1; j < points.length; j++) {
                double slope = p.slopeTo(points[j]);

                if (Double.compare(slope, currentSlope) == 0) {
                    currentLinePoints.add(points[j]);
                } else {
                    // check if the current line has enough points
                    if (currentLinePoints.size() + 1 >= minPoints) {
                        Line line = new Line();
                        line.addPoint(p);
                        line.points.addAll(currentLinePoints);
                        lines.add(line);
                    }

                    // start a new line with the current point
                    currentLinePoints.clear();
                    currentLinePoints.add(points[j]);
                    currentSlope = slope;
                }
            }

            // check for the last set of consecutive points
            if (currentLinePoints.size() + 1 >= minPoints) {
                Line line = new Line();
                line.addPoint(p);
                line.points.addAll(currentLinePoints);
                lines.add(line);
            }
        }

        log.info("lines result: {}", lines.toString());

        return lines;
    }

}
