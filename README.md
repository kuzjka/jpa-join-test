# ProductType - Product - Brand query problem

`Product` entity has many-to-one relationship with both `ProductType` and `Brand` entities.
The target is to get the subset of brands, which have products of a given product type.

This function of interest is defined in `ProductService` interface. Several implementations are compared
with the performance test `JoinTest`

## Test arrangement

The test generates types, brands and products, with some constraints to make target function return a small subset of brands.

Each test calls the target function for each type once. The test is repeated 20 times and average time is calculated. 

## Test Run results

Embedded H2 DB with file storage.
20 types, 20 brands, 40000 products.
For each type 6 brands are used during generation with probabilities [0.25, 0.25, 0.25, 0.083, 0.083, 0.083]

| Approach                                                           | Spring profile    | Average elapsed time per test |
|--------------------------------------------------------------------|-------------------|-------------------------------|
| Get all products from DB, filter and save brand names in `HashSet` | hash-set          | 1 s 965 ms                    |
| Get only products of given type, save brand names in `HashSet`     | filtered-hash-set | 333 ms                        |
| JPA query with join and sub-query (Anton's implementation)         | sub-query         | 230 ms                        |
| JPA query with a single table and `distinct` keyword               | jpa-distinct      | 193 ms                        |

