/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kata7.application.swing;

import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import kata7.control.Command;
import kata7.model.Attribute;
import kata7.view.AttributeDialog;
import kata7.view.PopulationDialog;

/**
 *
 * @author usuario
 */
public class Toolbar extends JPanel implements PopulationDialog, AttributeDialog{

    private final Map <String,Command> comands;
    private final List <Attribute> attribute = new ArrayList <>();
    private JComboBox combo;

    public Toolbar(Map<String, Command> comands) {
        super(new FlowLayout());
        this.comands = comands;
        this.add(mailDomainsAttribute());
        this.add(firstCharAtribute());
        this.add(combobox());
        this.add(calculateButton());
    }

    @Override
    public List population() {
        try {
            return MailReader.read("emails.txt");
        } catch (FileNotFoundException ex) {
            return new ArrayList();
        } catch (IOException ex) {
            return new ArrayList();
        }
    }

    @Override
    public Attribute attribute() {
        return attribute.get(combo.getSelectedIndex());
        
    }


    private JComboBox combobox() {
        combo = new JComboBox(options("Mail Domains","First Char"));
        return combo;
    }
    
    private String[] options(String ... options) {
        return options;
    }
    
    private void add (Attribute attribute){
        this.attribute.add(attribute);
    }
    
    private Attribute mailDomainsAttribute() {
        return new Attribute<Person,String>() {

            @Override
            public String get(Person item) {
                return item.getMail().split("@")[1];
            }
        };
    }

    private Attribute firstCharAtribute() {
        return new Attribute<Person,Character>() {

            @Override
            public Character get(Person item) {
                return item.getMail().charAt(0);
            }
        };
    }
    
    private JButton calculateButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comands.get("calculate").execute();
            }
        });
        return button;
    }
    
}
