/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.view;

import sistema.model.DAOFuncionario;
import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Endereco;
import sistema.controller.FuncionarioTecnico;
import sistema.controller.Telefone;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.model.DAOEndereco;

/**
 *
 * @author thais
 */
public class FuncionarioGUI extends javax.swing.JInternalFrame {
    /**
     * Creates new form CrudCliente
     */
    private FuncionarioTecnico Funcionario;
    
    public FuncionarioGUI() {
        initComponents();
        atualizarTabelaFuncionarios();
        this.desativarBotoes();
        this.btnSave.setEnabled(true);
        this.Funcionario = null;
        setScrollData();
        carregarScrollEnd();
    }
    
    void carregarScrollEnd(){
        this.jEnd.removeAllItems();
        DAOEndereco end = new DAOEndereco();
        end.listar(this.jEnd);
    }
    
    private void setScrollData(){
        txtDia.removeAllItems();
        txtMes.removeAllItems();
        txtAno.removeAllItems();
        for(int i = 1; i <= 31; i++){
            txtDia.addItem(i);
        }
        for(int i = 1; i <= 12; i++){
            txtMes.addItem(i);
        }
        for(int i = 1500; i <= 2017; i++){
            txtAno.addItem(i);
        }
    }
    
    private void limpaCampos(){
        txtNome.setText("");
        txtRg.setText("");
        txtCPF.setText("");
        txtEmail.setText("");
        txtCel.setText("");
        txtFixo.setText("");
        txtDDCel.setText("");
        txtDDFixo.setText("");
    }
    
    private boolean verificaCampos(){
        boolean nome = false, rg = false, cpf = false, telDD = false, email = false, celDD = false, tel = false, cel = false, end = false;
        if(!txtNome.getText().isEmpty()){
            nome = true;
        }
        if(!txtRg.getText().isEmpty()){
            rg = true;
        }
        if(!txtCPF.getText().isEmpty()){
            cpf = true;
        }
        if(!txtCel.getText().isEmpty()){
            cel = true;
        }
        if(!txtDDCel.getText().isEmpty()){
            celDD = true;
        }
        if(!txtDDFixo.getText().isEmpty()){
            telDD = true;
        }
        if(!txtFixo.getText().isEmpty()){
            tel = true;
        }
        if(!txtEmail.getText().isEmpty()){
            email = true;
        }
        if(jEnd.getSelectedIndex() != -1){
            end = true;
        }
        return nome && rg && cpf && rg && telDD && email && celDD && tel && cel && end;
    }
    
    
    private void atualizarTabelaFuncionarios(){
        limparTabela();
        DAOFuncionario dAOFuncionario = new DAOFuncionario();
        DefaultTableModel modelo = (DefaultTableModel) tblFuncionarios.getModel();
        dAOFuncionario.listar(modelo);
    }
    
    
    public void limparTabela(){
        DefaultTableModel modelo = (DefaultTableModel) tblFuncionarios.getModel();
        modelo.setNumRows(0);
        tblFuncionarios.setModel(modelo);
    }
    
    private void PesquisarFuncionario(){
        DAOFuncionario dAOFuncionario = new DAOFuncionario();
        DefaultTableModel modelo = (DefaultTableModel) tblFuncionarios.getModel();
        dAOFuncionario.listarComLike(BarraDeBusca.getText(), modelo);
    }
    
