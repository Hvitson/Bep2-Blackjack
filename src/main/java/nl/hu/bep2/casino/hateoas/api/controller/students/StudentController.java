package nl.hu.bep2.casino.hateoas.api.controller.students;

import nl.hu.bep2.casino.hateoas.api.controller.students.dto.StudentDtoResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExposesResourceFor(StudentDtoResponse.class)
@RestController
@RequestMapping(path = "/student")
public class StudentController {

	/**
	 * Endpoint used to retrieve the student resource.
	 *
	 * @param id The (fictional) id of the student resource to retrieve.
	 * @return A hardcoded student response DTO with a dynamic ID based on the input parameter (ID).
	 */
	@GetMapping("/{id}")
	public StudentDtoResponse get(@PathVariable @NotNull final Long id) {
		StudentDtoResponse studentDtoResponse = new StudentDtoResponse(id, "Henk", "Henksen");
		return applyHateoas(studentDtoResponse);
	}

	/*
	 Normally HATEOAS would be applied in a model mapper or some kind of Spring filter.
	 Since we are mocking away the domain and persistence layer for this demo, we apply HATEOAS within this controller.
	 It is good to note that this goes against best practices as the application of HATEOAS should not be the responsibility of this controller.
	 */
	private StudentDtoResponse applyHateoas(StudentDtoResponse studentDtoResponse) {
		final Link selfLink = linkTo(methodOn(StudentController.class).get(studentDtoResponse.getId())).withSelfRel();
		studentDtoResponse.add(selfLink);
		return studentDtoResponse;
	}

}
