
<!DOCTYPE html>

<html>
	<head>
	<style>
	
@page { 

    size: landscape;
	}
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
			<h1 >Recap Competences</h1>
			<br>
			<h2> Titre Professionnel<h2>	
			<br>
			<h2 class="red">${titrePro.titre}</h1>
			<br>
			
	</section>
	
	<section>
		<h2> Controle Continu </h2>
		<table class="nico-table">
			<thead>
				<tr>
					<th colspan="1"> Bloc </th>
					<th colspan="1"> Competences </th>
					
				</tr>
			</thead>
			<tbody>
			<#list blocCompsEnrichi as bce>
			<tr>
					<td> 
					${bce.bloc.titre} 
					</td>
					<td>
						<#list bce.comps as i>
							
							 	${i.titre}<br>
						
						</#list>
					</td>
				</tr>
			</#list>
				
			</tbody>
		</table>
		<br>
		<div>
		
		</div>
	</section>
	</body>

</html>