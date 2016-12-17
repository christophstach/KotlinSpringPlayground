/*
 * Copyright (c) 2016 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.christophstach.playground.controller


import edu.christophstach.playground.data.model.Course
import edu.christophstach.playground.data.model.Student
import edu.christophstach.playground.data.repository.StudentRepository
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.nio.charset.Charset
import java.util.*


/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/7/16
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTests {
    val studentList: ArrayList<Student> = ArrayList()

    @Autowired
    lateinit var studentRepository: StudentRepository

    val contentType: MediaType = MediaType(
            MediaTypes.HAL_JSON.type,
            MediaTypes.HAL_JSON.subtype,
            Charset.forName("utf8")
    )

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var controller: StudentController

    @Before
    fun setUp() {
        studentRepository.deleteAll()

        val c1 = Course("Programmieren 1", "Variablen, Schleifen, Arrays, Sortierverfahren")
        val c2 = Course("Programmieren 2", "Objektorientierte Programmierung")
        val c3 = Course("Mathe 1", "Ebenen, Vektoren und Matrizen")
        val c4 = Course("Mathe 2", "Folgen und Reihenen, Differentialrechnung, Integralrechnung")


        val s1 = Student(555912, "Christoph", "Stach")
        val s2 = Student(555913, "Laila", "Westphal")
        val s3 = Student(555914, "Justin", "Sprenger")
        val s4 = Student(555915, "Miles", "Lorenz")
        val s5 = Student(555916, "Steffen", "Exler")
        val s6 = Student(555917, "Laura", "Harters")

        s2.courses.add(c1)
        s5.courses.add(c2)
        s5.courses.add(c3)
        s5.courses.add(c4)

        studentList.add(studentRepository.save(s1))
        studentList.add(studentRepository.save(s2))
        studentList.add(studentRepository.save(s3))
        studentList.add(studentRepository.save(s4))
        studentList.add(studentRepository.save(s5))
        studentList.add(studentRepository.save(s6))
    }

    @Test
    fun contextLoads() {
        assertThat(controller).isNotNull()
    }

    @Test
    fun findAll() {
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.studentResourceList").isArray)
                .andExpect(jsonPath("$._embedded.studentResourceList", hasSize<Student>(6)))

                .andExpect(jsonPath("$._embedded.studentResourceList[0]._data.id", `is`(studentList[0].id.toString())))
                .andExpect(jsonPath("$._embedded.studentResourceList[0]._data.firstName", `is`(studentList[0].firstName)))
                .andExpect(jsonPath("$._embedded.studentResourceList[0]._data.lastName", `is`(studentList[0].lastName)))
                .andExpect(jsonPath("$._embedded.studentResourceList[0]._data.courses", hasSize<Student>(studentList[0].courses.size)))
                .andExpect(jsonPath("$._embedded.studentResourceList[0]._links.courses").doesNotExist())

                .andExpect(jsonPath("$._embedded.studentResourceList[4]._data.id", `is`(studentList[4].id.toString())))
                .andExpect(jsonPath("$._embedded.studentResourceList[4]._data.firstName", `is`(studentList[4].firstName)))
                .andExpect(jsonPath("$._embedded.studentResourceList[4]._data.lastName", `is`(studentList[4].lastName)))
                .andExpect(jsonPath("$._embedded.studentResourceList[4]._data.courses", hasSize<Student>(studentList[4].courses.size)))
                .andExpect(jsonPath("$._embedded.studentResourceList[4]._links.courses", hasSize<Student>(studentList[4].courses.size)))
    }

    @Test
    fun findOne() {
        mockMvc.perform(get("/student/" + studentList[0].id))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._data.id", `is`(studentList[0].id.toString())))
                .andExpect(jsonPath("$._data.firstName", `is`(studentList[0].firstName)))
                .andExpect(jsonPath("$._data.lastName", `is`(studentList[0].lastName)))
                .andExpect(jsonPath("$._data.courses", hasSize<Student>(studentList[0].courses.size)))
                .andExpect(jsonPath("$._links.courses").doesNotExist())
    }
}
