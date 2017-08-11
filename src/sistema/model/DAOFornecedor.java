/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Endereco;
import sistema.controller.Fornecedor;
import sistema.controller.Telefone;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rickh
 */
public class DAOFornecedor {
    
    public List<Fornecedor> listar(){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            return session.createQuery("From Fornecedor").list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void listar(DefaultTableModel modelo)
    {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Fornecedor").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Fornecedor f = ((Fornecedor) it.next());
                modelo.addRow(new Object[]{
                    f.getIdFornecedor(),
                    f.getNome(),
                    f.getEndereco().getIdEndereco() + " - " + f.getEndereco().getRuaAven() + "/Num " + f.getEndereco().getNumero() + "/CEP " + f.getEndereco().getCep()
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
            List c = session.createQuery("From Fornecedor").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Fornecedor end = ((Fornecedor) it.next());
                modelo.addItem(end.getIdFornecedor() + " - " +end.getNome());
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
            List listaFornecedores = session.createQuery("From Fornecedor c where upper(c.nome) like '"+ nome + "%'").list();
            
            for(Iterator it = listaFornecedores.iterator();it.hasNext();){
                Fornecedor fornecedor = ((Fornecedor) it.next());
                    modelo.addRow(new Object[]{
                        fornecedor.getIdFornecedor(),
                        fornecedor.getNome(),
                        fornecedor.getEndereco().getIdEndereco() + " - " + fornecedor.getEndereco().getRuaAven() + "/Num " + fornecedor.getEndereco().getNumero() + "/CEP " + fornecedor.getEndereco().getCep()
                    });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Fornecedor buscarComID(int idFornecedor){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaidFornecedor = session.createQuery("From Fornecedor where idFornecedor = " + idFornecedor).list();
            
            Fornecedor f = (Fornecedor) listaidFornecedor.get(0);
            Fornecedor fu = new Fornecedor();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
            
            fu.setIdFornecedor(f.getIdFornecedor());
            fu.setNome(f.getNome());
            
            endereco.setIdEndereco(f.getEndereco().getIdEndereco());
            endereco.setComplemento(f.getEndereco().getComplemento());
            endereco.setCep(f.getEndereco().getComplemento());
            endereco.setNumero(f.getEndereco().getNumero());
            endereco.setRuaAven(f.getEndereco().getRuaAven());
            
            telefone.setIdTelefone(f.getTelefone().getIdTelefone());
            telefone.setTelCelular(f.getTelefone().getTelCelular());
            telefone.setTelFixo(f.getTelefone().getTelFixo());
            
            fu.setEndereco(endereco);
            fu.setTelefone(telefone);
            return fu;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(Fornecedor fornecedor) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction(); 
            //session.persist(fornecedor.getEndereco());
            session.persist(fornecedor.getTelefone());
            session.save(fornecedor);
            session.save(fornecedor);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Fornecedor armazenado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
        
    }
    
    public void alterar(Fornecedor fornecedor) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaFornecedor = session.createQuery("From Fornecedor where idFornecedor = " + fornecedor.getIdFornecedor()).list();
            Fornecedor c = (Fornecedor) listaFornecedor.get(0);
            
            c.setNome(fornecedor.getNome());
           
            c.getTelefone().setTelFixo(fornecedor.getTelefone().getTelFixo());
            c.getTelefone().setTelCelular(fornecedor.getTelefone().getTelCelular());
           
            
            c.setEndereco(fornecedor.getEndereco());
            
            session.merge(c);
            session.getTransaction().commit();  
            JOptionPane.showMessageDialog(null, "Fornecedor alterado com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
    
    public void excluir(int idFornecedor) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Telefone telefone;
        Endereco endereco;
        try {
            transaction = session.beginTransaction();
            Fornecedor fornecedor = (Fornecedor)session.get(Fornecedor.class, idFornecedor);
            endereco = fornecedor.getEndereco();
            telefone = fornecedor.getTelefone();
            session.delete(fornecedor);
            //session.delete(endereco);
            session.delete(telefone);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Fornecedor excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
}
