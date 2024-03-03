package pl.patrykjava.cinemate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.cinemate.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
