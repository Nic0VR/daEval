<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<link rel="stylesheet" href="${backEndUrl}/css/main.css" />	
	</head>
	<body>
		<h3 class="red">${promotion.titreProfessionnel.titre}</h3> 
		<h5>Grille de positionnement : ${promotion.ville.nom} 
			du ${promotion.dateDebut.format('dd/MM/yyyy')} au ${promotion.dateFin.format('dd/MM/yyyy')}</h5>

		<table border="1">
			<tr>
				<th>Module</th>
				<th>Dates d'intervention</td>
				<th>Intervenant</td>
				<th>Objectifs pédagogiques</th>
				<th>
				<!-- TODO boucle sur les étudiants ici pour remonter les noms et garder uniquement début/fin -->
					<table border="1">
						<tr>
							<td colspan="2">PLACEHOLDER</td>
						</tr>
						<tr>
							<th>Début</th>
							<th>Fin</th>
						</tr>
					</table>
				</th>
			</tr>
			<#list posiByPromoEntries as entry>
				<tr>
					<td>${entry.key.formation.titre} </td>
					<td>${entry.key.dateDebut} au ${entry.key.dateFin}</td>
					<td>${entry.key.formateur.prenom} ${entry.key.formateur.nom}</td>
					<td>${entry.key.formation.objectifsPedagogiques}</td>
						<#list entry.value as pos>
							<td>
								<table border="1">
									<tr>
										<td colspan="2">${pos.etudiant.prenom} ${pos.etudiant.nom}</td>
									</tr>
									<tr>
										<td>${pos.niveauDebut.valeur}</td>
										<td>${pos.niveauFin.valeur}</td>
									</tr>
								</table>	
							</td>
						</#list>
				</tr>
			</#list>

		</table>						
	</body>
</html>


