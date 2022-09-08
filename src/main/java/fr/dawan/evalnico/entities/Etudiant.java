package fr.dawan.evalnico.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import fr.dawan.evalnico.enums.Role;

@SuppressWarnings("serial")
@Entity
public class Etudiant extends Utilisateur implements Serializable {

	@ManyToMany
	private List<Promotion> promotions;

	public Etudiant() {
		super();
		this.setRole(Role.ETUDIANT);
		// promotions= new ArrayList<Promotion>();
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public List<Long> getPromotionsId() {
		List<Long> promotionsId = new ArrayList<Long>();
		for (Promotion promo : promotions) {
			if (promo != null)
				promotionsId.add(promo.getId());
		}
		return promotionsId;
	}

}
