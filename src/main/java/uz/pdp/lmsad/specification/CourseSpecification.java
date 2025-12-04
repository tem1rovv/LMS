package uz.pdp.lmsad.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.pdp.lmsad.dto.course.CourseDto;
import uz.pdp.lmsad.entity.Course;

public class CourseSpecification {

    public static Specification<Course> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("title")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Course> minPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Course> maxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}

