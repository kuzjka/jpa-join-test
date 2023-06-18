package ua.kuzjka.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.model.ProductType;
import ua.kuzjka.jpatest.repository.ProductRepository;
import ua.kuzjka.jpatest.repository.ProductTypeRepository;

import java.util.List;

@Service
@Profile("sub-query")
public class JpaSubQueryImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository typeRepository;

    @Override
    public List<String> getBrandsForType(String typeName) {
        ProductType type = typeRepository.findByName(typeName);
        List<Brand> brands = productRepository.findBrandsForTypeSubQuery(type);
        return brands.stream().map(Brand::getName).toList();
    }
}
