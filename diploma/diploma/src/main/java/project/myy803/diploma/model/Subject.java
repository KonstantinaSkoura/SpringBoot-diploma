package project.myy803.diploma.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="subjects")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String objectives;
	
	@ManyToOne
    @JoinColumn(name="professor_id")
	private Professor professor;
	
	@OneToMany(mappedBy="subject")
	private List<Application> applications;
	
	@OneToOne(mappedBy = "subject")
    private Thesis thesis;
	
	public Subject() {
		
	}
	
	public Subject(int id, String title, String objectives, Professor professor, List<Application> applications, Thesis thesis) {
		this.id = id;
		this.title = title;
		this.objectives = objectives;
		this.professor = professor;
		this.applications = applications;
		this.thesis = thesis;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public Thesis getThesis() {
		return thesis;
	}

	public void setThesis(Thesis thesis) {
		this.thesis = thesis;
	}
	
	@Override
	public String toString() {
		return "Subject [id=" + id + ", title=" + title + ", objectives=" + objectives + ", professorId=" + professor.getId() + "]";
	}
}
