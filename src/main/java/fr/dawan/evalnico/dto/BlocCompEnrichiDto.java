package fr.dawan.evalnico.dto;

import java.util.List;

public class BlocCompEnrichiDto {

	private BlocCompetencesDto bloc;
	private List<CompetenceDto> comps;
	public BlocCompetencesDto getBloc() {
		return bloc;
	}
	public void setBloc(BlocCompetencesDto bloc) {
		this.bloc = bloc;
	}
	public List<CompetenceDto> getComps() {
		return comps;
	}
	public void setComps(List<CompetenceDto> comps) {
		this.comps = comps;
	}
	
	
}
