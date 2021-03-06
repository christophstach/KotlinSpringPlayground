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
import edu.christophstach.playground.data.service.CourseService
import edu.christophstach.playground.hateoas.assembler.CourseResourceAssembler
import edu.christophstach.playground.hateoas.resource.CourseResource
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.PagedResources
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */
@RestController
@RequestMapping("/course")
@ExposesResourceFor(Course::class)
class CourseController(
        val courseService: CourseService,
        val courseResourceAssembler: CourseResourceAssembler,
        val pagedResourcesAssembler: PagedResourcesAssembler<Course>
) {

    @GetMapping(
            produces = arrayOf("application/hal+json")
    )
    fun findAll(pageable: Pageable): PagedResources<CourseResource> {
        return pagedResourcesAssembler.toResource(courseService.findAll(pageable), courseResourceAssembler)
    }

    @GetMapping(
            "/{id}",
            produces = arrayOf("application/hal+json")
    )
    fun findOne(@PathVariable("id") id: UUID): CourseResource {
        return courseResourceAssembler.toResource(courseService.findOne(id))
    }

    @PostMapping(
            produces = arrayOf("application/hal+json"),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun create(@RequestBody course: Course): CourseResource {
        return courseResourceAssembler.toResource(courseService.create(course))
    }

    @PutMapping(
            produces = arrayOf("application/hal+json"),
            consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun update(@PathVariable("id") id: UUID, @RequestBody course: Course): CourseResource {
        return courseResourceAssembler.toResource(courseService.update(id, course))
    }

    @DeleteMapping(
            "/{id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE)
    )
    fun delete(id: UUID): Course {
        return courseService.delete(id)
    }
}
