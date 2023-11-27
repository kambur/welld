package com.welid.patternrecognitionkambur.repo;

import com.welid.patternrecognitionkambur.domain.Point;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Component
public class PointRepository {

    public PointRepository() {
        points = new HashSet<Point>();
    }

    private Set<Point> points;

    public Point[] pointArray () {
        return points.stream().toArray(size -> new Point[size]);
    }

}
