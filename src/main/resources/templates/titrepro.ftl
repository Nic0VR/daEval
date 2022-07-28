
<!DOCTYPE html>

<html>
	<head>
	<style>
		.red{
		color:red;
		}
		
		.nico-table{
		border:solid black 1px
		}
		.nico-table th,td {
			border:solid black 1px
		}
	</style>
	<meta charset="UTF-8" />
	</head>

	<body>
	<section>
			<h1 >Bulletin d'évaluation</h1>
			<br>
			<h2> Titre Professionnel<h2>	
			<br>
			<h2 class="red">${titrePro.titre}</h1>
			<br>
			<div>
				<span >Nom : ${etudiant.nom}</span>
				<br>
				<span >Prénom : ${etudiant.prenom}</span>
				<br>
				<span >Année d'étude :${promo.getAnnee()}</span>
				<br>
				<span >Filière : ${titrePro.titre}</span>
				<br>
			</div>
	</section>
	
	<section>
		<h2> Controle Continu </h2>
		<table class="nico-table">
			<thead>
				<tr>
					<th colspan="1"> Bloc </th>
					<th colspan="1"> Moyenne de l'étudiant </th>
					<th colspan="1"> Moyenne de la promotion</th>
				</tr>
			</thead>
			<tbody>
			<#list blocs as b>
			<tr>
					<td> ${b.titre} <br>
						<#list competences as i>
							<#if i.blocCompetencesId == b.id>
							 	${i.description}<br>
							</#if>
						</#list>
					</td>
					<td>${moyennesEtudiant[b.id?string].getNb()}</td>
					<td>${moyennesPromotion[b.id?string].getNb()}</td>
				</tr>
			</#list>
				
			</tbody>
		</table>
		<br>
		<div>
		<span>Moyenne générale de l'étudiant : ${moyennesGenerale["etudiant"].getNb()}</span>
		<br>
		<span>Moyenne générale de la promotion : ${moyennesGenerale["promotion"].getNb()}</span>

		</div>
	</section>
	</body>

</html>