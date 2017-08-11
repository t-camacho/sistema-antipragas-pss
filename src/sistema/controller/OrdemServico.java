package sistema.controller;
// Generated 05/06/2017 10:05:02 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import sistema.model.DAOServico;

/**
 * OrdemServico generated by hbm2java
 */
public class OrdemServico  implements java.io.Serializable {


     private int idOrdemServico;
     private Cliente cliente;
     private Endereco endereco;
     private String status;
     private Float precoTotal;
     private String descricao;
     private String data;
     private Set pragasEncontradases = new HashSet(0);
     private Set produtoServicos = new HashSet(0);

    public OrdemServico() {
    }

	
    public OrdemServico(int idOrdemServico, Cliente cliente, Endereco endereco) {
        this.idOrdemServico = idOrdemServico;
        this.cliente = cliente;
        this.endereco = endereco;
    }
    public OrdemServico(int idOrdemServico, Cliente cliente, Endereco endereco, String status, Float precoTotal, String descricao, Set pragasEncontradases, Set produtoServicos) {
       this.idOrdemServico = idOrdemServico;
       this.cliente = cliente;
       this.endereco = endereco;
       this.status = status;
       this.precoTotal = precoTotal;
       this.descricao = descricao;
       this.pragasEncontradases = pragasEncontradases;
       this.produtoServicos = produtoServicos;
    }
   
    public int getIdOrdemServico() {
        return this.idOrdemServico;
    }
    
    public void setIdOrdemServico(int idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Endereco getEndereco() {
        return this.endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Float getPrecoTotal() {
        return this.precoTotal;
    }
    
    public void setPrecoTotal(Float precoTotal) {
        this.precoTotal = precoTotal;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Set getPragasEncontradases() {
        return this.pragasEncontradases;
    }
    
    public void setPragasEncontradases(Set pragasEncontradases) {
        this.pragasEncontradases = pragasEncontradases;
    }
    public Set getProdutoServicos() {
        return this.produtoServicos;
    }
    
    public void setProdutoServicos(Set produtoServicos) {
        this.produtoServicos = produtoServicos;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    

    public void armazenar(){
        DAOServico dao = new DAOServico();
        dao.armazenar(this);
    }
    
    public void alterar(){
        DAOServico dao = new DAOServico();
        dao.alterar(this);
    }
    
    public void excluir(){
        DAOServico dao = new DAOServico();
        dao.excluir(this.getIdOrdemServico());
    }
    
    public void buscar(){
        DAOServico dao = new DAOServico();
        dao.buscarComID(this.getIdOrdemServico());
    }


}

