/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import sistema.controller.ProdutoServico;

/**
 *
 * @author thais
 */
public class DAOProdutoServico {
    public void armazenar(ProdutoServico pe){
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
