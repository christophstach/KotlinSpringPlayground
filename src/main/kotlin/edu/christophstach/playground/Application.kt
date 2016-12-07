/*
 * Copyright (c) 2016 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.christophstach.playground

import edu.christophstach.playground.data.model.Course
import edu.christophstach.playground.data.model.Student
import edu.christophstach.playground.data.repository.CourseRepository
import edu.christophstach.playground.data.repository.StudentRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class DemoApplication {
    @Bean
    open fun init(
            studentRepository: StudentRepository,
            courseRepository: CourseRepository
    ) = CommandLineRunner {
        val c1 = Course("Programmieren 1", "Variablen, Schleifen, Arrays, Sortierverfahren")
        val c2 = Course("Programmieren 2", "Objektorientierte Programmierung")
        val c3 = Course("Mathe 1", "Ebenen, Vektoren und Matrizen")
        val c4 = Course("Mathe 2", "Folgen und Reihenen, Differentialrechnung, Integralrechnung")


        val s1 = Student(555912, "Christoph", "Stach")
        val s2 = Student(555913, "Laila", "Westphal")
        val s3 = Student(555914, "Justin", "Sprenger")
        val s4 = Student(555915, "Miles", "Lorenz")
        val s5 = Student(555916, "Steffe", "Exler")
        val s6 = Student(555917, "Laura", "Harters")

        s2.courses.add(c1)
        s5.courses.add(c2)
        s5.courses.add(c3)
        s5.courses.add(c4)

        // studentRepository.deleteAll()
        // courseRepository.deleteAll()

        studentRepository.save(s1)
        studentRepository.save(s2)
        studentRepository.save(s3)
        studentRepository.save(s4)
        studentRepository.save(s5)
        studentRepository.save(s6)


    }
}

fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}
