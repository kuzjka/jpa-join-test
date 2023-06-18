package ua.kuzjka.jpatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.kuzjka.jpatest.model.Product;
import ua.kuzjka.jpatest.repository.BrandRepository;
import ua.kuzjka.jpatest.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("filtered-hash-set")
public class FIlteredHashSetImpl implements ProductService{
    @Autowired
    private ProductRepository repository;

    @Override
    public List<String> getBrandsForType(String type) {
        List<Product> products = repository.findAllByTypeName(type);
        Set<String> brands = new HashSet<>();
        products.forEach(p -> brands.add(p.getBrand().getName()));
        return new ArrayList<>(brands);
    }
}
