/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.model;

import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Endereco;
import sistema.controller.OrdemServico;
import sistema.controller.Telefone;

/**
 *
 * @author thais
 */
public class DAOServico {
    public void armazenar(OrdemServico servico) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
 
        try {
            session.beginTransaction(); 
            session.save(servico);
            session.getTransaction().commit();
 
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
            List listaO = session.createQuery("From OrdemServico p where upper(p." + param + ") like '"+ argumento + "%'").list();
            
            for(Iterator it2 = listaO.iterator();it2.hasNext();){

                OrdemServico or = ((OrdemServico) it2.next());
                modelo.addRow(new Object[]{
                    or.getIdOrdemServico(),
                    or.getStatus(),
                    or.getPrecoTotal(),                    
                    or.getCliente().getCadastro().getNome(),
                    or.getEndereco().getRuaAven(),
                    or.getData(),
                    or.getDescricao()
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void listar(DefaultTableModel modelo){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            List c = session.createQuery("From OrdemServico").list();
            
            
            List pdu = session.createQuery("From ProdutoServico").list();
            for(Iterator it = c.iterator();it.hasNext();){
                OrdemServico or = ((OrdemServico) it.next());
                modelo.addRow(new Object[]{
                    or.getIdOrdemServico(),
                    or.getStatus(),
                    or.getPrecoTotal(),
                    or.getCliente().getCadastro().getNome(),
                    or.getEndereco().getRuaAven() + "/Num " + or.getEndereco().getNumero() + "/CEP " + or.getEndereco().getCep() + "/Comp " + or.getEndereco().getComplemento(),
                    or.getDescricao(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public OrdemServico buscarComID(int idOS){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaid = session.createQuery("From OrdemServico where idOrdemServico = " + idOS).list();
            OrdemServico os = (OrdemServico) listaid.get(0);
            OrdemServico ordem = new OrdemServico();
            Endereco endereco = new Endereco();
            Cliente cliente = new Cliente();
            Cadastro cadastro = new Cadastro();
            Telefone telefone = new Telefone();
            Endereco enderecoc = new Endereco();
            
            ordem.setIdOrdemServico(os.getIdOrdemServico());
            ordem.setStatus(os.getStatus());
            ordem.setDescricao(os.getDescricao());
            ordem.setPrecoTotal(os.getPrecoTotal());
            ordem.setData(os.getData());
            
            
            endereco.setIdEndereco(os.getEndereco().getIdEndereco());
            endereco.setComplemento(os.getEndereco().getComplemento());
            endereco.setCep(os.getEndereco().getComplemento());
            endereco.setNumero(os.getEndereco().getNumero());
            endereco.setRuaAven(os.getEndereco().getRuaAven());
            
            cliente.setIdCliente(os.getCliente().getIdCliente());
            cadastro.setIdCadastro(os.getCliente().getCadastro().getIdCadastro());
            cadastro.setNome(os.getCliente().getCadastro().getNome());
            cadastro.setCpf(os.getCliente().getCadastro().getCpf());
            cadastro.setRg(os.getCliente().getCadastro().getRg());
            cadastro.setDataNascimento(os.getCliente().getCadastro().getDataNascimento());
            cadastro.setEmail(os.getCliente().getCadastro().getEmail());
            
            enderecoc.setIdEndereco(os.getCliente().getCadastro().getEndereco().getIdEndereco());
            enderecoc.setComplemento(os.getCliente().getCadastro().getEndereco().getComplemento());
            enderecoc.setCep(os.getCliente().getCadastro().getEndereco().getComplemento());
            enderecoc.setNumero(os.getCliente().getCadastro().getEndereco().getNumero());
            enderecoc.setRuaAven(os.getCliente().getCadastro().getEndereco().getRuaAven());
            
            telefone.setIdTelefone(os.getCliente().getCadastro().getTelefone().getIdTelefone());
            telefone.setTelFixo(os.getCliente().getCadastro().getTelefone().getTelFixo());
            telefone.setTelCelular(os.getCliente().getCadastro().getTelefone().getTelCelular());
            
            cadastro.setEndereco(enderecoc);
            cadastro.setTelefone(telefone);
            cliente.setCadastro(cadastro);
            os.setEndereco(endereco);
            os.setCliente(cliente);
            return ordem;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
        return null;
    }
    
    public void excluir(int idOS) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Telefone telefone;
        Endereco endereco;
        try {
            transaction = session.beginTransaction();
            OrdemServico os = (OrdemServico)session.get(OrdemServico.class, idOS);
            session.delete(os);
            session.getTransaction().commit();   
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            session.close();
        }
    }
    
    public void alterar(OrdemServico os) {
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List listaFornecedor = session.createQuery("From OrdemServico where idOrdemServico = " + os.getIdOrdemServico()).list();
            OrdemServico c = (OrdemServico) listaFornecedor.get(0);
            
            c.setStatus(os.getStatus());
            c.setPrecoTotal(os.getPrecoTotal());
            c.setDescricao(os.getDescricao());
            c.setData(os.getData());
            
            session.merge(c);
            session.getTransaction().commit();  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
 
        }
    }
}