    private void setFuncionario(int idFuncionario){
        int idEnd;
        int dia, mes, ano;
        StringTokenizer aux;
        DAOFuncionario dAOFuncionario = new DAOFuncionario();
        this.Funcionario = dAOFuncionario.buscarComID(idFuncionario);
        txtNome.setText(this.Funcionario.getCadastro().getNome());
        txtCPF.setText(this.Funcionario.getCadastro().getCpf());
        txtRg.setText(this.Funcionario.getCadastro().getRg());
        txtEmail.setText(this.Funcionario.getCadastro().getEmail());
        
        txtDDCel.setText(this.Funcionario.getCadastro().getTelefone().getTelCelular().substring(0, 2));
        txtCel.setText(this.Funcionario.getCadastro().getTelefone().getTelCelular().substring(2));
        txtDDFixo.setText(this.Funcionario.getCadastro().getTelefone().getTelFixo().substring(0, 2));
        txtFixo.setText(this.Funcionario.getCadastro().getTelefone().getTelFixo().substring(2));
        
        idEnd = this.Funcionario.getCadastro().getEndereco().getIdEndereco();
        for(int i = 0; i < jEnd.getItemCount(); i++){
            aux = new StringTokenizer(jEnd.getItemAt(i), " ");
            int idScroll = Integer.parseInt(aux.nextToken());
            if(idScroll == idEnd){
                jEnd.setSelectedIndex(i);
                break;
            }
        }
        
        aux = new StringTokenizer(this.Funcionario.getCadastro().getDataNascimento().toString(), "-");
        ano = Integer.parseInt(aux.nextToken());
        mes = Integer.parseInt(aux.nextToken());
        dia = Integer.parseInt(aux.nextToken());
        txtDia.setSelectedIndex(dia-1);
        txtMes.setSelectedIndex(mes-1);
        txtAno.setSelectedIndex(ano-1500);
    }
    
    private void ativarBotoes(){
        this.btnDelete.setEnabled(true);
        this.btnMerge.setEnabled(true);
        this.btnSave.setEnabled(true);
    }
    
    private void desativarBotoes(){
        this.btnDelete.setEnabled(false);
        this.btnMerge.setEnabled(false);
        this.btnSave.setEnabled(false);
    }
    
