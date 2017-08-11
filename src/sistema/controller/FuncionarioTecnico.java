package sistema.controller;
// Generated 05/06/2017 10:05:02 by Hibernate Tools 4.3.1

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




/**
 * FuncionarioTecnico generated by hbm2java
 */
@Entity
@Table(name="FuncionarioTecnico")
public class FuncionarioTecnico  implements java.io.Serializable {

     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private int idFuncionario;
     private Cadastro cadastro;

    public FuncionarioTecnico() {
    }

    public FuncionarioTecnico(int idFuncionario, Cadastro cadastro) {
       this.idFuncionario = idFuncionario;
       this.cadastro = cadastro;
    }
   
    public int getIdFuncionario() {
        return this.idFuncionario;
    }
    
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    public Cadastro getCadastro() {
        return this.cadastro;
    }
    
    public void setCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }
}


