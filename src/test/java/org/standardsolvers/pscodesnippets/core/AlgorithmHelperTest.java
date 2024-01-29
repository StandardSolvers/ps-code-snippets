package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.*;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class AlgorithmHelperTest {

    public static class MockAlgorithm implements Algorithm {
        @Override
        public <T> Algorithm setProperty(String key, T value) {
            return this;
        }

        @Override
        public String getContext() {
            return null;
        }
    }

    Class<MockAlgorithm> mockAlgorithmClass = MockAlgorithm.class;
    Algorithm mockAlgorithm;
    AlgorithmHelper algorithmHelper = AlgorithmHelper.getInstance();

    @BeforeEach
    void beforeEach() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        mockAlgorithm = mockAlgorithmClass.getConstructor().newInstance();
    }

    @AfterEach
    void afterEach(){
        algorithmHelper.clearMap();
    }

    @Test
    void testFind_whenAlgorithmClassExistsNotInCache() {
        Assertions.assertFalse(algorithmHelper.isExists(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmHelper.isExists(mockAlgorithmClass));

    }
    @Test
    void testFind_whenAlgorithmClassExistsInCache() {
        algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertTrue(algorithmHelper.isExists(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmHelper.isExists(mockAlgorithmClass));
    }
    @Test
    void testFind_whenAlgorithmClassExists() {
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
    }

}