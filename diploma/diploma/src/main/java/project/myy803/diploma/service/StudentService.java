package project.myy803.diploma.service;

import project.myy803.diploma.model.Application;
import project.myy803.diploma.model.Student;

public interface StudentService {
	public void save(Student student);
	public Application findBySubjectIdAndStudentId(int subjectId, int studentId);
	public void applyToSubject(Student student, int subjectId);
}
