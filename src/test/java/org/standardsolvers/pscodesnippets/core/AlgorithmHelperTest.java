package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.Optional;

public class AlgorithmHelperTest {

    // 맵에 없을때 -> 캐싱 확인
    // 맵에 있을때
    // 없는 클래스 요청 -> Optional.empty()
    @Test
    void testFind_whenAlgorithmClassExists() {
        Class<MockAlgorithm> algorithmClass = MockAlgorithm.class;
        Algorithm algorithm = AlgorithmHelper.getInstance().find(algorithmClass).orElse(null);
        Assertions.assertNotNull(algorithm);
        Assertions.assertTrue(algorithm instanceof MockAlgorithm);
    }

    @Test
    void testFind_whenAlgorithmDoesNotExist() {
        Class<MockAlgorithm> algorithmClass = MockAlgorithm.class;
        Optional<Algorithm> algorithm = AlgorithmHelper.getInstance().find(algorithmClass);
        Assertions.assertTrue(algorithm.isEmpty());
    }

    static class MockAlgorithm implements Algorithm {
        @Override
        public <T> Algorithm setProperty(String key, T value) {
            return this;
        }

        @Override
        public String getContext() {
            return null;
        }
    }

}