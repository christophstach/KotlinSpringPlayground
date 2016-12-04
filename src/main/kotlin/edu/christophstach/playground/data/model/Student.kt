/*
 * Copyright (c) 2016 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.christophstach.playground.data.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonManagedReference
import edu.christophstach.playground.controller.StudentController
import org.springframework.hateoas.Identifiable
import org.springframework.hateoas.Link
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import javax.persistence.*

/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */
@Entity
class Student() {
    @Id
    @GeneratedValue
    val id: Long? = 0

    var mn: Long = 0
    var firstName: String = ""
    var lastName: String = ""

    @JsonManagedReference
    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @OrderColumn(name = "pos")
    @JoinTable(
            name = "student_course",
            joinColumns = arrayOf(JoinColumn(name = "student_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "course_id"))
    )
    var courses: MutableSet<Course> = mutableSetOf()

    @JsonCreator
    constructor(mn: Long, firstName: String, lastName: String) : this() {
        this.mn = mn
        this.firstName = firstName
        this.lastName = lastName
    }

    init {
        //add(linkTo(StudentController::class).slash(this.getId()).withSelfRel())
        //add(linkTo(StudentController::class).withRel("all"))
    }

    override fun toString(): String {
        return "Student(id=$id, mn=$mn, firstName='$firstName', lastName='$lastName')"
    }
}