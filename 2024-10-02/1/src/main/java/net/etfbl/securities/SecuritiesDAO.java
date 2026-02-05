package net.etfbl.securities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import net.etfbl.enums.SecuritiesType;
import net.etfbl.transactions.TransactionEntity;

public class SecuritiesDAO {
	private static List<SecuritiesEntity> securities = new LinkedList<SecuritiesEntity>();
	
	static {
		List<TransactionEntity> transactions1 = new ArrayList<>();
        transactions1.add(new TransactionEntity(100, new BigDecimal("12.50"), "Kupovina akcija"));
        transactions1.add(new TransactionEntity(50, new BigDecimal("13.20"), "Dodatna kupovina"));

        SecuritiesEntity s1 = new SecuritiesEntity(
            "SEC-001",
            "Telekom Srbija",
            SecuritiesType.STOCK,
            transactions1
        );

        // --- Drugi SecuritiesEntity ---
        List<TransactionEntity> transactions2 = new ArrayList<>();
        transactions2.add(new TransactionEntity(200, new BigDecimal("8.75"), "Kupovina obveznica"));
        transactions2.add(new TransactionEntity(100, new BigDecimal("9.10"), "Dodatna kupovina"));
        transactions2.get(0).setDate("2026-01-05");
        transactions2.get(1).setDate("2026-01-06");

        SecuritiesEntity s2 = new SecuritiesEntity(
            "SEC-002",
            "NIS",
            SecuritiesType.BOND,
            transactions2
        );

        // --- Dodavanje u listu ---
        securities.add(s1);
        securities.add(s2);
	}
	
	public List<SecuritiesEntity> findAll() {
		return securities;
	}
	
	public Optional<SecuritiesEntity> findById(String id) {
		return securities.stream()
				.filter(s -> id.equals(s.getId()))
				.findFirst();
	}
	
	public void add(SecuritiesEntity secur) {
		securities.add(secur);
	}
	
	//zgodan nacin za azuriranje entieta u listi (ovaj dio sa return)
	public void update(SecuritiesEntity updatedSecur) {
	    for (int i = 0; i < securities.size(); i++) {
	        if (securities.get(i).getId().equals(updatedSecur.getId())) {
	            securities.set(i, updatedSecur); 
	            return; 
	        }
	    }
	}

}
