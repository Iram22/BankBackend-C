/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Iram
 */
@Entity
@Table(name = "CHECKING_ACCOUNTS")
@NamedQueries({
    @NamedQuery(name = "CheckingAccount.findAll", query = "SELECT c FROM CheckingAccount c")})
public class CheckingAccount extends Account {
    private static final long serialVersionUID = 1L;

    public CheckingAccount() {
    }

    public CheckingAccount(String accountNumber) {
        super(accountNumber);
    }
    
    public CheckingAccount(String accountNumber, Double interest, Person person)
    {
        super(accountNumber, interest, person);
    }

    @Override
    public String toString() {
        return "model.CheckingAccount[ accountnumber=" + getNumber() + " ]";
    }
    
}
