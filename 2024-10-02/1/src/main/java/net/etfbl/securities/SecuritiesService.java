package net.etfbl.securities;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.etfbl.exceptions.ConflictExcetpion;
import net.etfbl.exceptions.NotFoundException;
import net.etfbl.transactions.TransactionEntity;

public class SecuritiesService {
	private final SecuritiesDAO securDAO;
	
	public SecuritiesService() {
		this.securDAO = new SecuritiesDAO();
	}
	
	public List<SecuritiesEntity> getAll() {
		return securDAO.findAll();
	}
	
	public List<SecuritiesDTO> getAllSortedByLastTransactionDate(String sortingOrder) {
		return securDAO.findAll().stream()
			    .map(s -> new SecuritiesDTO(
			        s.getId(),
			        s.getTransactions().get(s.getTransactions().size() - 1).getDate().toString()
			    ))
			    .sorted((s1, s2) -> {
			        LocalDate d1 = LocalDate.parse(s1.getLastTransactionDate());
			        LocalDate d2 = LocalDate.parse(s2.getLastTransactionDate());
			        return "desc".equalsIgnoreCase(sortingOrder) ? d2.compareTo(d1) : d1.compareTo(d2);
			    })
			    .collect(Collectors.toList());
//
//		List<SecuritiesEntity> securs = securDAO.findAll();
//		List<SecuritiesDTO> result = new LinkedList<SecuritiesDTO>();
//		for(SecuritiesEntity s : securs) {
//			int lastTransOrder = s.getTransactions().size() - 1;
//			String lastTransDate = s.getTransactions().get(lastTransOrder).toString();
//			result.add(new SecuritiesDTO(s.getId(), lastTransDate));
//		}
//		if("desc".equalsIgnoreCase(sortingOrder)) {
//			return result.stream()
//					.sorted((s1, s2) -> {
//						LocalDate date1 = LocalDate.parse(s1.getLastTransactionDate());
//						LocalDate date2 = LocalDate.parse(s2.getLastTransactionDate());
//						return date2.compareTo(date1);
//						}
//					)
//					.collect(Collectors.toList());
//		}
//		return result.stream()
//			.sorted((s1, s2) -> {
//				LocalDate date1 = LocalDate.parse(s1.getLastTransactionDate());
//				LocalDate date2 = LocalDate.parse(s2.getLastTransactionDate());
//				return date1.compareTo(date2);
//				}
//			)
//			.collect(Collectors.toList());
	}
	
	public SecuritiesEntity getById(String id) throws NotFoundException {
		return securDAO.findById(id).orElseThrow(NotFoundException::new);
	}
	
	public void add(SecuritiesEntity request) throws ConflictExcetpion {
		if(!isValid(request))
			throw new ConflictExcetpion();
		securDAO.add(request);
	}
	
	public SecuritiesEntity addTransaction(String securitiesId, TransactionEntity trans) throws NotFoundException, ConflictExcetpion {
		if(!isTransactionValid(trans))
			throw new ConflictExcetpion();
		Optional<SecuritiesEntity> optional = securDAO.findById(securitiesId);
		if(optional.isEmpty())
			throw new NotFoundException();
		SecuritiesEntity entity = optional.get();
		entity.getTransactions().add(trans);
		//add to DB updated entity
		securDAO.update(entity);
		return entity;
	}
	
//	public List<TransactionEntity> problematicTransactions() {
//		List<TransactionEntity> result = new LinkedList<TransactionEntity>();
//		List<ProblematicTransactions> problems = new LinkedList<ProblematicTransactions>();
//		securDAO.findAll()
//			.stream()
//			.forEach(s -> {
//				s.getTransactions().
//			});
//	}
	
	
	private boolean isValid(SecuritiesEntity request) {
		return request.getEmitentName() != null 
				&& request.getId() != null 
				&& request.getType() != null 
				&& request.getTransactions() != null;
	}
	
	private boolean isTransactionValid(TransactionEntity request) {
		return  request.getUnitPrice() != null 
				&& request.getComment() != null;
	}
}
