package contacts.server;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

import contacts.models.Contact;
import exceptions.DuplicateException;
import exceptions.InvalidFormatException;
import exceptions.NotFoundException;
import rmi.client.ContactClient;

public class ContactService {
	private final ContactDAO contactDAO;
	
	private ContactClient clientRMI;
	
	public ContactService() {
		this.contactDAO = new ContactDAO();
		this.clientRMI = new ContactClient();
		
	}
	
	public List<Contact> getAll() {
		return contactDAO.readAll();
	}
	
	public Contact getById(List<Contact> contacts, String id) throws NotFoundException {
		return contacts
				.stream()
				.filter(c -> id.equals(c.getId()))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}
	
	public List<Contact> add(Contact newContact) throws DuplicateException, InvalidFormatException {
		try {
			if(!clientRMI.contactRMI.isEmailValid(newContact.getEmails().get(0)))
				throw new InvalidFormatException("E-mail addres does not contain @ sign");
		} catch(RemoteException e) {
			e.printStackTrace();
		}
			
		for(String s : newContact.getEmails())
			if(!isEmailUnique(s))
				throw new DuplicateException("E-mail already exists!");
		for(String s : newContact.getPhones())
			if(!isPhoneUnique(s))
				throw new DuplicateException("Phone number already exists!");
		List<Contact> contacts = contactDAO.readAll();
		contacts.add(newContact);
		contactDAO.writeAll(contacts);
		return contacts;
	}
	
	public List<Contact> update(Contact updateContact) throws NotFoundException {
		List<Contact> contacts = contactDAO.readAll();
		Contact oldContact = getById(contacts, updateContact.getId());
		int index = contacts.indexOf(oldContact);
		updateContact.setTime(oldContact.getTime());
		updateContact.setEmails(oldContact.getEmails());
		updateContact.setPhones(oldContact.getPhones());
		contacts.set(index, updateContact);
		contactDAO.writeAll(contacts);
		return contacts;
	}
	
	public void deleteById(String id) throws NotFoundException {
		List<Contact> contacts = contactDAO.readAll();
		Contact oldContact = getById(contacts, id);
		contacts.remove(oldContact);
		contactDAO.writeAll(contacts);
	}
	
	public Contact addEmail(String contactId, String email) throws NotFoundException, DuplicateException, InvalidFormatException {
		try {
			if(!clientRMI.contactRMI.isEmailValid(email))
				throw new InvalidFormatException("E-mail addres does not contain @ sign");
		} catch(RemoteException e) {
			e.printStackTrace();
		}
		if(!isEmailUnique(email))
			throw new DuplicateException("E-mail address must be unique!");
		List<Contact> contacts = contactDAO.readAll();
		Contact contact = getById(contacts, contactId);
		contact.getEmails().add(email);
		contactDAO.writeAll(contacts);
		return contact;	
	}
	
	public Contact addPhone(String contactId, String phone) throws NotFoundException, DuplicateException {
		if(!isPhoneUnique(phone))
			throw new DuplicateException("Phone number must be unique!");
		List<Contact> contacts = contactDAO.readAll();
		Contact contact = getById(contacts, contactId);
		contact.getPhones().add(phone);
		contactDAO.writeAll(contacts);
		return contact;	
	}
	
	private boolean isPhoneUnique(String phone) {
		List<Contact> contacts = contactDAO.readAll();
		Optional<Contact> phoneOptional = contacts.stream()
				.filter(c -> c.getPhones().contains(phone))
				.findFirst();
		return phoneOptional.isEmpty();
	}
	
	private boolean isEmailUnique(String email) {
		List<Contact> contacts = contactDAO.readAll();
		Optional<Contact> emailOptional = contacts.stream()
			.filter(c -> c.getEmails().contains(email))
			.findFirst();
		return emailOptional.isEmpty();
	}
}
