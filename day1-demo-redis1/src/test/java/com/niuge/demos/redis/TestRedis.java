package com.niuge.demos.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRedis {
  @Autowired
  private StudentRepository studentRepository;

  @Test
  public void test() {
    Student student = new Student(
        "id001", "张三", Student.Gender.MALE, 1);
    // 测试save:
    studentRepository.save(student);


    // 测试find:
    Student retrievedStudent =
        studentRepository.findById("id001").get();
    System.out.println(retrievedStudent);
    System.out.println(retrievedStudent.getId());


    // 测试delete:
    studentRepository.deleteById(student.getId());


    Student engStudent = new Student(
        "id001", "张三", Student.Gender.MALE, 1);
    Student medStudent = new Student(
        "id002", "李四", Student.Gender.MALE, 2);
    studentRepository.save(engStudent);
    studentRepository.save(medStudent);

    // 测试findAll:
    Iterable<Student> all = studentRepository.findAll();
    for (Student student1 : all) {
      System.out.println(student1.getName());
    }


    // 测试deleteAll:
    studentRepository.deleteAll();
  }
}
