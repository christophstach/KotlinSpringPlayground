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
import edu.christophstach.playground.data.service.StudentService
import edu.christophstach.playground.hateoas.assembler.StudentResourceAssembler
import edu.christophstach.playground.hateoas.resource.StudentResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.PagedResources
import org.springframework.http.MediaType
import org.springframework.hateoas.MediaTypes
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */
@RestController
@RequestMapping("/student")
@ExposesResourceFor(Course::class)
class StudentController(
        val studentService: StudentService,
        val studentResourceAssembler: StudentResourceAssembler,
        val pagedResourcesAssembler: PagedResourcesAssembler<Student>
) {
    @GetMapping(
            produces = arrayOf(MediaTypes.HAL_JSON_VALUE)
    )
    fun findAll(pageable: Pageable): PagedResources<StudentResource> {
        return pagedResourcesAssembler.toResource(studentService.findAll(pageable), studentResourceAssembler)
    }

    @GetMapping(
            "/{id}",
            produces = arrayOf()
    )
    fun findOne(@PathVariable("id") id: UUID): StudentResource {
        return studentResourceAssembler.toResource(studentService.findOne(id))
    }

    @PostMapping(
            produces = arrayOf(MediaTypes.HAL_JSON_VALUE),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun create(@RequestBody student: Student): StudentResource {
        return studentResourceAssembler.toResource(studentService.create(student))
    }

    @PutMapping(
            "/{id}",
            produces = arrayOf(MediaTypes.HAL_JSON_VALUE),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun update(@PathVariable("id") id: UUID, @RequestBody student: Student): StudentResource {
        return studentResourceAssembler.toResource(studentService.update(id, student))
    }

    @DeleteMapping(
            "/{id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun delete(@PathVariable("id") id: UUID): Student {
        return studentService.delete(id)
    }

}