    private FuncionarioTecnico criarFuncionario(){
        FuncionarioTecnico funcionario = new FuncionarioTecnico();
        Cadastro cadastro = new Cadastro();
        Endereco endereco = new Endereco();
        Telefone telefone = new Telefone();
        
        cadastro.setNome(txtNome.getText());
        cadastro.setCpf(txtCPF.getText());
        cadastro.setRg(txtRg.getText());
        cadastro.setEmail(txtEmail.getText());
        String data = txtAno.getSelectedItem().toString() + "/" + txtMes.getSelectedItem() + "/" + txtDia.getSelectedItem();
        Date d = new Date(data);
        cadastro.setDataNascimento(d);
     
        StringTokenizer aux = new StringTokenizer(jEnd.getSelectedItem().toString(), " ");
        endereco.buscarComId(Integer.parseInt(aux.nextToken()));
        
        String num = txtDDCel.getText()+txtCel.getText();
        telefone.setTelCelular(num);
        num = txtDDFixo.getText()+txtFixo.getText();
        telefone.setTelFixo(num);
        
        cadastro.setTelefone(telefone);
        cadastro.setEndereco(endereco);
        funcionario.setCadastro(cadastro);
        
        return funcionario;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFuncionarios = new javax.swing.JTable();
        BarraDeBusca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtRg = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDDFixo = new javax.swing.JTextField();
        txtDDCel = new javax.swing.JTextField();
        txtFixo = new javax.swing.JTextField();
        txtCel = new javax.swing.JTextField();
        txtDia = new javax.swing.JComboBox();
        txtMes = new javax.swing.JComboBox();
        txtAno = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        btnMerge = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jEnd = new javax.swing.JComboBox<>();

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Informações de Endereço");

        jTextField9.setText("jTextField8");

        setPreferredSize(new java.awt.Dimension(1050, 550));

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Funcionários");

        tblFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "CPF", "E-mail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblFuncionariosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblFuncionarios);

        BarraDeBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BarraDeBuscaKeyReleased(evt);
            }
        });

        jLabel2.setText("Nome do Funcionário");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Informações Gerais");

        jLabel5.setText("*Nome");

        jLabel6.setText("*RG");

        jLabel7.setText("*CPF");

        jLabel8.setText("E-mail");

        jLabel9.setText("Data de Nascimento");

        jLabel15.setText("Tel. Fixo");

        jLabel16.setText("Tel. Celular");

        txtDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        txtMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        txtAno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1997" }));

        btnMerge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Compose_2190985.png"))); // NOI18N
        btnMerge.setText("Alterar");
        btnMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMergeActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Close_2190987.png"))); // NOI18N
        btnDelete.setText("Ecluir");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Plus_2190977.png"))); // NOI18N
        btnSave.setText("Adicionar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMerge)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnMerge)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Endereço");

        jEnd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BarraDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel3))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEmail)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtDDFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtDDCel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(txtFixo, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                                            .addComponent(txtCel)))
                                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(jEnd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1034, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(BarraDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(txtDDFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtDDCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMergeActionPerformed
        // TODO add your handling code here:
        if(verificaCampos()){
            DAOFuncionario dAOFuncionario = new DAOFuncionario();
        
            FuncionarioTecnico funcionario = new FuncionarioTecnico();
            Cadastro cadastro = new Cadastro();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
        
            funcionario.setIdFuncionario(this.Funcionario.getIdFuncionario());
        
            cadastro.setNome(txtNome.getText());
            cadastro.setCpf(txtCPF.getText());
            cadastro.setRg(txtRg.getText());
            cadastro.setEmail(txtEmail.getText());
            String data = txtAno.getSelectedItem().toString() + "/" + txtMes.getSelectedItem() + "/" + txtDia.getSelectedItem();
            Date d = new Date(data);
            cadastro.setDataNascimento(d);
        
            StringTokenizer aux = new StringTokenizer(jEnd.getSelectedItem().toString(), " ");
            endereco.buscarComId(Integer.parseInt(aux.nextToken()));
            
            String num = txtDDCel.getText()+txtCel.getText();
            telefone.setTelCelular(num);
            num = txtDDFixo.getText()+txtFixo.getText();
            telefone.setTelFixo(num);
        
            cadastro.setTelefone(telefone);
            cadastro.setEndereco(endereco);
            funcionario.setCadastro(cadastro);
        
            dAOFuncionario.alterar(funcionario);
            
            atualizarTabelaFuncionarios();
            limpaCampos();
            this.Funcionario = null;
            this.btnDelete.setEnabled(false);
            this.btnMerge.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null, "Campo(s) obrigatório(s) em branco.");
        }
        
    }//GEN-LAST:event_btnMergeActionPerformed

    private void tblFuncionariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFuncionariosMousePressed
        // TODO add your handling code here:
        int linhaTabelaCliente = tblFuncionarios.getSelectedRow();
        String id = tblFuncionarios.getModel().getValueAt(linhaTabelaCliente, 0).toString();
        setFuncionario(Integer.parseInt(id));
        this.ativarBotoes();
    }//GEN-LAST:event_tblFuncionariosMousePressed

    private void BarraDeBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarraDeBuscaKeyReleased
        // TODO add your handling code here:
        limparTabela();
        PesquisarFuncionario();
    }//GEN-LAST:event_BarraDeBuscaKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DAOFuncionario dAOFuncionario = new DAOFuncionario();
        dAOFuncionario.excluir(this.Funcionario.getIdFuncionario());
        this.limparTabela();
        atualizarTabelaFuncionarios();
        this.Funcionario = null;
        limpaCampos();
        this.btnDelete.setEnabled(false);
        this.btnMerge.setEnabled(false);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(verificaCampos()){
            FuncionarioTecnico funcionario = criarFuncionario();
            DAOFuncionario dAOLFuncionario = new DAOFuncionario();
            dAOLFuncionario.armazenar(funcionario);
            
            atualizarTabelaFuncionarios();
            this.Funcionario = null;
            limpaCampos();
            this.btnDelete.setEnabled(false);
            this.btnMerge.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null, "Campo(s) obrigatório(s) em branco.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BarraDeBusca;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMerge;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> jEnd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tblFuncionarios;
    private javax.swing.JComboBox txtAno;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDDCel;
    private javax.swing.JTextField txtDDFixo;
    private javax.swing.JComboBox txtDia;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFixo;
    private javax.swing.JComboBox txtMes;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRg;
    // End of variables declaration//GEN-END:variables
}
