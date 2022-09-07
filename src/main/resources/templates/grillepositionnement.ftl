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

	</head>

	<body>
		<section>
		<h1> Grille de positionnement</h1>
		<h2> ${etudiant.nom} ${etudiant.prenom}</h2>
		<table>
			<thead>
				<tr>
					<th colspan="1"> Module </th>
					<th colspan="1"> Date d'intervention </th>
					<th colspan="1"> Intervenant</th>
					<th colspan="1"> Objectifs pédagogiques</th>
					<th>Niveau Début</th>
					<th>Niveau Fin</th>
				</tr>
			</thead>
			<tbody>

			<#list data as mge>
				<tr>
				<td>${mge.formation.titre}</td>
				<td>${mge.intervention.dateDebut}</td>
				<td>${mge.nomCompletFormateur}</td>
				<td>${mge.formation.objectifsPedagogiques}</td>
				<td style="background-color:${mge.niveauDebut.codeCouleurHexa}">${mge.niveauDebut.description}</td>
				<td style="background-color:${mge.niveauFin.codeCouleurHexa}">${mge.niveauFin.description}</td>
				</tr>
			</#list>
			</tbody>
		</table>
		</section>
	</body>
</html>