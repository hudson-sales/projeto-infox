/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

/**
 * Aula 13
 *
 * @author hudso
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    //usando variáveis de conexão
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuarios
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //Métodos da classe usuários
    //Pesquisar
    private void consultar() {
        String sql = "SELECT * FROM tbl_usuarios WHERE iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuFone.setText(rs.getString(3));
                txtUsuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));
                //A limha abaixo se refere ao combobox
                cboUsuPerfil.setSelectedItem(rs.getString(6).toString());

            } else {
                JOptionPane.showMessageDialog(null, "Usuário inexistente");
                txtUsuNome.setText(null);
                txtUsuFone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);
                cboUsuPerfil.setSelectedItem("user");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão: " + e);
        }
    }

    // Método para adiciaonar usuários
    private void adicionar() {
        String sql = "INSERT INTO tbl_usuarios(iduser,usuario,fone,login,senha,perfil)"
                + "VALUES (?,?,?,?,?,?)";
        boolean inseriu = false;
        if (txtUsuId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo ID*");
        } else if (txtUsuNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Nome*");
        } else if (txtUsuLogin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Login*");
        } else if (txtUsuSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Senha*");
        } else if ( cboUsuPerfil.getSelectedItem().toString() == " ") {
            JOptionPane.showMessageDialog(null, " Preencha os campo Perfil*");
        } else {

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                pst.setString(2, txtUsuNome.getText());
                pst.setString(3, txtUsuFone.getText());
                pst.setString(4, txtUsuLogin.getText());
                pst.setString(5, txtUsuSenha.getText());
                pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
                pst.executeUpdate();
                inseriu = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na conexão: " + e);
            }
        }
        if (inseriu) {
            txtUsuId.setText(Integer.toString(Integer.parseInt(txtUsuId.getText()) + 1));
            txtUsuNome.setText(null);
            txtUsuFone.setText(null);
            txtUsuLogin.setText(null);
            txtUsuSenha.setText(null);
            cboUsuPerfil.setSelectedItem("user");
            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso");
        }
    }

    //Método alterar usuário
    private void alterar() {
        String sql = "UPDATE tbl_usuarios set usuario=?,fone=?,login=?,senha=?,perfil=? WHERE iduser=?";
        boolean inseriu = false;
        if (txtUsuId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo ID*");
        } else if (txtUsuNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Nome*");
        } else if (txtUsuLogin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Login*");
        } else if (txtUsuSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, " Preencha os campo Senha*");
        } else if ((String) cboUsuPerfil.getSelectedItem().toString() == " ") {
            JOptionPane.showMessageDialog(null, " Preencha os campo Perfil*");
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuNome.getText());
                pst.setString(2, txtUsuFone.getText());
                pst.setString(3, txtUsuLogin.getText());
                pst.setString(4, txtUsuSenha.getText());
                pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
                pst.setString(6, txtUsuId.getText());

                pst.executeUpdate();
                inseriu = true;
                JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao alterar: " + e);
            }
        }
        if (inseriu) {
            txtUsuId.setText(Integer.toString(Integer.parseInt(txtUsuId.getText()) + 1));
            txtUsuNome.setText(null);
            txtUsuFone.setText(null);
            txtUsuLogin.setText(null);
            txtUsuSenha.setText(null);
            cboUsuPerfil.setSelectedItem("user");
        }
    }

    //Método resonsável por remover usuário
    private void deletar() {
        String sql = "DELETE FROM tbl_usuarios WHERE iduser=?";
        boolean deletado = false;
        //Código abaixo mostra uma caixa de texto com a 
        //opção de confirmar a exclusão do usuário
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usário",
                "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                pst.executeUpdate();
                deletado = true;
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na execução: " + e);
            }
        }
        if (deletado) {
            txtUsuId.setText(Integer.toString(Integer.parseInt(txtUsuId.getText()) + 1));
            txtUsuNome.setText(null);
            txtUsuFone.setText(null);
            txtUsuLogin.setText(null);
            txtUsuSenha.setText(null);
            cboUsuPerfil.setSelectedItem("user");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuLogin = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        txtUsuFone = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        txtUsuId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jFormattedTextField1.setText("jFormattedTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("ID *");

        jLabel2.setText("Nome *");

        jLabel3.setText("Login *");

        jLabel4.setText("Senha *");

        jLabel5.setText("Perfil *");

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        jLabel6.setText("Telefone");

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N
        btnUsuRead.setToolTipText("Consultar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/alterar1.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Editar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/excluir1.png"))); // NOI18N
        jButton3.setToolTipText("Excluir");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adicionar1.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Adicionar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("* Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel5)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jLabel4)))
                                            .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUsuDelete, btnUsuRead, btnUsuUpdate, jButton3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUsuDelete, btnUsuRead, btnUsuUpdate, jButton3});

        setBounds(0, 0, 729, 594);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        // Chamando o método consultar
        consultar();

    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JButton jButton3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
