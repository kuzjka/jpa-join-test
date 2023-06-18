package ua.kuzjka.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.model.Product;
import ua.kuzjka.jpatest.repository.BrandRepository;
import ua.kuzjka.jpatest.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("hash-set")
public class HashSetProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<String> getBrandsForType(String type) {
        List<Product> products = repository.findAll();
        Set<String> brands = new HashSet<>();
        for (Product p : products) {
            if (type.equals(p.getType().getName()))
                brands.add(p.getBrand().getName());
        }

        return new ArrayList<>(brands);
    }
}
