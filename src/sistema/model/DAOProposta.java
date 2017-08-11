/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Proposta;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rickh
 */
public class DAOProposta {
    public void listar(DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From Proposta").list();
            for(Iterator it = c.iterator();it.hasNext();){
                Proposta proposta = ((Proposta) it.next());
                proposta.atualizarAttrs();
                modelo.addRow(new Object[]{
                    proposta.getIdProposta(),
                    proposta.getCliente().getCadastro().getNome(),
                    proposta.getTipoServicoExtenso(),                    
                    proposta.getDescricao(),
                    proposta.getOrcamento(),
                    proposta.getStatusExtenso()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    
    
    public List listarCliente(String param, String arg){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        List<Proposta> propostas = new ArrayList<>();
        try {
            session.beginTransaction();
            List listaCadastros = session.createQuery("From Cadastro c where upper(c." + param +") like '"+ arg + "%'").list();
            List listaPropostas = session.createQuery("From Proposta").list();
            
            for(Iterator it = listaCadastros.iterator();it.hasNext();){
                Cadastro cadastro = ((Cadastro) it.next());
                //boolean aux = false;
                for(Iterator it2 = listaPropostas.iterator();it2.hasNext();){
                    
                    Proposta p = ((Proposta) it2.next());
                    if(p.getCliente().getCadastro().getIdCadastro().equals(cadastro.getIdCadastro())){
                        propostas.add(p);
                    }
                    
                 }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return propostas;
    }
    
    public void listarComJoinClienteCPF(String param, String argumento, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaProposta = session.createQuery("From Proposta p where upper(p." + param + ") like '"+ argumento + "%'").list();
            
            for(Iterator it2 = listaProposta.iterator();it2.hasNext();){

                Proposta proposta = ((Proposta) it2.next());
                modelo.addRow(new Object[]{
                    proposta.getIdProposta(),
                    proposta.getCliente().getCadastro().getCpf(),
                    proposta.getDescricao(),
                    proposta.getOrcamento(),
                    proposta.getStatus()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void listarComJoinCliente(String param, String nome, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            DAOCliente daoc = new DAOCliente();
            List listaClientes = daoc.listarComLikeAttr(param, nome);
            List listaProposta = session.createQuery("From Proposta").list();
            
             for(Iterator it = listaClientes.iterator();it.hasNext();){
                Cliente cliente = ((Cliente) it.next());
                //boolean aux = false;
                for(Iterator it2 = listaProposta.iterator();it2.hasNext();){
                    
                    Proposta proposta = ((Proposta) it2.next());
                    if(proposta.getCliente().getIdCliente() == cliente.getIdCliente()){
                        modelo.addRow(new Object[]{
                            proposta.getIdProposta(),
                            proposta.getCliente().getCadastro().getNome(),
                            proposta.getTipoServico(),                    
                            proposta.getDescricao(),
                            proposta.getOrcamento(),
                            proposta.getStatus()
                        });
                    
                 }
                }
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void listarComLike(String param, String argumento, DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaProposta = session.createQuery("From Proposta p where upper(p." + param + ") like '"+ argumento + "%'").list();
            
            for(Iterator it2 = listaProposta.iterator();it2.hasNext();){

                Proposta proposta = ((Proposta) it2.next());
                modelo.addRow(new Object[]{
                    proposta.getIdProposta(),
                    proposta.getCliente().getCadastro().getNome(),
                    proposta.getTipoServico(),                    
                    proposta.getDescricao(),
                    proposta.getOrcamento(),
                    proposta.getStatus()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void alterar(Proposta proposta) {
        System.out.println("a " + proposta.getIdProposta());
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();                  
            Proposta c = (Proposta)session.createQuery("From Proposta where idProposta = " + proposta.getIdProposta()).list().get(0);
            System.out.println(c.getCliente().getCadastro().getNome());
            c.setCliente(proposta.getCliente());
            c.setDescricao(proposta.getDescricao());
            c.setOrcamento(proposta.getOrcamento());
            c.setStatus(proposta.getStatus());
            c.setTipoServico(proposta.getTipoServicoEnum());
            
            session.merge(c);
            session.getTransaction().commit();  
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void excluir(int idProposta) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Proposta proposta = (Proposta)session.get(Proposta.class, idProposta);
            session.delete(proposta);
            session.getTransaction().commit();   
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public Proposta buscarComID(int idProposta){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            System.out.println("SEARCHING FOR" + idProposta);
            List listaPropostas = session.createQuery("From Proposta where idProposta = " + idProposta).list();
         
            Proposta c = (Proposta) listaPropostas.get(0);
            
            Proposta prop = new Proposta();
            Cliente cliente = new Cliente();
            Cadastro cadastro = new Cadastro();
            
            prop.setIdProposta(c.getIdProposta());
            prop.setDescricao(c.getDescricao());
            prop.setOrcamento(c.getOrcamento());
            prop.setTipoServico(c.getTipoServico());
            prop.setStatus(c.getStatus());
                      
            cadastro.setIdCadastro(c.getCliente().getCadastro().getIdCadastro());
            cadastro.setNome(c.getCliente().getCadastro().getNome());
            cadastro.setRg(c.getCliente().getCadastro().getRg());
            cadastro.setCpf(c.getCliente().getCadastro().getCpf());
            cadastro.setDataNascimento(c.getCliente().getCadastro().getDataNascimento());
            cadastro.setEmail(c.getCliente().getCadastro().getEmail());
            
            cliente.setCadastro(cadastro);
            cliente.setIdCliente(c.getCliente().getIdCliente());            
            
            prop.setCliente(cliente);
            return prop;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void armazenar(Proposta proposta) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction();
            session.save(proposta);
            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Proposta armazenada com sucesso");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        } finally {
            session.close();
        }
        
    }
}

