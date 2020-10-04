package nl.hu.bep2.casino.hateoas.api.controller.students.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/*
 The Student DTO Response object is the container which yields information about our student (his ID, first name and last name).
 This is what we will be serialized and send back to the API caller.
 */
public class StudentDtoResponse extends RepresentationModel<StudentDtoResponse> {
	private final Long id;
	private final String firstName;
	private final String lastName;

	public StudentDtoResponse(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	/*
	 We override the equals method since this DTO extends RepresentationModel,
	  we do not want to include the fields of our super class in the comparison.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		StudentDtoResponse that = (StudentDtoResponse) o;
		return id.equals(that.id) &&
				firstName.equals(that.firstName) &&
				lastName.equals(that.lastName);
	}

	/*
	 Same as for the equals() method.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, firstName, lastName);
	}

}
