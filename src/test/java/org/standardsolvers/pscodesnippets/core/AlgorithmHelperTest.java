package org.standardsolvers.pscodesnippets.core;

import org.junit.jupiter.api.*;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;

public class AlgorithmHelperTest {
    AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();

    @AfterEach
    void afterEach(){
        algorithmManager.clearCache();
    }

    @Test
    void testFind_whenAlgorithmClassNotCached() {
        Assertions.assertFalse(algorithmManager.isCached("Sample"));
        List<Algorithm> result = algorithmManager.find("Sample");
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertTrue(algorithmManager.isCached("Sample"));

    }
    @Test
    void testFind_whenAlgorithmClassCached() {
        Assertions.assertFalse(algorithmManager.isCached("Sample"));
        algorithmManager.find("Sample");
        List<Algorithm> result = algorithmManager.find("Sample");
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertTrue(algorithmManager.isCached("Sample"));
    }
    @Test
    void testFind_whenAlgorithmClassExists() {
        List<Algorithm> result = algorithmManager.find("Sample");
        Assertions.assertTrue(!result.isEmpty());
    }



    @Test
    public void testIsExists_ValidAlgorithmName_ReturnsTrue() {
        // Replace with a valid algorithm name
        String validAlgorithmName = "Sample";

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
        Assertions.assertTrue(algorithmManager.isCached("Dijkstra"));
    }
}