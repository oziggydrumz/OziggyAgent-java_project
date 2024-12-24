package com.oziggyagent.oziggyagent.repo;

import com.oziggyagent.oziggyagent.model.MyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface LocationRepo extends JpaRepository<MyLocation,Long> {
    MyLocation findByState(String state);
    MyLocation findById(long id);
}
