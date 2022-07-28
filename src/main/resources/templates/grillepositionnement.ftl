<!DOCTYPE html>

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
		<h1> Grille de positionnement</h1>
		<h2> ${etudiant.nom} ${etudiant.prenom}</h2>
		<table>
			<thead>
				<tr>
					<th colspan="1"> Module </th>
					<th colspan="1"> Date d'intervention </th>
					<th colspan="1"> Intervenant</th>
					<th colspan="1"> Objectifs pédagogiques</th>
					<th>${etudiant.prenom}</th>
				</tr>
			</thead>
			<tbody>
			<#list positionnements?keys as key>
				<tr>
					<td> ${key.formation.titre}</td>
					<td> ${key.dateDebut}</td>
					<td> ${key.formateur}</td>
					<td> ${key.formation.objectifsPedagogiques}</td>
					<td>${positionnements[key].niveauDebut.valeur}</td>
				</tr>
			</#list>
			</tbody>
		<table>
		</section>
	</body>
</html>