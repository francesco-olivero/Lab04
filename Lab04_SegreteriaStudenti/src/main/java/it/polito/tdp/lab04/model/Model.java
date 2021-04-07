package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;


public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}
	
	public List<String> getTuttiICorsi() {
		List<String> corsi = new LinkedList<String>();
		for (Corso c: corsoDao.getTuttiICorsi()) {
			corsi.add(c.getNome());
		}
		return corsi;
	}
	
	public List<Studente> getTuttiGliStudenti() {
		return this.studenteDao.getTuttiGliStudenti();
	}
	
	public Studente cercaStudente(Integer matricola) {
		Studente result = null; 
		for (Studente s: this.studenteDao.getTuttiGliStudenti()) {
			if (s.getMatricola().equals(matricola))
				result = s;
		}
		return result;
	}
	
	public Corso getCorsoFromNome(String nomeCorso) {
		return this.corsoDao.getCorsoFromNome(nomeCorso);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return this.corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiStudente(Integer matricola) {
		return this.corsoDao.getCorsiStudente(matricola);
	}
	
	public boolean inscriviStudenteACorso(Integer matricola, String nomeCorso) {
		Studente studente = this.studenteDao.getStudenteFromMatricola(matricola);
		Corso corso = this.corsoDao.getCorsoFromNome(nomeCorso);
		if (studente!=null && corso!=null) {
			return this.corsoDao.inscriviStudenteACorso(studente, corso);
		}
		return false;
	}


}
