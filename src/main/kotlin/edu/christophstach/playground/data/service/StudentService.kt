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

import edu.christophstach.playground.data.model.Student
import edu.christophstach.playground.data.repository.StudentRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */

@Service
class StudentService(val studentRepository: StudentRepository) {
    fun findAll() = studentRepository.findAll()
    fun findAll(pageable: Pageable) = studentRepository.findAll(pageable)
    fun findOne(id: UUID) = studentRepository.findOne(id)

    fun create(student: Student): Student {
        if (student.id == null) {
            return studentRepository.save(student)
        } else {
            throw IllegalArgumentException("Student id must be null")
        }
    }

    fun update(id: UUID, student: Student): Student {
        if (studentRepository.findOne(id) != null) {
            return studentRepository.save(student)
        } else {
            throw IllegalArgumentException("Student does not exists")
        }
    }

    fun delete(id: UUID): Student {
        val student: Student? = studentRepository.findOne(id)

        if (student != null) {
            studentRepository.delete(student)
            return student
        } else {
            throw IllegalArgumentException("Student does not exist")
        }
    }
}

