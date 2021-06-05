package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;
    @Test
    void getPeopleByNames(){
        Mockito.when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);
        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put(){
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
        personService.put(dto);

        //void 인 method에 대해서 test 하는 Mokito의 특이한 방법
        Mockito.verify(personRepository, Mockito.times(1)).save(ArgumentMatchers.any(Person.class));
    }

    @Test
    void modifyIfPersonNotFound(){
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> personService.modify(1L, mockPersonDto()));

    }

    @Test
    void modifyIfNameIsDifferent(){
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RuntimeException.class, ()->personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify(){
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, mockPersonDto());

//        Mockito.verify(personRepository, Mockito.times(1)).save(ArgumentMatchers.any(Person.class));
        Mockito.verify(personRepository, Mockito.times(1)).save(ArgumentMatchers.argThat(new IsPersonWillBeUpdated()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(),"programming")
//                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }

        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }
}