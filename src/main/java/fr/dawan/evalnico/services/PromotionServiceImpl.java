package fr.dawan.evalnico.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.evalnico.dto.CountDto;
import fr.dawan.evalnico.dto.EtudiantDto;
import fr.dawan.evalnico.dto.PromotionDto;
import fr.dawan.evalnico.entities.Etudiant;
import fr.dawan.evalnico.entities.Promotion;
import fr.dawan.evalnico.repositories.EtudiantRepository;
import fr.dawan.evalnico.repositories.PromotionRepository;
import fr.dawan.evalnico.tools.DtoTools;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private EtudiantRepository etudiantRepository;

	

	
	@Override
	public PromotionDto saveOrUpdate(PromotionDto pDto) throws Exception {
		Promotion promo = DtoTools.convert(pDto, Promotion.class);
		
		Optional<Promotion> promoInDb = promotionRepository.findById(pDto.getId());
		if(promoInDb.isPresent()) {
			// ceci fait une copie par référence => ConcurrentAccesException
			List<Etudiant> etudiantsDansPromo = new ArrayList<Etudiant>();
			etudiantsDansPromo.addAll(promoInDb.get().getEtudiants());
			
			if(!etudiantsDansPromo.isEmpty()) {
				for (Etudiant etudiant : etudiantsDansPromo) {
					if(!pDto.getEtudiantsId().contains(etudiant.getId())){
						etudiant.getPromotions().remove(promo);
						promoInDb.get().removeEtudiant(etudiant);
					}
				}
			}
		}
		if(pDto.getEtudiantsId()!=null) {
			for (long id : pDto.getEtudiantsId()) {
				Etudiant etu = etudiantRepository.findById(id).get();
				if(!promo.getEtudiantsId().contains(etu.getId())) {
					promo.getEtudiants().add(etu);
				}
				if(!etu.getPromotionsId().contains(promo.getId())) {
					etu.getPromotions().add(promo);
				}
                promo.getEtudiants().remove(null);
			}
         
		}
		promo = promotionRepository.saveAndFlush(promo);
		return DtoTools.convert(promo, PromotionDto.class);
	}
	

	@Override
	public void delete(long id) {

		promotionRepository.deleteRefToPromId(id);	
		promotionRepository.deleteById(id);

	}
	
	@Override
	public List<PromotionDto> findAll() {
		List<Promotion> resultInDb = promotionRepository.findAll();
		List<PromotionDto> result = new ArrayList<PromotionDto>();
		for (Promotion promo : resultInDb) {
			result.add(DtoTools.convert(promo, PromotionDto.class));
		}
		return result;
	}
	@Override
	public PromotionDto findById(long id) {
		Optional<Promotion> p = promotionRepository.findById(id);
		if(p.isPresent()) {
			return DtoTools.convert(p.get(), PromotionDto.class);
		}
		return null;
	}

	@Override
	public List<EtudiantDto> getEtudiantsFromPromo(long promoId) {

		List<Etudiant> resultInDb = promotionRepository.findAllEtudiantsByPromoId(promoId);
		List<EtudiantDto> result = new ArrayList<EtudiantDto>();

		for (Etudiant etu : resultInDb) {
			result.add(DtoTools.convert(etu, EtudiantDto.class));
		}
		return result;
	}

	@Override
	public List<PromotionDto> findByTitreProId(long titreProId) {
		
		List<Promotion> resultInDb = promotionRepository.findByTitreProId(titreProId);
		List<PromotionDto> result = new ArrayList<PromotionDto>();
		for (Promotion promo : resultInDb) {
			result.add(DtoTools.convert(promo, PromotionDto.class));
		}
		return result;
		
		
	}

	@Override
	public List<PromotionDto> getAll(int page, int max, String search) {
		List<Promotion> resultInDb = promotionRepository.findAllByTitreProContainingOrVilleContaining(search,
				 search, PageRequest.of(page, max)).get().collect(Collectors.toList());
		List<PromotionDto> result = new ArrayList<PromotionDto>();
		for (Promotion promo : resultInDb) {
			result.add(DtoTools.convert(promo, PromotionDto.class));
		}
		return result;
	}

	@Override
	public CountDto count(String search) {
		long result = promotionRepository.countByTitreContainingOrVilleContaining(search, search);
		CountDto d = new CountDto();
		d.setNb(result);
		return d;
	}

	@Override
	public List<PromotionDto> getAllPromosContainingEtudiant(long etudiantId) {
		List<Promotion> resultInDb = promotionRepository.getPromoContainingEtudiant(etudiantId);
		List<PromotionDto> result = new ArrayList<PromotionDto>();
		for (Promotion promo : resultInDb) {
			result.add(DtoTools.convert(promo, PromotionDto.class));
		}
		return result;
	}

	
}
