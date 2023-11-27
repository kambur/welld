package com.welid.patternrecognitionkambur.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Line {

    public List<Point> points;

    public Line() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
