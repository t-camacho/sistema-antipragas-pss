/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Endereco;
import sistema.controller.Telefone;
import sistema.controller.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author thais
 */
public class DAOUsuario {
    
    public void listar(DefaultTableModel modelo)
    {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Usuario").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Usuario usuario = ((Usuario) it.next());
                modelo.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getLogin(),
                    usuario.getSenha(),
                    usuario.getTipo()
                });
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
            List listaUsuario = session.createQuery("From Usuario c where upper(c.login) like '"+ nome + "%'").list();
            
            for(Iterator it2 = listaUsuario.iterator();it2.hasNext();){
                    
                Usuario usuario = ((Usuario) it2.next());
                
                modelo.addRow(new Object[]{
                usuario.getIdUsuario(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getTipo()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public Usuario buscarComID(int idUsuario){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaUsuario = session.createQuery("From Usuario where idUsuario = " + idUsuario).list();
         
            Usuario u = (Usuario) listaUsuario.get(0);
            Usuario usuario = new Usuario();
            
            usuario.setIdUsuario(u.getIdUsuario());
            usuario.setLogin(u.getLogin());
            usuario.setSenha(u.getSenha());
            usuario.setTipo(u.getTipo());
            
            return usuario;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public Usuario Autenticar(String login, String senha){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            java.util.List listaFuncionarios = session.createQuery("from Usuario").list();
            for(Iterator it = listaFuncionarios.iterator();it.hasNext();)
            {
                Usuario usuario = (Usuario) it.next();
                if(login.equals(usuario.getLogin()) && senha.equals(usuario.getSenha())){
                    return usuario;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    public void armazenar(Usuario usuario) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction(); 
            boolean aux = true;
            List u = session.createQuery("From Usuario").list();
            for(Iterator it = u.iterator();it.hasNext();){
                Usuario user = ((Usuario) it.next());
                if(user.getLogin().equals(usuario.getLogin())){
                    JOptionPane.showMessageDialog(null, "Usuario com esse login já existe");
                    aux = false;
                }
            }
            
            if(aux){
                session.save(usuario);
                session.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Usuario armazenado com sucesso");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
    }
    
    public void alterar(Usuario usuario){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaUsuario = session.createQuery("From Usuario where idUsuario = " + usuario.getIdUsuario()).list();
            Usuario u = (Usuario) listaUsuario.get(0);
            u.setLogin(usuario.getLogin());
            u.setSenha(usuario.getSenha());
            u.setTipo(usuario.getTipo());
            session.merge(u);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void excluir(Usuario usuario, int idLogado){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaUsuario = session.createQuery("From Usuario where idUsuario = " + usuario.getIdUsuario()).list();
            Usuario u = (Usuario) listaUsuario.get(0);
            session.delete(u);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Usuario excluído com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
