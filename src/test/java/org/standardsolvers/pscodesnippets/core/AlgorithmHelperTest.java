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
    AlgorithmManager algorithmManager = SimpleAlgorithmManagerImplement.getInstance();

    @BeforeEach
    void beforeEach() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        mockAlgorithm = mockAlgorithmClass.getConstructor().newInstance();
    }

    @AfterEach
    void afterEach(){
        algorithmManager.clearCache();
    }

    @Test
    void testFind_whenAlgorithmClassNotCached() {
        Assertions.assertFalse(algorithmManager.isCached(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmManager.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmManager.isCached(mockAlgorithmClass));

    }
    @Test
    void testFind_whenAlgorithmClassCached() {
        algorithmManager.find(mockAlgorithmClass);
        Assertions.assertTrue(algorithmManager.isCached(mockAlgorithmClass));
        Optional<Algorithm> result = algorithmManager.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
        Assertions.assertTrue(algorithmManager.isCached(mockAlgorithmClass));
    }
    @Test
    void testFind_whenAlgorithmClassExists() {
        Optional<Algorithm> result = algorithmManager.find(mockAlgorithmClass);
        Assertions.assertNotNull(result.orElse(null));
        Assertions.assertEquals(result.orElse(null).getContext(), mockAlgorithm.getContext());
    }



    @Test
    public void testIsExists_ValidAlgorithmName_ReturnsTrue() {
        // Replace with a valid algorithm name
        String validAlgorithmName = "SampleAlgorithm";

        Assertions.assertTrue(algorithmManager.isExists(validAlgorithmName));
    }

    @Test
    public void testIsExists_InvalidAlgorithmName_ReturnsFalse() {
        // Replace with an invalid algorithm name
        String invalidAlgorithmName = "InvalidAlgorithm";

        Assertions.assertFalse(algorithmManager.isExists(invalidAlgorithmName));
    }


    @Test
    void testFindByName_AlgorithmNotExists() {
        List<Algorithm> result = algorithmManager.find("InvalidAlgorithm");

        // The algorithm does not exist, so the result should be Optional.empty
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testFindByName_AlgorithmExistsNotCached() {
        // Assuming "ExistingAlgorithm" is an algorithm that exists, but is not cached
        List<Algorithm> result = algorithmManager.find("Dijkstra");

        // Since algorithm exists, the returned value should not be empty. Check result.isPresent()
        Assertions.assertTrue(!result.isEmpty());
        // Also it should now be cached.
        Assertions.assertTrue(algorithmManager.isCached(result.get(0).getClass()));
    }
}