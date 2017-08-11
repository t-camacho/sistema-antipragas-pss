/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import sistema.model.DAOHibernateUtil;
import sistema.view.LoginGUI;
import org.hibernate.Session;

/**
 *
 * @author thais
 */
public class SistemaAntipraga {
    public static void main(String[] args){
        Session session = DAOHibernateUtil.getSessionFactory().openSession();
        LoginGUI t = new LoginGUI();
        t.setVisible(true);
    }
}
