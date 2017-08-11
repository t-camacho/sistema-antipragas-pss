package sistema.controller;
// Generated 05/06/2017 10:05:02 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Telefone generated by hbm2java
 */
@Embeddable
@Table(name="Telefone")
public class Telefone  implements java.io.Serializable {

     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private int idTelefone;
     private String telFixo;
     private String telCelular;
     private Set fornecedors = new HashSet(0);
     private Set cadastros = new HashSet(0);

    public Telefone() {
    }

	
    public Telefone(int idTelefone) {
        this.idTelefone = idTelefone;
    }
    public Telefone(int idTelefone, String telFixo, String telCelular, Set fornecedors, Set cadastros) {
       this.idTelefone = idTelefone;
       this.telFixo = telFixo;
       this.telCelular = telCelular;
       this.fornecedors = fornecedors;
       this.cadastros = cadastros;
    }
   
    public int getIdTelefone() {
        return this.idTelefone;
    }
    
    public void setIdTelefone(int idTelefone) {
        this.idTelefone = idTelefone;
    }
    public String getTelFixo() {
        return this.telFixo;
    }
    
    public void setTelFixo(String telFixo) {
        this.telFixo = telFixo;
    }
    public String getTelCelular() {
        return this.telCelular;
    }
    
    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }
    public Set getFornecedors() {
        return this.fornecedors;
    }
    
    public void setFornecedors(Set fornecedors) {
        this.fornecedors = fornecedors;
    }
    public Set getCadastros() {
        return this.cadastros;
    }
    
    public void setCadastros(Set cadastros) {
        this.cadastros = cadastros;
    }




}

