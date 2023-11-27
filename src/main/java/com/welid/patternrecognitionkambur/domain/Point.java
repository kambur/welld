package com.welid.patternrecognitionkambur.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Point implements Comparable<Point>{

    private int x, y;

    public double slopeTo(Point that) {
        if (this.x == that.x) {
            // vertical line case
            return Double.POSITIVE_INFINITY;
        } else {
            return (double) (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    public int compareTo(Point inP) {
        if (y < inP.y || (y == inP.y && x < inP.x)) {
            return -1;
        }
        else if (y == inP.y && x == inP.x) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
