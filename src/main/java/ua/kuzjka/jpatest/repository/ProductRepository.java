package ua.kuzjka.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.jpatest.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
