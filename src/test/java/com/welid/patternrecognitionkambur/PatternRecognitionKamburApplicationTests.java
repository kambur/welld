package com.welid.patternrecognitionkambur;

import com.welid.patternrecognitionkambur.domain.Line;
import com.welid.patternrecognitionkambur.domain.Point;
import com.welid.patternrecognitionkambur.repo.PointRepository;
import com.welid.patternrecognitionkambur.service.LineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = PatternRecognitionKamburApplication.class)
class PatternRecognitionKamburApplicationTests {
	@Autowired
	private LineService lineService;

	@Autowired
	private PointRepository pointRepository;

	@BeforeEach
	public void setup() {

		pointRepository.getPoints().add(new Point(30, 70));
		pointRepository.getPoints().add(new Point(70, 30));
		pointRepository.getPoints().add(new Point(200, 210));
		pointRepository.getPoints().add(new Point(30, 40));
		pointRepository.getPoints().add(new Point(140, 150));
		pointRepository.getPoints().add(new Point(60, 70));
		pointRepository.getPoints().add(new Point(0, 100));
	}

	@Test
	void test1() {

		List<Line> lines = lineService.findLines(4);
		Assertions.assertNotNull(lines);
		Assertions.assertTrue(lines.size() == 1);
		System.out.println(lines.toString());
	}

}
