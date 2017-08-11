/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

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
 * @author rickh
 */
public class DAOProduto {
    public void listar(DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaProdutos = session.createQuery("From Produto").list();
             for(Iterator it2 = listaProdutos.iterator();it2.hasNext();){

                Produto produto = ((Produto) it2.next());
                modelo.addRow(new Object[]{
                    produto.getIdProduto(),
                    produto.getNome(),
                    produto.getPrincipioAtivo(),
                    produto.getFabricante(),
                    produto.getPreco(),
                    produto.getQuantidadeEstoque(),
                    produto.getFornecedor().getNome(),
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
            List c = session.createQuery("From Produto").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Produto end = ((Produto) it.next());
                modelo.addItem(end.getIdProduto()+ " - " + end.getNome());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public void listarComLike(String param, String argumento, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaProdutos = session.createQuery("From Produto p where upper(p." + param + ") like '"+ argumento + "%'").list();
            
            for(Iterator it2 = listaProdutos.iterator();it2.hasNext();){

                Produto produto = ((Produto) it2.next());
                modelo.addRow(new Object[]{
                    produto.getIdProduto(),
                    produto.getNome(),
                    produto.getPrincipioAtivo(),
                    produto.getFabricante(),
                    produto.getPreco(),
                    produto.getQuantidadeEstoque(),
                    produto.getFornecedor().getNome(),
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Produto buscarComID(int idProduto){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaProdutos = session.createQuery("From Produto where idProduto = " + idProduto).list();
         
            return (Produto) listaProdutos.get(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(Produto produto) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction();
            session.save(produto);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Produto armazenado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
        
    }
    
    public void alterar(Produto p) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();                  
            Produto produto = (Produto)session.createQuery("From Produto where idProduto = " + p.getIdProduto()).list().get(0);
            
            produto.setFabricante(p.getFabricante());
            produto.setFornecedor(p.getFornecedor());
            produto.setNome(p.getNome());
            produto.setPrincipioAtivo(p.getPrincipioAtivo());
            produto.setPreco(p.getPreco());
            produto.setQuantidadeEstoque(p.getQuantidadeEstoque());
            
            session.merge(produto);
            session.getTransaction().commit();  

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
    
    public void excluir(int idProduto) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Produto produto = (Produto)session.get(Produto.class, idProduto);
            session.delete(produto);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Produto excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
}
