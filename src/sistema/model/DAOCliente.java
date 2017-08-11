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
import java.util.ArrayList;
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
public class DAOCliente {
    
    public void listar(DefaultTableModel modelo)
    {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Cliente").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Cliente cliente = ((Cliente) it.next());
                modelo.addRow(new Object[]{
                    cliente.getIdCliente(),
                    cliente.getCadastro().getNome(),
                    cliente.getCadastro().getCpf(),
                    cliente.getCadastro().getEmail()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public List listarComLikeAttr(String atributo, String argumento){
         Session session = DAOHibernateUtil.getSessionFactory().openSession();
         List<Cliente> l = new ArrayList<>();
        try {
            session.beginTransaction();
            List listaCadastros = session.createQuery("From Cadastro c where upper(c."+ atributo +") like '"+ argumento + "%'").list();
            List listaClientes = session.createQuery("From Cliente").list();
            
            for(Iterator it = listaCadastros.iterator();it.hasNext();){
                Cadastro cadastro = ((Cadastro) it.next());
                //boolean aux = false;
                for(Iterator it2 = listaClientes.iterator();it2.hasNext();){
                    
                    Cliente cliente = ((Cliente) it2.next());
                    if(cliente.getCadastro().getIdCadastro().equals(cadastro.getIdCadastro())){
                        l.add(cliente);
                    }
                    
                 }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return l;
    }
    
    public void listarComLike(String nome, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaCadastros = session.createQuery("From Cadastro c where upper(c.nome) like '"+ nome + "%'").list();
            List listaClientes = session.createQuery("From Cliente").list();
            
            for(Iterator it = listaCadastros.iterator();it.hasNext();){
                Cadastro cadastro = ((Cadastro) it.next());
                //boolean aux = false;
                for(Iterator it2 = listaClientes.iterator();it2.hasNext();){
                    
                    Cliente cliente = ((Cliente) it2.next());
                    if(cliente.getCadastro().getIdCadastro() == cadastro.getIdCadastro()){
                        //aux = true;
                        modelo.addRow(new Object[]{
                            cliente.getIdCliente(),
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
    
    public Cliente buscarComID(int idCliente){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaClientes = session.createQuery("From Cliente where idCliente = " + idCliente).list();
         
            Cliente c = (Cliente) listaClientes.get(0);
            Cliente cliente = new Cliente();
            Cadastro cadastro = new Cadastro();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
            
            cliente.setIdCliente(c.getIdCliente());
            cliente.setCadastro(c.getCadastro());
            
            cadastro.setIdCadastro(c.getCadastro().getIdCadastro());
            cadastro.setNome(c.getCadastro().getNome());
            cadastro.setRg(c.getCadastro().getRg());
            cadastro.setCpf(c.getCadastro().getCpf());
            cadastro.setDataNascimento(c.getCadastro().getDataNascimento());
            cadastro.setEmail(c.getCadastro().getEmail());
            
            endereco.setIdEndereco(c.getCadastro().getEndereco().getIdEndereco());
            System.out.println("aquii");
            System.out.println(c.getIdCliente());
            System.out.println(c.getCadastro().getEndereco().getIdEndereco());
            endereco.setRuaAven(c.getCadastro().getEndereco().getRuaAven());
            endereco.setNumero(c.getCadastro().getEndereco().getNumero());
            endereco.setCep(c.getCadastro().getEndereco().getCep());
            endereco.setComplemento(c.getCadastro().getEndereco().getComplemento());
            
            telefone.setIdTelefone(c.getCadastro().getTelefone().getIdTelefone());
            telefone.setTelFixo(c.getCadastro().getTelefone().getTelFixo());
            telefone.setTelCelular(c.getCadastro().getTelefone().getTelCelular());
            
            cadastro.setTelefone(telefone);
            cadastro.setEndereco(endereco);
            
            return cliente;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    
    public Cliente buscarComCPF(String cpf){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaCadastros = session.createQuery("From Cadastro").list();
            List listaClientes = session.createQuery("From Cliente").list();
            Cliente c = null;
            for(Iterator it = listaCadastros.iterator();it.hasNext();){
                Cadastro cadastro = ((Cadastro) it.next());
                //boolean aux = false;
                for(Iterator it2 = listaClientes.iterator();it2.hasNext();){
                    
                    Cliente cliente = ((Cliente) it2.next());
                    
                    System.out.println("C : " + cliente.getCadastro().getIdCadastro() + " | Cl : " + cadastro.getIdCadastro());
                    if(cliente.getCadastro().getIdCadastro().equals(cadastro.getIdCadastro())){
                        System.out.println("Cl.cpf : " + cliente.getCadastro().getCpf() + " | CPF : " + cpf);
                        if(cliente.getCadastro().getCpf().equals(cpf))
                             c = cliente;
                    }
                    
                 }
            }
            if(c==null) return c;
             
            Cliente cliente = new Cliente();
            Cadastro cadastro = new Cadastro();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
            
            
            cliente.setIdCliente(c.getIdCliente());
            cliente.setCadastro(c.getCadastro());
            
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
            
            return cliente;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(Cliente cliente) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction(); 
            session.persist(cliente.getCadastro().getTelefone());
            session.save(cliente.getCadastro());
            session.save(cliente);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Cliente armazenado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
        
    }
    
    public void alterar(Cliente cliente) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaClientes = session.createQuery("From Cliente where idCliente = " + cliente.getIdCliente()).list();
            Cliente c = (Cliente) listaClientes.get(0);
            
            c.getCadastro().setNome(cliente.getCadastro().getNome());
            c.getCadastro().setRg(cliente.getCadastro().getRg());
            c.getCadastro().setCpf(cliente.getCadastro().getCpf());
            c.getCadastro().setDataNascimento(cliente.getCadastro().getDataNascimento());
            c.getCadastro().setEmail(cliente.getCadastro().getEmail());
           
            c.getCadastro().getTelefone().setTelFixo(cliente.getCadastro().getTelefone().getTelFixo());
            c.getCadastro().getTelefone().setTelCelular(cliente.getCadastro().getTelefone().getTelCelular());
            
            c.getCadastro().setEndereco(cliente.getCadastro().getEndereco());
            
            session.merge(c);
            session.getTransaction().commit();  
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
    
    public void excluir(int idCliente) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Cadastro cadastro;
        Telefone telefone;
        Endereco endereco;
        try {
            transaction = session.beginTransaction();
            Cliente cliente = (Cliente)session.get(Cliente.class, idCliente);
            cadastro = cliente.getCadastro();
            endereco = cliente.getCadastro().getEndereco();
            telefone = cliente.getCadastro().getTelefone();
            session.delete(cliente);
            session.delete(cadastro);
            //session.delete(endereco);
            session.delete(telefone);
            session.getTransaction().commit();   
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    
}