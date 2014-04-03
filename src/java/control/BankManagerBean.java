/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dk.cphbusiness.bank.contract.BankManager;
import dk.cphbusiness.bank.contract.dto.AccountDetail;
import dk.cphbusiness.bank.contract.dto.AccountIdentifier;
import dk.cphbusiness.bank.contract.dto.AccountSummary;
import dk.cphbusiness.bank.contract.dto.CustomerDetail;
import dk.cphbusiness.bank.contract.dto.CustomerIdentifier;
import dk.cphbusiness.bank.contract.dto.CustomerSummary;
import dk.cphbusiness.bank.contract.eto.CustomerBannedException;
import dk.cphbusiness.bank.contract.eto.InsufficientFundsException;
import dk.cphbusiness.bank.contract.eto.NoSuchAccountException;
import dk.cphbusiness.bank.contract.eto.NoSuchCustomerException;
import dk.cphbusiness.bank.contract.eto.TransferNotAcceptedException;
import entity.Person;
import java.math.BigDecimal;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static control.Assembler.*;
import entity.Account;
import entity.CheckingAccount;
import entity.Postal;
import entity.Transfer;
import java.util.ArrayList;

/**
 *
 * @author Iram
 */
@Stateless
public class BankManagerBean implements BankManager {

    @PersistenceContext(unitName = "BankBackendPU")
    private EntityManager em;

    @Override
    public Collection<CustomerSummary> listCustomers() {
        Query query = em.createNamedQuery("Person.findAll");
        Collection<Person> customers = query.getResultList();
        return createCustomerSummaries(customers);
    }

    @Override
    public Collection<AccountSummary> listCustomerAccounts(CustomerIdentifier customer) {
        Person person = em.find(Person.class, customer.getCpr());
        Collection<Account> accounts = person.getAccounts();
        return createAccountSummaries(accounts);
    }

    @Override
    public CustomerDetail showCustomer(CustomerIdentifier customer) throws NoSuchCustomerException {
        Person person = em.find(Person.class, customer.getCpr());
        if (person == null) {
            throw new NoSuchCustomerException(customer);
        }
        return createCustomerDetail(person);
    }

    @Override
    public AccountDetail showAccountHistory(AccountIdentifier accountIdentifier) {
        Account account = em.find(Account.class, accountIdentifier.getNumber());
        em.refresh(account);
        return createAccountDetail(account);
    }

    @Override
    public Collection<AccountSummary> listAccounts() {
        Query query = em.createNamedQuery("Account.findAll");
        Collection<Account> accounts = query.getResultList();
        return createAccountSummaries(accounts);
    }

    @Override
    public Collection<String> listAccountTypes() {
        Collection<String> accountTypes = new ArrayList<>();
        accountTypes.add("Checking Account");
        accountTypes.add("Time Deposit Account");
        accountTypes.add("Money Market Account");
        return accountTypes;
    }

    @Override
    public CustomerDetail saveCustomer(CustomerDetail customer) {
        Postal code = em.find(Postal.class, customer.getPostalCode());
        if (code == null) {
            code = new Postal(customer.getPostalCode(), customer.getPostalDistrict());
            em.persist(code);
        }
        Person person = new Person(customer.getCpr(),
                customer.getTitle(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getPhone(),
                customer.getEmail(),
                code);
        if (em.find(Person.class, customer.getCpr()) == null) {

            em.persist(person);
        } else {
            person = em.merge(person);
        }
        return createCustomerDetail(person);

    }

    @Override
    public AccountDetail transferAmount(BigDecimal amount, AccountIdentifier source, AccountIdentifier target) throws NoSuchAccountException, TransferNotAcceptedException, InsufficientFundsException {
        Account sourceAccount = em.find(Account.class, source.getNumber());
        Account targetAccount = em.find(Account.class, target.getNumber());
        Transfer transfer = new Transfer(Double.parseDouble("" + amount), sourceAccount, targetAccount);
        em.persist(transfer);
        em.flush();
        return createAccountDetail(sourceAccount);
    }

    @Override
    public AccountDetail createAccount(CustomerIdentifier customer, AccountDetail account) throws NoSuchCustomerException, CustomerBannedException {
        
        Account customerAccount;
        Person person = em.find(Person.class, customer.getCpr());
        if(person == null) throw new NoSuchCustomerException(customer);
        customerAccount  = new CheckingAccount(account.getNumber(),
                 Double.parseDouble("" + account.getInterest()), person);
        return createAccountDetail(customerAccount);
    }
    
    @Override
    public String sayHello(String name) {
        return "Hello " + name + " from bank manager bean. Gruppe C";
    }

    @Override
    public boolean checkIfCustomerExists(CustomerIdentifier customer) {
      if(em.find(Person.class, customer.getCpr())==null)
      {
          return false;
      }
      return true;
    }

}
