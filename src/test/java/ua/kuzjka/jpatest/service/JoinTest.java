package ua.kuzjka.jpatest.service;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import ua.kuzjka.jpatest.generator.SelectionWeight;
import ua.kuzjka.jpatest.generator.WeightedSelector;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.model.Product;
import ua.kuzjka.jpatest.model.ProductType;
import ua.kuzjka.jpatest.repository.BrandRepository;
import ua.kuzjka.jpatest.repository.ProductRepository;
import ua.kuzjka.jpatest.repository.ProductTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

@ActiveProfiles("jpa-distinct")
@SpringBootTest
public class JoinTest {
    private static final Logger logger = LoggerFactory.getLogger(JoinTest.class);

    private static final int TYPE_COUNT = 20;
    private static final int BRAND_COUNT = 20;
    private static final int PRODUCT_COUNT = 40000;

    @Autowired
    private ProductTypeRepository typeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private static Random random = new Random();

    private static List<Long> times = new ArrayList<>();

    @AfterAll
    public static void printAverageTime() {
        OptionalDouble avg = times.stream().mapToLong(Long::longValue).average();
        int millis = (int) Math.round(avg.orElseThrow(() -> new IllegalStateException("Cannot get average time")));
        logger.info("Average test time: {} s {} ms", millis / 1000, millis % 1000);
    }

    @BeforeEach
    public void generateData() {
        List<ProductType> types = new ArrayList<>(TYPE_COUNT);
        for (int i = 0; i < TYPE_COUNT; ++i)
            types.add(new ProductType(generateString(8)));
        types = typeRepository.saveAll(types);

        List<Brand> brands = new ArrayList<>(BRAND_COUNT);
        for (int i = 0; i < BRAND_COUNT; ++i)
            brands.add(new Brand(generateString(8)));
        brands = brandRepository.saveAll(brands);

        List<Product> products = new ArrayList<>(PRODUCT_COUNT);
        for (int i = 0; i < TYPE_COUNT; ++i) {
            WeightedSelector<Brand> selector = new WeightedSelector<>(brands, List.of(
                    new SelectionWeight(3, 3),
                    new SelectionWeight(3, 1)
            ));
            for (int j = 0; j < PRODUCT_COUNT / TYPE_COUNT; ++j) {
                products.add(new Product(generateString(16), selector.selectItem(),
                        types.get(i), random.nextInt(10000)));
            }
        }
        productRepository.saveAll(products);
    }

    @AfterEach
    public void removeData() {
        productRepository.deleteAll();
        typeRepository.deleteAll();
        brandRepository.deleteAll();
    }

    @RepeatedTest(20)
    public void test() {
        long start = System.currentTimeMillis();
        List<ProductType> types = typeRepository.findAll();
        for (ProductType type : types) {
            List<String> brands = productService.getBrandsForType(type.getName());
            logger.info("Found {} brands for type {}", brands.size(), type.getName());
        }
        long elapsed = System.currentTimeMillis() - start;
        times.add(elapsed);
        logger.info("Elapsed {} s {} ms", elapsed / 1000, elapsed % 1000);
    }

    /**
     * Generates alphabetic lowercase string.
     * @param length    String length
     * @return          Generated string
     */
    private static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append((char) random.nextInt('a', 'z' + 1));
        }
        return sb.toString();
    }
}
