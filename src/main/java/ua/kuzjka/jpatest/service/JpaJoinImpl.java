package ua.kuzjka.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.repository.ProductRepository;

import java.util.List;

@Service
@Profile("jpa-distinct")
public class JpaJoinImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<String> getBrandsForType(String type) {
        List<Brand> brands = repository.findBrandsForTypeName(type);
        return brands.stream().map(Brand::getName).toList();
    }
}
