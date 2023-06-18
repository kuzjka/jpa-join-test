package ua.kuzjka.jpatest.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<String> getBrandsForType(String type);
}
