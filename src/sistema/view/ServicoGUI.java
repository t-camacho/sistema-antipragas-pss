/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.view;

import sistema.model.DAOProposta;
import sistema.controller.Proposta;
import javax.swing.table.DefaultTableModel;
import sistema.model.DAOServico;

/**
 *
 * @author rickh
 */
public class ServicoGUI extends javax.swing.JInternalFrame {
    private Proposta proposta;
    /**
     * Creates new form CrudCliente
     */
    public ServicoGUI() {
        initComponents();
        atualizarTabelaServico();
        this.proposta = null;
    }
    
    
    private void atualizarTabelaServico(){
        limparTabela();
        DAOServico daoServico = new DAOServico();
        DefaultTableModel modelo = (DefaultTableModel) tblServico.getModel();
        daoServico.listar(modelo);
    }
    
    public void limparTabela(){
        DefaultTableModel modelo = (DefaultTableModel) tblServico.getModel();
        modelo.setNumRows(0);
        tblServico.setModel(modelo);
    }
    
    private void PesquisarProposta(String param){
        DAOProposta daoProposta = new DAOProposta();
        DefaultTableModel modelo = (DefaultTableModel) tblServico.getModel();
        //daoProposta.listarComLike(param, BarraDeBusca.getText(), modelo);
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
        tblServico = new javax.swing.JTable();
        BarraDeBusca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Informações de Endereço");

        jTextField9.setText("jTextField8");

        setPreferredSize(new java.awt.Dimension(1050, 550));

        jLabel1.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ordens de Serviço");

        tblServico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status", "Preço (R$)", "Cliente", "Endereço", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblServico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblServicoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblServico);

        BarraDeBusca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BarraDeBuscaKeyReleased(evt);
            }
        });

        jLabel2.setText("Nome Cliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1034, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BarraDeBusca)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BarraDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblServicoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServicoMousePressed
       
    }//GEN-LAST:event_tblServicoMousePressed

    private void BarraDeBuscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarraDeBuscaKeyReleased
        limparTabela();
        PesquisarProposta("nome");      
    }//GEN-LAST:event_BarraDeBuscaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BarraDeBusca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tblServico;
    // End of variables declaration//GEN-END:variables
}
