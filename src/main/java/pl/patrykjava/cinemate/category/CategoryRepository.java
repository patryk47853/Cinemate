package pl.patrykjava.cinemate.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryByCategoryName(String name);
    Optional<Category> findByCategoryName(String name);
    void deleteByCategoryName(String name);
    boolean existsCategoryById(Long id);

}
