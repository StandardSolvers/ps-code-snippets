package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.*;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class AlgorithmHelperTest {

    public static class MockAlgorithm implements Algorithm {
        @Override
        public <T> Algorithm set(String key, T value) {
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
        algorithmHelper.clearCache();
    }

    @Test
    void testFind_whenAlgorithmClassNotCached() {
        Assertions.assertFalse(algorithmHelper.isCached(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmHelper.isCached(mockAlgorithmClass));

    }
    @Test
    void testFind_whenAlgorithmClassCached() {
        algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertTrue(algorithmHelper.isCached(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmHelper.isCached(mockAlgorithmClass));
    }
    @Test
    void testFind_whenAlgorithmClassExists() {
        Optional<Algorithm> result = algorithmHelper.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
    }



    @Test
    public void testIsExists_ValidAlgorithmName_ReturnsTrue() {
        AlgorithmHelper helper = AlgorithmHelper.getInstance();

        // Replace with a valid algorithm name
        String validAlgorithmName = "SampleAlgorithm";

        Assertions.assertTrue(helper.isExists(validAlgorithmName));
    }

    @Test
    public void testIsExists_InvalidAlgorithmName_ReturnsFalse() {
        AlgorithmHelper helper = AlgorithmHelper.getInstance();

        // Replace with an invalid algorithm name
        String invalidAlgorithmName = "InvalidAlgorithm";

        Assertions.assertFalse(helper.isExists(invalidAlgorithmName));
    }


    @Test
    void testFindByName_AlgorithmNotExists() {
        AlgorithmHelper helper = AlgorithmHelper.getInstance();

        List<Algorithm> result = helper.find("InvalidAlgorithm");

        // The algorithm does not exist, so the result should be Optional.empty
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testFindByName_AlgorithmExistsNotCached() {
        AlgorithmHelper helper = AlgorithmHelper.getInstance();

        // Assuming "ExistingAlgorithm" is an algorithm that exists, but is not cached
        List<Algorithm> result = helper.find("Dijkstra");

        // Since algorithm exists, the returned value should not be empty. Check result.isPresent()
        Assertions.assertTrue(!result.isEmpty());
        // Also it should now be cached.
        Assertions.assertTrue(helper.isCached(result.get(0).getClass()));
    }
}