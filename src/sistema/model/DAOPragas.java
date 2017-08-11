/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Pragas;
import sistema.controller.Produto;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author root
 */
public class DAOPragas {
    public void listar(DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Pragas").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Pragas pragas = ((Pragas) it.next());
                modelo.addRow(new Object[]{
                    pragas.getIdPragas(),
                    pragas.getNome()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public void listar(JComboBox<String> modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Pragas").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Pragas end = ((Pragas) it.next());
                modelo.addItem(end.getIdPragas() + " - " + end.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public void listarComLike(String nome, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaPragas = session.createQuery("From Pragas c where upper(c.nome) like '"+ nome + "%'").list();
                for(Iterator it2 = listaPragas.iterator();it2.hasNext();){
                    Pragas pragas = ((Pragas) it2.next());
                    modelo.addRow(new Object[]{
                        pragas.getIdPragas(),
                        pragas.getNome(),
                    });
                 }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Pragas buscarComID(int idPraga){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaPragas = session.createQuery("From Pragas where idPragas = " + idPraga).list();
         
            return (Pragas) listaPragas.get(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(Pragas pragas) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction();
            session.save(pragas);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Pragas armazenada com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
        
    }
    
    public void alterar(Pragas p) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();                  
            Pragas pragas = (Pragas)session.createQuery("From Pragas where idPragas = " + p.getIdPragas()).list().get(0);
            
            pragas.setNome(p.getNome());
            session.merge(pragas);
            session.getTransaction().commit();  
            JOptionPane.showMessageDialog(null, "Pragas alterado com sucesso");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
    
    public void excluir(int idPragas) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Pragas pragas = (Pragas)session.get(Pragas.class, idPragas);
            session.delete(pragas);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Pragas excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
}
