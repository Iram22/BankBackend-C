/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Iram
 */
@Entity
@Table(name = "ACCOUNTS")
@Inheritance (strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")})
public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "NUMBER")
    private String number;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BALANCE")
    private Double balance;
    
    @Column(name = "INTEREST")
    private Double interest;
    
    @JoinColumn(name = "OWNER", referencedColumnName = "CPR")
    @ManyToOne
    private Person owner;
    
    @OneToMany(mappedBy = "source")
    private Collection<Transfer> outgoing;
    
    @OneToMany(mappedBy = "target")
    private Collection<Transfer> incoming;

    public Account() {
    }

    public Account(String number) {
        this.number = number;
    }

    public Account(String number, Double interest, Person owner) {
        this.number = number;
        this.interest = interest;
        this.owner = owner;
    }
    
    

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Collection<Transfer> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Collection<Transfer> outgoing) {
        this.outgoing = outgoing;
    }

    public Collection<Transfer> getIncoming() {
        return incoming;
    }

    public void setIncoming(Collection<Transfer> incoming) {
        this.incoming = incoming;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (number != null ? number.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.number == null && other.number != null) || (this.number != null && !this.number.equals(other.number))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Account[ number=" + number + " ]";
    }


    
}
