package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository // JpaRepository를 extends 하면 @Repository 안 붙여도 됨.
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    // index 위치 지정
//    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1 and person.birthday.dayOfBirthday = ?2")
//    List<Person> findByMonthOfBirthday(int monyhOfBirthday, int dayOfBirthday);

    //jpql Query 활용한 것.
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monyhOfBirthday);

    // native Query 활용 요건 (참고하기)
//    @Query(value = "select * from person where month_of_birthday = :monthOfBirthday and day_of_birthday = :dayOfBirthday", nativeQuery = true)
//    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monyhOfBirthday, @Param("dayOfBirthday") int dayOfBirthday);

    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();
}