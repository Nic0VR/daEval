package fr.dawan.evalnico.dto;

import java.util.List;

public class EtudiantDto extends UtilisateurDto  {

	private List<Long> promotionsId;

	
//	public EtudiantDto() {
//		super();
//		this.setRole("ETUDIANT");
//		
//	}


	public List<Long> getPromotionsId() {
		return promotionsId;
	}


	public void setPromotionsId(List<Long> promotionId) {
		this.promotionsId = promotionId;
	}
	
}
