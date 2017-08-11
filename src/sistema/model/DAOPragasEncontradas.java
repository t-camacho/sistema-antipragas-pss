/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import sistema.controller.PragasEncontradas;

/**
 *
 * @author thais
 */
public class DAOPragasEncontradas {
    
    public void armazenar(PragasEncontradas pe){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction();
            session.save(pe);
            session.getTransaction().commit();
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
    }
}
