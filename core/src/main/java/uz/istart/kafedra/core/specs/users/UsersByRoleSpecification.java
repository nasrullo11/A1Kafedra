package uz.istart.kafedra.core.specs.users;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.entities.UserEntity_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UsersByRoleSpecification implements Specification<UserEntity> {

    private List<Role> roles;
    private String searchText;

    public UsersByRoleSpecification(List<Role> roles, String searchText){
        this.roles = roles;
        this.searchText = searchText;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();
        if(roles!=null){
            List<Predicate> predicateListOr = new ArrayList<>();
            roles.forEach(role ->
                    predicateListOr.add(builder.equal(root.get(UserEntity_.role), role)));
            predicateList.add(builder.or(predicateListOr.toArray(new Predicate[predicateListOr.size()])));
        }

        if (!StringUtils.isEmpty(searchText)) {
            List<Predicate> predicateListOr = new ArrayList<>();
            predicateListOr.add(builder.equal(builder.lower(root.get(UserEntity_.username)), "%" + searchText.toLowerCase() + "%"));
            predicateListOr.add(builder.like(builder.lower(root.get(UserEntity_.fullName)), "%" + searchText.toLowerCase() + "%"));

            predicateList.add(builder.or(predicateListOr.toArray(new Predicate[predicateListOr.size()])));
        }

        return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
