package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private WebClient.Builder webClient;
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData> getCollegeDetails(@PathVariable("id") Long studentId)
	{
		ResponseData response=new ResponseData();

		Student s1=new Student();
		s1.setStudentId(1l);
		s1.setStudentName("nandini");
		s1.setAddress("bangalore");
		s1.setCollegeId(1l);
		response.setStudent(s1);
		Long collegeId=s1.getCollegeId();
	/* 	RestTemplate template=new RestTemplate();
		String endPoint="http://localhost:8082/college/{collegeId}";
		ResponseEntity<College> data=template.getForEntity(endPoint, College.class, collegeId);
		if(data.getStatusCodeValue()==200) {
			College college=data.getBody();
			response.setCollege(college);
		}
		*/


		College c1=webClient.build()
				.get()
				.uri("http://localhost:8082/college/"+collegeId)
				.retrieve()
				.bodyToMono(College.class)
				.block();
		response.setCollege(c1);
		return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
	}

}
