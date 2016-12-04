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
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.ResourceSupport
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Christoph Stach - s0555912@htw-berlin.de
 * @since 12/2/16
 */
@RestController
@RequestMapping("/course", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, "application/hal+json"))
class CourseController(
        val courseService: CourseService,
        val courseResourceAssembler: CourseResourceAssembler,
        val pagedResourcesAssembler: PagedResourcesAssembler<Course>
) {

    @GetMapping()
    fun findAll(pageable: Pageable): ResourceSupport {
        return pagedResourcesAssembler.toResource(courseService.findAll(pageable), courseResourceAssembler)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable("id") id: Long) = courseResourceAssembler.toResource(courseService.findOne(id))
}
