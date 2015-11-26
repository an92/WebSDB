<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">.recentcomments a{display:inline !important;padding:0 !important;margin:0 !important;}</style>
	
	<script language="javascript">
	function idAuto()
	{
		var s = "P38198";
		document.getElementById("ID").value=s;
		
		var n = "UniprotID";
		document.getElementById("SearchID").value = n;
	}
	</script>
	
	<script language="javascript">
	function nameAuto()
	{
		var s = "Protein STU1";
		document.getElementById("name").value=s;
	}
	</script>
	
	<script language="javascript">
	function kinaseAuto()
	{
		var s = "PKA";
		document.getElementById("kinase").value=s;
	}
	</script>
	
	<script language="javascript">
	function partnerAuto()
	{
		var s = "RTR1";
		document.getElementById("partner").value=s;
	}
	
	
	function checkID()
	{
		var value = document.getElementById("ID").value;
		var value_new = value.replace(/^\s+|\s+$/g, '');
		if(value_new =="" || value_new==null)
		{
			alert("Please fill in the blank!");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	function checkName()
	{
		var value = document.getElementById("name").value;
		var value_new = value.replace(/^\s+|\s+$/g, '');
		if(value_new =="" || value_new==null)
		{
			alert("Please fill in the blank!");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	function checkKinase()
	{
		var value = document.getElementById("kinase").value;
		var value_new = value.replace(/^\s+|\s+$/g, '');
		if(value_new =="" || value_new==null)
		{
			alert("Please fill in the blank!");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	function checkPartner()
	{
		var value = document.getElementById("partner").value;
		var value_new = value.replace(/^\s+|\s+$/g, '');
		if(value_new =="" || value_new==null)
		{
			alert("Please fill in the blank!");
			return false;
		}
		else
		{
			return true;
		}
	}
	</script>
<link rel='stylesheet' id='twentytwelve-style-css'  href='./CSS/Theme.css' type='text/css' media='all' />
</head>
<body>
<body class="page page-id-10 page-template page-template-page-templatesfull-width-php full-width custom-font-enabled single-author">
	<div id="page" class="hfeed site">
		<header id="masthead" class="site-header" role="banner" style="background:url(pics/kinetochore-banner.jpg)100% 0 no-repeat;">
			<hgroup style="padding-left:0px;padding-top:10px;height:90px;">
				<h1 class="site-title"><a href="http://lightning.med.monash.edu/kinetochoreDB2/" title="SecretEPDB" rel="home">Bacterial Secret Effector Protein DataBase</a></h1>
				<h2 class="site-description">A web-based resource for secreted effector protein of bacterial type  III, type IV and type VI secretion systems</h2>
			</hgroup>
			<nav id="site-navigation" class="main-navigation" role="navigation">
				<h3 class="menu-toggle">Menu</h3>
					<a class="assistive-text" href="#content" title="Skip to content">Skip to content</a>
					<div class="nav-menu">
						<ul>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/" title="Home">Home</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/Statistics.html">Statistics</a></li>
							<li class="page_item page-item-10 current_page_item"  ><a href="http://lightning.med.monash.edu/kinetochoreDB2/Search.jsp">Search</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/searchDBwithAll?db=all">Browse</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/download.html">Download</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/Help.html">Help</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/Contact.html">Contact</a></li>
							<li ><a href="http://lightning.med.monash.edu/kinetochoreDB2/Submission.html">Submission</a></li>																							
						</ul>
					</div>

				</nav><!-- #site-navigation -->
		</header><!-- #masthead -->
	
		<div class="entry-content">	
				<font face="Arial" size="3">
						<h2>ID Search</h2>
						<p>Search with Uniprot ID or database ID.</P>	
						
						<table cellpadding="4" cellspacing="0"> 
							<td >	

			
								<form action="searchDBwithID" onsubmit="return checkID()">
								<div>
									<select name="SearchID" id="SearchID"><option value="UniprotID">UniprotID</option><option value="DBid">DBid</option></select>
							  			<input type="text" name="ID" id="ID" value=""></input>
							 				<input type="submit" name="SubmitID" id="SubmitID" value="Submit" /></input>
							 				<input type="reset" name="ResetID" id="ResetID" value="Reset" /></input>
							 				<input type="button" name="Example" id="exampleID" value="Example" onclick="idAuto()"/></input>
							 	<div>					 		
								</form>
															</td> 
						</table>

				<h2>Keyword Search</h2>
				<p>Use different kinds of keywords to search database</P>
				<table cellpadding="4" cellspacing="0"> 
				<tr>		
						<form action="searchDBwithName" onsubmit="return checkName()">
					  		<p>Protein Name
					    	<input type="text" name="name" id="name" /></input>
					      	<input type="submit" name="SubmitName" id="SubmitName" value="Submit" /></input>
					      	<input type="reset" name="ResetName" id="ResetName" value="Reset" /></input>
					      	<input type="button" name="ExampleName" id="exampleName" value="Example" onclick="nameAuto()"/></input>
					      </p>	
						</form>
				</tr>
				<tr>
				<form action = "searchDBwithKinase" onsubmit="return checkKinase()">
			  		<p>Kinase 
			    	<input type="text" name="kinase" id="kinase" /></input>
			    	<input type="submit" name="SubmitKin" id="SubmitKin" value="Submit" /></input>
			    	<input type="reset" name="ResetKin" id="ResetKin" value="Reset" /></input>
			    	<input type="button" name="ExampleKinase" id="exampleKinase" value="Example" onclick="kinaseAuto()"/></input>
					</p>
				</form>
				</tr>
				<tr>
				<form action = "searchDBwithPTM">
			  		<p>Post-translational Modification Type 
			  	  	<select name = "PTM">
			  	  		<optgroup label="Phosphorylation">
			  	  			<option value = "Phosphotyrosine">Phosphotyrosine</option>
			  	  			<option value = "Phosphoserine">Phosphoserine</option>
			  	  			<option value = "Phosphothreonine">Phosphothreonine</option>
			  	  		</optgroup>
			  	  		<optgroup label="Acetylation">
			  	  			<option value = "N-acetylalanine">N-acetylalanine</option>
			  	  			<option value = "N-acetylserine">N-acetylserine</option>
			  	  			<option value = "N-acetylvaline">N-acetylvaline</option>
			  	  			<option value = "N6-acetyllysine">N6-acetyllysine</option>
			  	  			<option value = "N-acetylmethionine">N-acetylmethionine</option>
							<option value = "N-acetylthreonine">N-acetylthreonine</option>
			  	  		</optgroup>
			  	  		<optgroup label="Methylation">
			  	  			<option value = "N#N#N-trimethylalanine">N,N,N-trimethylalanine</option>
			  	  			<option value = "N#N#N-trimethylglycine">N,N,N-trimethylglycine</option>
			  	  			<option value = "N#N-dimethylglycine">N,N-dimethylglycine</option>
			  	  			<option value = "N-methylglycine">N-methylglycine</option>
			  	  			<option value = "N#N-dimethylproline">N,N-dimethylproline</option>
			  	  			<option value = "N6-methyllysine">N6-methyllysine</option>
			  	  			<option value = "N6#N6#N6-trimethyllysine">N6,N6,N6-trimethyllysine</option>
			  	  			<option value = "Leucine methyl ester">Leucine methyl ester</option>
			  	  			<option value = "Asymmetric dimethylarginine">Asymmetric dimethylarginine</option>
			  	  			<option value = "N6#N6-dimethyllysine">N6,N6-dimethyllysine</option>
			  	  			<option value = "Symmetric dimethylarginine">Symmetric dimethylarginine</option>
							<option value = "Cysteine methyl ester">Cysteine methyl ester</option>
							<option value = "N6-methylated lysine">N6-methylated lysine</option>
							<option value = "Omega-N-methylated arginine">Omega-N-methylated arginine</option>
							<option value = "N5-methylglutamine">N5-methylglutamine</option>
							<option value = "Dimethylated arginine">Dimethylated arginine</option>
							<option value = "Omega-N-methylarginine">Omega-N-methylarginine</option>
							<option value = "N#N#N-trimethylserine">N,N,N-trimethylserine</option>
							<option value = "N#N-dimethylserine">N,N-dimethylserine</option>
							<option value = "N-methylserine">N-methylserine</option>
			  	  		</optgroup>
			  	  		<optgroup label="AMP">
			  	  			<option value = "O-AMP-tyrosine">O-AMP-tyrosine</option>
			  	  			<option value = "O-AMP-threonine">O-AMP-threonine</option>
			  	  		</optgroup>
			  	  		<optgroup label="ADP-ribosyl">
			  	  			<option value = "PolyADP-ribosyl glutamic acid">PolyADP-ribosyl glutamic acid</option>
							<option value = "N6-(ADP-ribosyl)lysine">N6-(ADP-ribosyl)lysine</option>
			  	  		</optgroup>
			  	  		<optgroup label="Others">
							<option value = "Allysine">Allysine</option>
							<option value = "N6-crotonyllysine">N6-crotonyllysine</option>
			  	  			<option value = "(3S)-3-hydroxyasparagine">(3S)-3-hydroxyasparagine</option>
			  	  			<option value = "Citrulline">Citrulline</option>
							<option value = "N6-succinyllysine">N6-succinyllysine</option>
							<option value = "Nitrated tyrosine">Nitrated tyrosine</option>
							<option value = "S-nitrosocysteine">S-nitrosocysteine</option>
			  	  		</optgroup>
			  	  	</select>
			  	  	<input type="submit" name="SubmitPTM" id="SubmitPTM" value="Submit" /></input>
			 		<input type="reset" name="ResetPTM" id="ResetPTM" value="Reset" /></input>
					</p>
				</form>
				</tr>
				<tr>
				<form action = "searchDBwithPartner" onsubmit="return checkPartner()">
			  		<p>Interaction Partner Name 
			  	  	<input type="text" name="partner" id="partner" /></input>
			    	<input type="submit" name="SubmitPartner" id="SubmitPartner" value="Submit" /></input>
			    	<input type="reset" name="ResetPartner" id="ResetPartner" value="Reset" /></input>
			    	<input type="button" name="ExamplePartner" id="examplePartner" value="Example" onclick="partnerAuto()"/></input>
					</p>
				</form>
				</tr> 
				<tr>
				<form action="searchDBwithDisease">
			  		<p>Disease caused by mutation
			  		<select name="Disease">
<option value = "ACLS">ACROCALLOSAL SYNDROME</option>
<option value = "AD3">ALZHEIMER DISEASE 3</option>
<option value = "AD4">ALZHEIMER DISEASE 4</option>
<option value = "ADCADN">CEREBELLAR ATAXIA, DEAFNESS, AND NARCOLEPSY, AUTOSOMAL DOMINANT</option>
<option value = "ALS">AMYOTROPHIC LATERAL SCLEROSIS</option>
<option value = "ARH">HYPERCHOLESTEROLEMIA, AUTOSOMAL RECESSIVE</option>
<option value = "ATRX">ALPHA-THALASSEMIA/MENTAL RETARDATION SYNDROME, X-LINKED</option>
<option value = "BBS">BARDET-BIEDL SYNDROME</option>
<option value = "BBS4">BARDET-BIEDL SYNDROME 4</option>
<option value = "BFLS">BORJESON-FORSSMAN-LEHMANN SYNDROME</option>
<option value = "BRRS">BANNAYAN-RILEY-RUVALCABA SYNDROME</option>
<option value = "Cancer">Cancer</option>
<option value = "Tumor">Tumor</option>
<option value = "CDCBM2">CORTICAL DYSPLASIA, COMPLEX, WITH OTHER BRAIN MALFORMATIONS 2</option>
<option value = "CDCBM3">CORTICAL DYSPLASIA, COMPLEX, WITH OTHER BRAIN MALFORMATIONS 3</option>
<option value = "CDCBM4">CORTICAL DYSPLASIA, COMPLEX, WITH OTHER BRAIN MALFORMATIONS 4</option>
<option value = "CDLS2">CORNELIA DE LANGE SYNDROME 2</option>
<option value = "CDLS3">CORNELIA DE LANGE SYNDROME 3</option>
<option value = "CDLS4">CORNELIA DE LANGE SYNDROME 4</option>
<option value = "CFC1">CARDIOFACIOCUTANEOUS SYNDROME 1</option>
<option value = "CFEOM1">FIBROSIS OF EXTRAOCULAR MUSCLES, CONGENITAL, 1</option>
<option value = "CHM">CHOROIDEREMIA</option>
<option value = "CILD3">CILIARY DYSKINESIA, PRIMARY, 3</option>
<option value = "CILD7">CILIARY DYSKINESIA, PRIMARY, 7</option>
<option value = "CMD1U">CARDIOMYOPATHY, DILATED, 1U</option>
<option value = "CMD1V">CARDIOMYOPATHY, DILATED, 1V</option>
<option value = "CMT2A1">CHARCOT-MARIE-TOOTH DISEASE, AXONAL, TYPE 2A1</option>
<option value = "CMT2O">CHARCOT-MARIE-TOOTH DISEASE, AXONAL, TYPE 2O</option>
<option value = "CWS1">COWDEN SYNDROME 1</option>
<option value = "DKCX">DYSKERATOSIS CONGENITA, X-LINKED</option>
<option value = "endometrial hyperplasia">Endometrial Hyperplasia</option>
<option value = "FAP">FAMILIAL ADENOMATOUS POLYPOSIS 1</option>
<option value = "glioblastoma">Glioblastoma</option>
<option value = "glioma">Glioma</option>
<option value = "hepatoblastoma">Hepatoblastoma</option>
<option value = "GLM2">GLIOMA SUSCEPTIBILITY 2</option>
<option value = "HHS">HOYERAAL-HREIDARSSON SYNDROME</option>
<option value = "HLS2">HYDROLETHALUS SYNDROME 2</option>
<option value = "HMN7B">NEURONOPATHY, DISTAL HEREDITARY MOTOR, TYPE VIIB</option>
<option value = "HNSCC">SQUAMOUS CELL CARCINOMA, HEAD AND NECK</option>
<option value = "HSN1E">NEUROPATHY, HEREDITARY SENSORY, TYPE IE</option>
<option value = "ICF1">IMMUNODEFICIENCY-CENTROMERIC INSTABILITY-FACIAL ANOMALIES SYNDROME 1</option>
<option value = "IIAE3">ENCEPHALOPATHY, ACUTE, INFECTION-INDUCED, SUSCEPTIBILITY TO, 3</option>
<option value = "IMD26">IMMUNODEFICIENCY 26 WITH OR WITHOUT NEUROLOGIC ABNORMALITIES</option>
<option value = "JBTS12">JOUBERT SYNDROME</option>
<option value = "LDD">LUMBAR DISC DISEASE</option>
<option value = "LEOPARD3">LEOPARD3</option>
<option value = "LIS1">LISSENCEPHALY 1</option>
<option value = "LOA">LEBER OPTIC ATROPHY, SUSCEPTIBILITY TO</option>
<option value = "LPRD3">LEOPARD SYNDROME 3</option>
<option value = "MCLMR">MICROCEPHALY WITH OR WITHOUT CHORIORETINOPATHY, LYMPHEDEMA, OR MENTAL RETARDATION</option>
<option value = "MCPH4">MICROCEPHALY 4, PRIMARY, AUTOSOMAL RECESSIVE</option>
<option value = "MCPH13">MICROCEPHALY 13, PRIMARY, AUTOSOMAL RECESSIVE</option>
<option value = "MCPH5">MICROCEPHALY 5, PRIMARY, AUTOSOMAL RECESSIVE</option>
<option value = "MDB">MEDULLOBLASTOMA</option>
<option value = "melanoma">Melanoma</option>
<option value = "MGORS3">MEIER-GORLIN SYNDROME 3</option>
<option value = "MGORS4">MEIER-GORLIN SYNDROME 4</option>
<option value = "MRD13">MENTAL RETARDATION, AUTOSOMAL DOMINANT 13</option>
<option value = "MRD15">MENTAL RETARDATION, AUTOSOMAL DOMINANT 15</option>
<option value = "MRD16">MENTAL RETARDATION, AUTOSOMAL DOMINANT 16</option>
<option value = "MRXS13">MENTAL RETARDATION, X-LINKED, SYNDROMIC 13</option>
<option value = "MVA1">MOSAIC VARIEGATED ANEUPLOIDY SYNDROME 1</option>
<option value = "NHL">NON-HODGKIN LYMPHOMA</option>
<option value = "NS7">NOONAN SYNDROME 7</option>
<option value = "PCS">PREMATURE CHROMATID SEPARATION TRAIT</option>
<option value = "PERRYS">PERRY SYNDROME</option>
<option value = "PHS">PALLISTER-HALL SYNDROME</option>
<option value = "RB">RETINOBLASTOMA</option>
<option value = "RP70">RETINITIS PIGMENTOSA 70</option>
<option value = "RSTS1">RUBINSTEIN-TAYBI SYNDROME 1</option>
<option value = "RTT">RETT SYNDROME</option>
<option value = "SBH">LISSENCEPHALY, X-LINKED, 1</option>
<option value = "SEMDJL2">SPONDYLOEPIMETAPHYSEAL DYSPLASIA WITH JOINT LAXITY TYPE 2</option>
<option value = "SMALED1">SPINAL MUSCULAR ATROPHY, LOWER EXTREMITY-PREDOMINANT, 1, AUTOSOMAL DOMINANT</option>
<option value = "SRTD3">SHORT-RIB THORACIC DYSPLASIA 3 WITH OR WITHOUT POLYDACTYLY</option>
<option value = "VATER">VATER ASSOCIATION</option>

			  		</select>
			  		<input type="submit" name="SubmitDisease" id="SubmitDisease" value="Submit" /></input>
			 		<input type="reset" name="ResetDisease" id="ResetDisease" value="Reset" /></input>
			 		</p>
			  	</form>
			  	</tr>
			  	
			  	<tr>
				<form action="searchDBwithSpecies" >
			  		<p>Species
			  		<select name="Species">
<option value = "Mus musculus (Mouse)">Mus musculus (Mouse)</option>
<option value = "Gallus gallus (Chicken)">Gallus gallus (Chicken)</option>
<option value = "Homo sapiens (Human)">Homo sapiens (Human)</option>
<option value = "Rattus norvegicus (Rat)">Rattus norvegicus (Rat)</option>
<option value = "Arabidopsis thaliana (Mouse-ear cress)">Arabidopsis thaliana (Mouse-ear cress)</option>
<option value = "Xenopus laevis (African clawed frog)">Xenopus laevis (African clawed frog)</option>
<option value = "Neosartorya fumigata (strain ATCC MYA-4609 / Af293 / CBS 101355 / FGSA1100) (Aspergillus fumigatus)">Neosartorya fumigata (strain ATCC MYA-4609 / Af293 / CBS 101355 / FGSA1100) (Aspergillus fumigatus)</option>
<option value = "Dictyostelium discoideum (Slime mold)">Dictyostelium discoideum (Slime mold)</option>
<option value = "Macaca fascicularis (Crab-eating macaque) (Cynomolgus monkey)">Macaca fascicularis (Crab-eating macaque) (Cynomolgus monkey)</option>
<option value = "Neurospora crassa (strain ATCC 24698 / 74-OR23-1A / CBS 708.71 / DS1257 / FGSC 987)">Neurospora crassa (strain ATCC 24698 / 74-OR23-1A / CBS 708.71 / DS1257 / FGSC 987)</option>
<option value = "Ashbya gossypii (strain ATCC 10895 / CBS 109.51 / FGSC 9923 / NRRY-1056) (Yeast) (Eremothecium gossypii)">Ashbya gossypii (strain ATCC 10895 / CBS 109.51 / FGSC 9923 / NRRY-1056) (Yeast) (Eremothecium gossypii)</option>
<option value = "Kluyveromyces lactis (strain ATCC 8585 / CBS 2359 / DSM 70799 / NBR1267 / NRRL Y-1140 / WM37) (Yeast) (Candida sphaerica)">Kluyveromyces lactis (strain ATCC 8585 / CBS 2359 / DSM 70799 / NBR1267 / NRRL Y-1140 / WM37) (Yeast) (Candida sphaerica)</option>
<option value = "Botryotinia fuckeliana (Noble rot fungus) (Botrytis cinerea)">Botryotinia fuckeliana (Noble rot fungus) (Botrytis cinerea)</option>
<option value = "Schizosaccharomyces pombe (strain 972 / ATCC 24843) (Fission yeast)">Schizosaccharomyces pombe (strain 972 / ATCC 24843) (Fission yeast)</option>
<option value = "Saccharomyces cerevisiae (strain ATCC 204508 / S288c) (Baker's yeast)">Saccharomyces cerevisiae (strain ATCC 204508 / S288c) (Baker's yeast)</option>
<option value = "Oryza sativa subsp. japonica (Rice)">Oryza sativa subsp. japonica (Rice)</option>
<option value = "Drosophila melanogaster (Fruit fly)">Drosophila melanogaster (Fruit fly)</option>
<option value = "Bos taurus (Bovine)">Bos taurus (Bovine)</option>
<option value = "Xenopus tropicalis (Western clawed frog) (Silurana tropicalis)">Xenopus tropicalis (Western clawed frog) (Silurana tropicalis)</option>
<option value = "Caenorhabditis elegans">Caenorhabditis elegans</option>
<option value = "Danio rerio (Zebrafish) (Brachydanio rerio)">Danio rerio (Zebrafish) (Brachydanio rerio)</option>
<option value = "Drosophila ananassae (Fruit fly)">Drosophila ananassae (Fruit fly)</option>
<option value = "Pongo abelii (Sumatran orangutan) (Pongo pygmaeus abelii)">Pongo abelii (Sumatran orangutan) (Pongo pygmaeus abelii)</option>
<option value = "Drosophila pseudoobscura pseudoobscura (Fruit fly)">Drosophila pseudoobscura pseudoobscura (Fruit fly)</option>
<option value = "Drosophila grimshawi (Fruit fly) (Idiomyia grimshawi)">Drosophila grimshawi (Fruit fly) (Idiomyia grimshawi)</option>
<option value = "Aspergillus clavatus (strain ATCC 1007 / CBS 513.65 / DSM 816 / NCT3887 / NRRL 1)">Aspergillus clavatus (strain ATCC 1007 / CBS 513.65 / DSM 816 / NCT3887 / NRRL 1)</option>
<option value = "Ustilago maydis (strain 521 / FGSC 9021) (Corn smut fungus)">Ustilago maydis (strain 521 / FGSC 9021) (Corn smut fungus)</option>
<option value = "Candida glabrata (strain ATCC 2001 / CBS 138 / JCM 3761 / NBRC 0622NRRL Y-65) (Yeast) (Torulopsis glabrata)">Candida glabrata (strain ATCC 2001 / CBS 138 / JCM 3761 / NBRC 0622NRRL Y-65) (Yeast) (Torulopsis glabrata)</option>
<option value = "Cricetulus griseus (Chinese hamster) (Cricetulus barabensis griseus)">Cricetulus griseus (Chinese hamster) (Cricetulus barabensis griseus)</option>
<option value = "Yarrowia lipolytica (strain CLIB 122 / E 150) (Yeast) (Candidlipolytica)">Yarrowia lipolytica (strain CLIB 122 / E 150) (Yeast) (Candidlipolytica)</option>
<option value = "Caenorhabditis briggsae">Caenorhabditis briggsae</option>
<option value = "Emericella nidulans (strain FGSC A4 / ATCC 38163 / CBS 112.46 / NRR194 / M139) (Aspergillus nidulans)">Emericella nidulans (strain FGSC A4 / ATCC 38163 / CBS 112.46 / NRR194 / M139) (Aspergillus nidulans)</option>
<option value = "Aspergillus oryzae (strain ATCC 42149 / RIB 40) (Yellow koji mold)">Aspergillus oryzae (strain ATCC 42149 / RIB 40) (Yellow koji mold)</option>
<option value = "Encephalitozoon cuniculi (strain GB-M1) (Microsporidian parasite)">Encephalitozoon cuniculi (strain GB-M1) (Microsporidian parasite)</option>
<option value = "Tetrahymena thermophila (strain SB210)">Tetrahymena thermophila (strain SB210)</option>
<option value = "Gibberella zeae (strain PH-1 / ATCC MYA-4620 / FGSC 9075 / NRRL 31084(Wheat head blight fungus) (Fusarium graminearum)">Gibberella zeae (strain PH-1 / ATCC MYA-4620 / FGSC 9075 / NRRL 31084(Wheat head blight fungus) (Fusarium graminearum)</option>
<option value = "Debaryomyces hansenii (strain ATCC 36239 / CBS 767 / JCM 1990 / NBR0083 / IGC 2968) (Yeast) (Torulaspora hansenii)">Debaryomyces hansenii (strain ATCC 36239 / CBS 767 / JCM 1990 / NBR0083 / IGC 2968) (Yeast) (Torulaspora hansenii)</option>
<option value = "Pyrenophora tritici-repentis (strain Pt-1C-BFP) (Wheat tan spofungus) (Drechslera tritici-repentis)">Pyrenophora tritici-repentis (strain Pt-1C-BFP) (Wheat tan spofungus) (Drechslera tritici-repentis)</option>
<option value = "Sus scrofa (Pig)">Sus scrofa (Pig)</option>
<option value = "Aspergillus flavus (strain ATCC 200026 / FGSC A1120 / NRRL 3357 / JC12722 / SRRC 167)">Aspergillus flavus (strain ATCC 200026 / FGSC A1120 / NRRL 3357 / JC12722 / SRRC 167)</option>
<option value = "Komagataella pastoris (strain GS115 / ATCC 20864) (Yeast) (Pichipastoris)">Komagataella pastoris (strain GS115 / ATCC 20864) (Yeast) (Pichipastoris)</option>
<option value = "Aedes aegypti (Yellowfever mosquito) (Culex aegypti)">Aedes aegypti (Yellowfever mosquito) (Culex aegypti)</option>
<option value = "Neosartorya fischeri (strain ATCC 1020 / DSM 3700 / FGSC A1164 / NRR181) (Aspergillus fischerianus)">Neosartorya fischeri (strain ATCC 1020 / DSM 3700 / FGSC A1164 / NRR181) (Aspergillus fischerianus)</option>
<option value = "Kluyveromyces marxianus (Yeast) (Candida kefyr)">Kluyveromyces marxianus (Yeast) (Candida kefyr)</option>
<option value = "Strongylocentrotus purpuratus (Purple sea urchin)">Strongylocentrotus purpuratus (Purple sea urchin)</option>
<option value = "Felis catus (Cat) (Felis silvestris catus)">Felis catus (Cat) (Felis silvestris catus)</option>
<option value = "Canis familiaris (Dog) (Canis lupus familiaris)">Canis familiaris (Dog) (Canis lupus familiaris)</option>
<option value = "Chlamydomonas reinhardtii (Chlamydomonas smithii)">Chlamydomonas reinhardtii (Chlamydomonas smithii)</option>
<option value = "Candida albicans (strain SC5314 / ATCC MYA-2876) (Yeast)">Candida albicans (strain SC5314 / ATCC MYA-2876) (Yeast)</option>
<option value = "Cryptococcus neoformans var. neoformans serotype D (strain B-3501A(Filobasidiella neoformans)">Cryptococcus neoformans var. neoformans serotype D (strain B-3501A(Filobasidiella neoformans)</option>
<option value = "Cryptococcus neoformans var. neoformans serotype D (strain JEC21ATCC MYA-565) (Filobasidiella neoformans)">Cryptococcus neoformans var. neoformans serotype D (strain JEC21ATCC MYA-565) (Filobasidiella neoformans)</option>
<option value = "Candida albicans (Yeast)">Candida albicans (Yeast)</option>
<option value = "Parascaris univalens">Parascaris univalens</option>
<option value = "Pan troglodytes (Chimpanzee)">Pan troglodytes (Chimpanzee)</option>
<option value = "Nicotiana tabacum (Common tobacco)">Nicotiana tabacum (Common tobacco)</option>
<option value = "Fusarium solani subsp. pisi (Nectria haematococca)">Fusarium solani subsp. pisi (Nectria haematococca)</option>
<option value = "Osmerus mordax (Rainbow smelt) (Atherina mordax)">Osmerus mordax (Rainbow smelt) (Atherina mordax)</option>
<option value = "Tripneustes gratilla (Hawaian sea urchin) (Echinus gratilla)">Tripneustes gratilla (Hawaian sea urchin) (Echinus gratilla)</option>
<option value = "Drosophila sechellia (Fruit fly)">Drosophila sechellia (Fruit fly)</option>
<option value = "Aspergillus terreus (strain NIH 2624 / FGSC A1156)">Aspergillus terreus (strain NIH 2624 / FGSC A1156)</option>
<option value = "Drosophila yakuba (Fruit fly)">Drosophila yakuba (Fruit fly)</option>
<option value = "Tragulus javanicus (Lesser Malay chevrotain) (Lesser mouse deer)">Tragulus javanicus (Lesser Malay chevrotain) (Lesser mouse deer)</option>
<option value = "Bombyx mori (Silk moth)">Bombyx mori (Silk moth)</option>
<option value = "Saccharomyces cerevisiae (strain RM11-1a) (Baker's yeast)">Saccharomyces cerevisiae (strain RM11-1a) (Baker's yeast)</option>
<option value = "Drosophila persimilis (Fruit fly)">Drosophila persimilis (Fruit fly)</option>
<option value = "Drosophila lutescens (Fruit fly)">Drosophila lutescens (Fruit fly)</option>
<option value = "Leishmania chagasi">Leishmania chagasi</option>
<option value = "Drosophila mojavensis (Fruit fly)">Drosophila mojavensis (Fruit fly)</option>
<option value = "Leishmania major">Leishmania major</option>
<option value = "Gibberella moniliformis (Maize ear and stalk rot fungus) (Fusariuverticillioides)">Gibberella moniliformis (Maize ear and stalk rot fungus) (Fusariuverticillioides)</option>
<option value = "Phaeosphaeria nodorum (strain SN15 / ATCC MYA-4574 / FGSC 10173(Glume blotch fungus) (Septoria nodorum)">Phaeosphaeria nodorum (strain SN15 / ATCC MYA-4574 / FGSC 10173(Glume blotch fungus) (Septoria nodorum)</option>
<option value = "Lachancea thermotolerans (strain ATCC 56472 / CBS 6340 / NRRL Y-8284(Yeast) (Kluyveromyces thermotolerans)">Lachancea thermotolerans (strain ATCC 56472 / CBS 6340 / NRRL Y-8284(Yeast) (Kluyveromyces thermotolerans)</option>
<option value = "Doryteuthis pealeii (Longfin inshore squid) (Loligo pealeii)">Doryteuthis pealeii (Longfin inshore squid) (Loligo pealeii)</option>
<option value = "Cylindrotheca fusiformis (Marine diatom)">Cylindrotheca fusiformis (Marine diatom)</option>
<option value = "Vanderwaltozyma polyspora (strain ATCC 22028 / DSM 70294(Kluyveromyces polysporus)">Vanderwaltozyma polyspora (strain ATCC 22028 / DSM 70294(Kluyveromyces polysporus)</option>
<option value = "Drosophila simulans (Fruit fly)">Drosophila simulans (Fruit fly)</option>
<option value = "Drosophila eugracilis (Fruit fly)">Drosophila eugracilis (Fruit fly)</option>
<option value = "Drosophila erecta (Fruit fly)">Drosophila erecta (Fruit fly)</option>
<option value = "Drosophila orena (Fruit fly)">Drosophila orena (Fruit fly)</option>
<option value = "Drosophila teissieri (Fruit fly)">Drosophila teissieri (Fruit fly)</option>
<option value = "Drosophila mauritiana (Fruit fly)">Drosophila mauritiana (Fruit fly)</option>
<option value = "Millerozyma farinosa (Yeast) (Pichia farinosa)">Millerozyma farinosa (Yeast) (Pichia farinosa)</option>
<option value = "Kazachstania servazzii (Yeast) (Saccharomyces servazzii)">Kazachstania servazzii (Yeast) (Saccharomyces servazzii)</option>
<option value = "Pyrenophora teres f. teres (strain 0-1) (Barley net blotch fungus(Drechslera teres f. teres)">Pyrenophora teres f. teres (strain 0-1) (Barley net blotch fungus(Drechslera teres f. teres)</option>
<option value = "Neosartorya fumigata (strain CEA10 / CBS 144.89 / FGSC A1163(Aspergillus fumigatus)">Neosartorya fumigata (strain CEA10 / CBS 144.89 / FGSC A1163(Aspergillus fumigatus)</option>
<option value = "Nectria haematococca (strain 77-13-4 / ATCC MYA-4622 / FGSC 9596MPVI) (Fusarium solani subsp. pisi)">Nectria haematococca (strain 77-13-4 / ATCC MYA-4622 / FGSC 9596MPVI) (Fusarium solani subsp. pisi)</option>
<option value = "Salmo salar (Atlantic salmon)">Salmo salar (Atlantic salmon)</option>
<option value = "Saccharomyces cerevisiae (strain AWRI1631) (Baker's yeast)">Saccharomyces cerevisiae (strain AWRI1631) (Baker's yeast)</option>
<option value = "Saccharomyces cerevisiae (strain Lalvin EC1118 / Prise de mousse(Baker's yeast)">Saccharomyces cerevisiae (strain Lalvin EC1118 / Prise de mousse(Baker's yeast)</option>
<option value = "Plasmodium falciparum (isolate 3D7)">Plasmodium falciparum (isolate 3D7)</option>
<option value = "Syncephalastrum racemosum (Filamentous fungus)">Syncephalastrum racemosum (Filamentous fungus)</option>
<option value = "Ovis aries (Sheep)">Ovis aries (Sheep)</option>
<option value = "Heliocidaris crassispina (Sea urchin) (Anthocidaris crassispina)">Heliocidaris crassispina (Sea urchin) (Anthocidaris crassispina)</option>
<option value = "Saccharomyces cerevisiae (strain JAY291) (Baker's yeast)">Saccharomyces cerevisiae (strain JAY291) (Baker's yeast)</option>
<option value = "Camelus dromedarius (Dromedary) (Arabian camel)">Camelus dromedarius (Dromedary) (Arabian camel)</option>
<option value = "Muntiacus reevesi (Reeve's muntjac) (Chinese muntjac)">Muntiacus reevesi (Reeve's muntjac) (Chinese muntjac)</option>
<option value = "Zygosaccharomyces rouxii (strain ATCC 2623 / CBS 732 / NBRC 1130NCYC 568 / NRRL Y-229) (Candida mogii)">Zygosaccharomyces rouxii (strain ATCC 2623 / CBS 732 / NBRC 1130NCYC 568 / NRRL Y-229) (Candida mogii)</option>
<option value = "Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958) (Ricblast fungus) (Pyricularia oryzae)">Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958) (Ricblast fungus) (Pyricularia oryzae)</option>
<option value = "Colletotrichum graminicola (strain M1.001 / M2 / FGSC 10212) (Maizanthracnose fungus) (Glomerella graminicola)">Colletotrichum graminicola (strain M1.001 / M2 / FGSC 10212) (Maizanthracnose fungus) (Glomerella graminicola)</option>
<option value = "Saccharomyces cerevisiae (strain YJM789) (Baker's yeast)">Saccharomyces cerevisiae (strain YJM789) (Baker's yeast)</option>
<option value = "Paramecium tetraurelia">Paramecium tetraurelia</option>
<option value = "Drosophila virilis (Fruit fly)">Drosophila virilis (Fruit fly)</option>
<option value = "Oryctolagus cuniculus (Rabbit)">Oryctolagus cuniculus (Rabbit)</option>
			  		</select>
			  		<input type="submit" name="SubmitSpecies" id="SubmitSpecies" value="Submit" /></input>
			 		<input type="reset" name="ResetSpecies" id="ResetSpecies" value="Reset" /></input>
			 		</p>
			  	</form>
			  	</tr>
			  	
			  	
			  	</table>
			  	</font>
		</div>
		
		
		<footer id="colophon" role="contentinfo">
			<div class="site-info" style="text-align:center;">
		<p><img src="pics/med2.gif" width="258" height="37"/>
		<img src="pics/logo_arc1.png" width="349" height="37"/></p>
						Copyright &copy; 2015. <a href="http://pxgrid.med.monash.edu.au/projects/">Buckle Laboratory</a>, <a href="http://www.med.monash.edu.au/">Department of Biochemistry and Molecular Biology, Faculty of Medicine</a>, <a href="http://monash.edu/" title="">Monash University</a>, Australia
		</div><!-- .site-info -->
		</footer><!-- #colophon -->
		
	</div><!-- #page -->
	<script type='text/javascript' src='http://lightning.med.monash.edu/kinetochore/wp-content/themes/twentytwelve/js/navigation.js?ver=1.0'></script>
</body>

</body>
</html>