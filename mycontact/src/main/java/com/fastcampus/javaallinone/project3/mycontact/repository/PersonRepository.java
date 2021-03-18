package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // JpaRepository를 extends 하면 @Repository 안 붙여도 됨.
public interface PersonRepository extends JpaRepository<Person, Long> {
}