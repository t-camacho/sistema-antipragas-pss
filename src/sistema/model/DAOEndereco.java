/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sistema.controller.Endereco;

/**
 *
 * @author thais
 */
public class DAOEndereco {
    public void armazenar(Endereco endereco) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction(); 
            session.save(endereco);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Endereco armazenado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        
    }
    
    public void listar(DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Endereco").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Endereco end = ((Endereco) it.next());
                modelo.addRow(new Object[]{
                    end.getIdEndereco(),
                    end.getRuaAven(),
                    end.getNumero(),
                    end.getCep(),
                    end.getComplemento()
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
            List c = session.createQuery("From Endereco").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Endereco end = ((Endereco) it.next());
                modelo.addItem(end.getIdEndereco() + " - " +end.getRuaAven() + "/Num " + end.getNumero() + "/CEP " + end.getCep());
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
            List listaPragas = session.createQuery("From Endereco c where upper(c.ruaAven) like '"+ nome + "%'").list();
                for(Iterator it2 = listaPragas.iterator();it2.hasNext();){
                    Endereco end = ((Endereco) it2.next());
                    modelo.addRow(new Object[]{
                        end.getIdEndereco(),
                        end.getRuaAven(),
                        end.getNumero(),
                        end.getCep(),
                        end.getComplemento()
                    });
                 }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Endereco buscarComID(int idEnd){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaEnd = session.createQuery("From Endereco where idEndereco = " + idEnd).list();
         
            return (Endereco) listaEnd.get(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void alterar(Endereco end) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaEnd = session.createQuery("From Endereco where idEndereco = " + end.getIdEndereco()).list();
            Endereco e = (Endereco) listaEnd.get(0);
            System.out.println(listaEnd.size());
            System.out.println(end.getCep());
            e.setCep(end.getCep());
            e.setComplemento(end.getComplemento());
            e.setRuaAven(end.getRuaAven());
            e.setNumero(end.getNumero());
            session.merge(e);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Endereco alterado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void excluir(int idEnd) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Endereco end = (Endereco)session.get(Endereco.class, idEnd);
            session.delete(end);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Endereco excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
}
