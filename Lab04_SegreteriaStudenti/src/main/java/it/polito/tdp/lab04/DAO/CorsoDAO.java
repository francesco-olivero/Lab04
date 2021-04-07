package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				
				Corso temp = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(temp);
				
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorsoFromNome(String nomeCorso) {
		List<Corso> corsi = getTuttiICorsi();
		for (Corso c: corsi) {
			if (nomeCorso.equals(c.getNome()))
				return c;
		}
		return null;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql =    "SELECT DISTINCT s.`matricola`, s.`cognome`, s.`nome`, s.`CDS` "
							+ "FROM studente s, iscrizione i "
							+ "WHERE s.`matricola`=i.`matricola` AND i.`codins`=?";
		
		List<Studente> result = new ArrayList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), 
						rs.getString("nome"), rs.getString("CDS"));
				result.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	/*
	 * Ottengo tutti i corsi a cui è iscritto uno studente
	 */
	public List<Corso> getCorsiStudente(Integer matricola) {

		final String sql =    "SELECT * "
							+ "FROM corso c, iscrizione i "
							+ "WHERE c.`codins`=i.`codins` AND i.`matricola`=?";

		List<Corso> corsiStudente = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				
				Corso temp = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsiStudente.add(temp);
				
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}

			conn.close();
			
			return corsiStudente;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	

	/*
	 * Data una matricola ed il codice insegnamento, verifica se già iscritto, 
	 * altrimenti iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {

			String sql = "SELECT * "
					+ "FROM iscrizione i "
					+ "WHERE i.`codins`=? AND i.`matricola`=?";
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, corso.getCodins());
				st.setInt(2, studente.getMatricola());
				ResultSet rs = st.executeQuery();
				if(rs.next()) {
					rs.close();
					st.close();
					conn.close();
					return true;
				} else{
					rs.close();
					st.close();
					conn.close();
					return false;
				}
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

}
