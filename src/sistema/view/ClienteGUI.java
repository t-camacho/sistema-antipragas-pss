/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.view;

import sistema.model.DAOCliente;
import sistema.controller.Cadastro;
import sistema.controller.Cliente;
import sistema.controller.Endereco;
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
public class ClienteGUI extends javax.swing.JInternalFrame {
    /**
     * Creates new form CrudCliente
     */
    private Cliente Cliente;
    
    public ClienteGUI() {
        initComponents();
        atualizarTabelaClientes();
        this.desativarBotoes();
        this.btnSave.setEnabled(true);
        this.Cliente = null;
        setScrollData();
        carregarScrollEnd();
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
    
    void carregarScrollEnd(){
        this.jEnd.removeAllItems();
        DAOEndereco end = new DAOEndereco();
        end.listar(this.jEnd);
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
    
    
    private void atualizarTabelaClientes(){
        limparTabela();
        DAOCliente dAOcliente = new DAOCliente();
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        dAOcliente.listar(modelo);
    }
    
    
    public void limparTabela(){
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setNumRows(0);
        tblClientes.setModel(modelo);
    }
    
    private void PesquisarCliente(){
        DAOCliente dAOcliente = new DAOCliente();
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        dAOcliente.listarComLike(BarraDeBusca.getText(), modelo);
    }
    
    private void setCliente(int idCliente){
        int idEnd;
        int dia, mes, ano;
        StringTokenizer aux;
        DAOCliente dAOcliente = new DAOCliente();
        this.Cliente = dAOcliente.buscarComID(idCliente);
        txtNome.setText(this.Cliente.getCadastro().getNome());
        txtCPF.setText(this.Cliente.getCadastro().getCpf());
        txtRg.setText(this.Cliente.getCadastro().getRg());
        txtEmail.setText(this.Cliente.getCadastro().getEmail());
        
        txtDDCel.setText(this.Cliente.getCadastro().getTelefone().getTelCelular().substring(0, 2));
        txtCel.setText(this.Cliente.getCadastro().getTelefone().getTelCelular().substring(2));
        txtDDFixo.setText(this.Cliente.getCadastro().getTelefone().getTelFixo().substring(0, 2));
        txtFixo.setText(this.Cliente.getCadastro().getTelefone().getTelFixo().substring(2));
        
        idEnd = this.Cliente.getCadastro().getEndereco().getIdEndereco();
        for(int i = 0; i < jEnd.getItemCount(); i++){
            aux = new StringTokenizer(jEnd.getItemAt(i), " ");
            int idScroll = Integer.parseInt(aux.nextToken());
            if(idScroll == idEnd){
                jEnd.setSelectedIndex(i);
                break;
            }
        }
        
        aux = new StringTokenizer(this.Cliente.getCadastro().getDataNascimento().toString(), "-");
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
    
    private Cliente criarCliente(){
        Cliente cliente = new Cliente();
        Cadastro cadastro = new Cadastro();
        Endereco endereco = new Endereco();
        Telefone telefone = new Telefone();
        try {
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
            cliente.setCadastro(cadastro);
        } catch (Exception e) {
        }
        
        return cliente;
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
        tblClientes = new javax.swing.JTable();
        BarraDeBusca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtRg = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtDDFixo = new javax.swing.JTextField();
        txtFixo = new javax.swing.JTextField();
        txtCel = new javax.swing.JTextField();
        txtDDCel = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtMes = new javax.swing.JComboBox();
        txtAno = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtDia = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnMerge = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jEnd = new javax.swing.JComboBox<>();

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Informações de Endereço");

        jTextField9.setText("jTextField8");

        setPreferredSize(new java.awt.Dimension(1050, 550));

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Clientes");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClientesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        BarraDeBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BarraDeBuscaKeyReleased(evt);
            }
        });

        jLabel2.setText("Nome do Cliente");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Informações Básicas");

        txtRg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRgActionPerformed(evt);
            }
        });

        jLabel7.setText("*CPF");

        jLabel6.setText("*RG");

        jLabel5.setText("*Nome");

        jLabel8.setText("E-mail");

        txtDDCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDDCelActionPerformed(evt);
            }
        });

        jLabel16.setText("Tel. Celular");

        jLabel15.setText("Tel. Fixo");

        txtMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        txtAno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1997" }));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Data de Nascimento");

        txtDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDDFixo, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                            .addComponent(txtDDCel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtFixo, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                            .addComponent(txtCel)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                        .addComponent(txtRg)
                                        .addComponent(txtCPF)
                                        .addComponent(txtEmail)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtDDFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtDDCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Plus_2190977.png"))); // NOI18N
        btnSave.setText("Adicionar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave);

        btnMerge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Compose_2190985.png"))); // NOI18N
        btnMerge.setText("Alterar");
        btnMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMergeActionPerformed(evt);
            }
        });
        jPanel2.add(btnMerge);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_Close_2190987.png"))); // NOI18N
        btnDelete.setText("Excluir");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btnDelete);

        jLabel10.setText("*Endereço");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Informações de Endereço");

        jEnd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(BarraDeBusca))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(BarraDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMergeActionPerformed
        // TODO add your handling code here:
        if(verificaCampos()){
            DAOCliente dAOcliente = new DAOCliente();
        
            Cliente cliente = new Cliente();
            Cadastro cadastro = new Cadastro();
            Endereco endereco = new Endereco();
            Telefone telefone = new Telefone();
        
            cliente.setIdCliente(this.Cliente.getIdCliente());
        
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
            cliente.setCadastro(cadastro);
        
            dAOcliente.alterar(cliente);
            
            limpaCampos();
            atualizarTabelaClientes();
            this.Cliente = null;
            this.btnDelete.setEnabled(false);
            this.btnMerge.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null, "Campo(s) obrigatório(s) em branco.");
        }
        
    }//GEN-LAST:event_btnMergeActionPerformed

    private void tblClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMousePressed
        // TODO add your handling code here:
        int linhaTabelaCliente = tblClientes.getSelectedRow();
        String id = tblClientes.getModel().getValueAt(linhaTabelaCliente, 0).toString();
        setCliente(Integer.parseInt(id));
        this.ativarBotoes();
    }//GEN-LAST:event_tblClientesMousePressed

    private void BarraDeBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarraDeBuscaKeyReleased
        // TODO add your handling code here:
        limparTabela();
        PesquisarCliente();
    }//GEN-LAST:event_BarraDeBuscaKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.Cliente.excluir();
        this.limparTabela();
        atualizarTabelaClientes();
        this.Cliente = null;
        limpaCampos();
        this.btnDelete.setEnabled(false);
        this.btnMerge.setEnabled(false);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(verificaCampos()){
            Cliente cliente = criarCliente();
            DAOCliente dAOLCliente = new DAOCliente();
            dAOLCliente.armazenar(cliente);
            
            atualizarTabelaClientes();
            this.Cliente = null;
            limpaCampos();
            this.btnDelete.setEnabled(false);
            this.btnMerge.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(null, "Campo(s) obrigatório(s) em branco.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtRgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRgActionPerformed

    private void txtDDCelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDDCelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDDCelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BarraDeBusca;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMerge;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> jEnd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tblClientes;
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
