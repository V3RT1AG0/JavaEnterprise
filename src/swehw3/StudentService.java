package swehw3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;


//Business logic
public class StudentService {

	String lastname;
	String firstname;
	String address;
	String city;
	String state;
	String zipcode;
	String email;
	String[] likeCampus;
	String interestUniversity;
	String recommend;
	String comments;
	String raffle;
	String phoneNum;
	String date;

	double mean;
	double sd;

	ArrayList<Student> surveyDetailList = new ArrayList<Student>();

	public StudentService() {
	}

	public StudentService(String lastname, String firstname, String address, String city, String state, String zipcode,
			String email, String[] likeCampus, String interestUniversity, String recommend, String comments,
			String raffle, String phoneNum, String date) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.email = email;
		this.likeCampus = likeCampus;
		this.interestUniversity = interestUniversity;
		this.recommend = recommend;
		this.comments = comments;
		this.raffle = raffle;
		this.phoneNum = phoneNum;
		this.date = date;
		this.mean = 0;
		this.sd = 0;
	}

	// return mean
	public double getMeanValue() {
		return mean;
	}
	
	//return Standard Deviation
	public double getSDValue() {
		return sd;
	}

	// Fetch data from DB and return it
	@SuppressWarnings("unchecked")
	public ArrayList<Student> displaySurveyData() throws IOException {
		ArrayList<Student> studentsList = new ArrayList<Student>();

		Session session = Persistence.getSessionFactory().openSession();

		try {

			session.beginTransaction();
			studentsList = (ArrayList<Student>) session.createQuery("FROM Student").list();
			session.getTransaction().commit();

		} catch (HibernateException ex) {

			ex.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			session.close();
		}

		return studentsList;
	}

	
	// Write the form to SQL database
	public void writeToDB() {
		Session session = Persistence.getSessionFactory().openSession();
		String lC = "";
		try {
			session.beginTransaction();

			// Add new student object
			Student s = new Student();
			// s.setId(0);
			s.setFirstname(firstname);
			s.setLastname(lastname);
			s.setAddress(address);
			s.setCity(city);
			s.setState(state);
			s.setZipcode(zipcode);
			s.setEmail(email);
			s.setInterestUniversity(interestUniversity);
			s.setRecommend(recommend);
			s.setComments(comments);
			s.setRaffle(raffle);
			s.setPhoneNum(phoneNum);
			s.setDate(date);

			for (String str : likeCampus) {
				lC += (str + ",");
			}
			
			s.setLikeCampus_db(lC);
			
			session.save(s);

			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// Evaluate mean and standard deviation
	
	public void evaluateRaffle() throws IOException {
		String[] raffleValues = raffle.split(",");
		int i;
		double tmp = 0;
		for (i = 0; i < raffleValues.length; i++)
			mean = mean + Integer.parseInt(raffleValues[i]);
		mean = mean / raffleValues.length;
		for (i = 0; i < raffleValues.length; i++)
			tmp += Math.pow(Integer.parseInt(raffleValues[i]) - mean, 2);
		sd = Math.sqrt(tmp / (raffleValues.length - 1));
	}


}
