package ua.kuzjka.jpatest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.model.Product;
import ua.kuzjka.jpatest.model.ProductType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RelationshipTests {
    @Autowired
    private ProductTypeRepository typeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testRelationships() {
        Brand brand = new Brand("Samsung");
        brand = brandRepository.save(brand);

        ProductType type = new ProductType("TV");
        type = typeRepository.save(type);

        Product product = new Product("QLED 8000", brand, type, 1000);
        product = productRepository.save(product);

        int id = product.getId();

        Optional<Product> opt = productRepository.findById(id);
        assertTrue(opt.isPresent());

        Product result = opt.get();
        assertEquals("QLED 8000", result.getName());
        assertEquals("Samsung", result.getBrand().getName());
        assertEquals("TV", result.getType().getName());
    }
}
