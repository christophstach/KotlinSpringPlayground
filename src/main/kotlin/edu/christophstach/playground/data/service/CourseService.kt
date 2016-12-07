/*
 * Copyright (c) 2016 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.christophstach.playground.data.service

import edu.christophstach.playground.data.model.Course
import edu.christophstach.playground.data.repository.CourseRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */
@Service
class CourseService(val courseRepository: CourseRepository) {
    fun findAll() = courseRepository.findAll()
    fun findAll(pageable: Pageable) = courseRepository.findAll(pageable)
    fun findOne(id: UUID) = courseRepository.findOne(id)

    fun create(course: Course): Course {
        if (course.id == null) {
            return courseRepository.save(course)
        } else {
            throw IllegalArgumentException("Course id must be null")
        }
    }

    fun update(id: UUID, course: Course): Course {
        if (courseRepository.findOne(id) != null) {
            return courseRepository.save(course)
        } else {
            throw IllegalArgumentException("Course does not exists")
        }
    }

    fun delete(id: UUID): Course {
        val course: Course? = courseRepository.findOne(id)

        if (course != null) {
            courseRepository.delete(course)
            return course
        } else {
            throw IllegalArgumentException("Course does not exists")
        }
    }
}