package ua.kuzjka.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.kuzjka.jpatest.model.Brand;
import ua.kuzjka.jpatest.model.Product;
import ua.kuzjka.jpatest.model.ProductType;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByTypeName(String name);

    @Query("select b from Brand b join b.products p where p in (select p from Product p where p.type=:type)")
    List<Brand> findBrandsForTypeSubQuery(ProductType type);

    @Query("select distinct p.brand from Product p where p.type.name = :name")
    List<Brand> findBrandsForTypeName(String name);
}
