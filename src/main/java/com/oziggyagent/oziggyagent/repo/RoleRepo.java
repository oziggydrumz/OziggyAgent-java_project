package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.MyRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepo extends JpaRepository <MyRoles,Long> {
    Optional <MyRoles> findByTitle(String name);
    MyRoles findById(long id);

}
