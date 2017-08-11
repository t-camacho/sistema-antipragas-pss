/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import sistema.controller.Cadastro;
import sistema.controller.Endereco;
import sistema.controller.FuncionarioTecnico;
import sistema.controller.Telefone;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author root
 */
public class DAOFuncionario {
    public void listar(DefaultTableModel modelo)
    {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From FuncionarioTecnico").list();
            for(Iterator it = c.iterator();it.hasNext();){
                FuncionarioTecnico funcionario = ((FuncionarioTecnico) it.next());
                modelo.addRow(new Object[]{
                    funcionario.getIdFuncionario(),
                    funcionario.getCadastro().getNome(),
                    funcionario.getCadastro().getCpf(),
                    funcionario.getCadastro().getEmail()
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
            List listaCadastros = session.createQuery("From Cadastro c where upper(c.nome) like '"+ nome + "%'").list();
            List listaFuncionarios = session.createQuery("From FuncionarioTecnico").list();
            
            for(Iterator it = listaCadastros.iterator();it.hasNext();){
                Cadastro cadastro = ((Cadastro) it.next());
                for(Iterator it2 = listaFuncionarios.iterator();it2.hasNext();){
                    
                    FuncionarioTecnico funcionario = ((FuncionarioTecnico) it2.next());
                    if(funcionario.getCadastro().getIdCadastro() == cadastro.getIdCadastro()){
                        //aux = true;
                        modelo.addRow(new Object[]{
                            funcionario.getIdFuncionario(),
                            cadastro.getNome(),
                            cadastro.getCpf(),
                            cadastro.getEmail()
                        });
                    }
                    
                 }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public FuncionarioTecnico buscarComID(int idFuncionario){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaFuncionario = session.createQuery("From FuncionarioTecnico where idFuncionario = " + idFuncionario).list();
         
            FuncionarioTecnico c = (FuncionarioTecnico) listaFuncionario.get(0);
            FuncionarioTecnico funcionario = new FuncionarioTecnico();
            Cadastro cadastro = new Cadastro();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
            
            funcionario.setIdFuncionario(c.getIdFuncionario());
            funcionario.setCadastro(c.getCadastro());
            
            cadastro.setIdCadastro(c.getCadastro().getIdCadastro());
            cadastro.setNome(c.getCadastro().getNome());
            cadastro.setRg(c.getCadastro().getRg());
            cadastro.setCpf(c.getCadastro().getCpf());
            cadastro.setDataNascimento(c.getCadastro().getDataNascimento());
            cadastro.setEmail(c.getCadastro().getEmail());
            
            endereco.setIdEndereco(c.getCadastro().getEndereco().getIdEndereco());
            endereco.setRuaAven(c.getCadastro().getEndereco().getRuaAven());
            endereco.setNumero(c.getCadastro().getEndereco().getNumero());
            endereco.setCep(c.getCadastro().getEndereco().getCep());
            endereco.setComplemento(c.getCadastro().getEndereco().getComplemento());
            
            telefone.setIdTelefone(c.getCadastro().getTelefone().getIdTelefone());
            telefone.setTelFixo(c.getCadastro().getTelefone().getTelFixo());
            telefone.setTelCelular(c.getCadastro().getTelefone().getTelCelular());
            
            cadastro.setTelefone(telefone);
            cadastro.setEndereco(endereco);
            
            return funcionario;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(FuncionarioTecnico funcionario) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction(); 
            //session.persist(funcionario.getCadastro().getEndereco());
            session.persist(funcionario.getCadastro().getTelefone());
            session.save(funcionario.getCadastro());
            session.save(funcionario);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Funcionário armazenado com sucesso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        
    }
    
    public void alterar(FuncionarioTecnico funcionario) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaFuncionarios = session.createQuery("From FuncionarioTecnico where idFuncionario = " + funcionario.getIdFuncionario()).list();
            FuncionarioTecnico c = (FuncionarioTecnico) listaFuncionarios.get(0);
            
            c.getCadastro().setNome(funcionario.getCadastro().getNome());
            c.getCadastro().setRg(funcionario.getCadastro().getRg());
            c.getCadastro().setCpf(funcionario.getCadastro().getCpf());
            c.getCadastro().setDataNascimento(funcionario.getCadastro().getDataNascimento());
            c.getCadastro().setEmail(funcionario.getCadastro().getEmail());
           
            c.getCadastro().getTelefone().setTelFixo(funcionario.getCadastro().getTelefone().getTelFixo());
            c.getCadastro().getTelefone().setTelCelular(funcionario.getCadastro().getTelefone().getTelCelular());
            
            
            c.getCadastro().setEndereco(funcionario.getCadastro().getEndereco());
            
            session.merge(c);
            session.getTransaction().commit();  
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
    
    public void excluir(int idFuncionario) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Cadastro cadastro;
        Telefone telefone;
        //Endereco endereco;
        try {
            transaction = session.beginTransaction();
            FuncionarioTecnico funcionario = (FuncionarioTecnico)session.get(FuncionarioTecnico.class, idFuncionario);
            cadastro = funcionario.getCadastro();
            //endereco = funcionario.getCadastro().getEndereco();
            telefone = funcionario.getCadastro().getTelefone();
            session.delete(funcionario);
            session.delete(cadastro);
            //session.delete(endereco);
            session.delete(telefone);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
}
