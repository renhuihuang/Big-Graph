import java.util.*;
import java.io.*;


public class Degree {

	//##########################################
	// Modification de la proba
	//##########################################
	
	
	// Si deux neouds ne sont pas connectés, alors la probabilité de leur prochaine liaison augmente.
	// Si un noeud se connecte aux plusieurs noeuds, alors ce noeud est considéré comme "populaire".
	public static void Ajout_proba(int noeud, int degre, double[] creation, double[] deletion){
		if(degre == 0 && Etre_Isoler(noeud, creation, deletion) == false){
			creation[noeud] = 0.5;
		}	
		if(degre >= 1 && Etre_Isoler(noeud, creation, deletion) == false){
			Populaire(noeud, creation, deletion);
		}	
	}
	
	// Si un noeud possède plein de voisin et il n'est pas populaire, alors on va retirer des voisins.
	public static void Retire_proba(int noeud, int degre, double[] creation, double[] deletion){
		if(degre == 1 && Etre_Populaire(noeud, creation, deletion) == false){
			Isoler(noeud, creation, deletion);
		}	
		if(degre >= 2 && Etre_Populaire(noeud, creation, deletion) == false){
			deletion[noeud] = 0.01;
		}	
	}
	
	// Lorsqu'un noeud est isolé, alors ses voisins diminuent.
	public static void Isoler(int noeud, double[] creation, double[] deletion){
		creation[noeud] = 0.01;
		deletion[noeud] = 0.5;
	}
	
	// Si un noeud est populaire, le nombre de voisin augmente.
	public static void Populaire(int noeud, double[] creation, double[] deletion){
		creation[noeud] = 1;
		deletion[noeud] = 0.001;
	}
	
	public static boolean Etre_Isoler(int noeud, double[] creation, double[] deletion){
		return creation[noeud] == 0.1 && deletion[noeud] == 1;
	}
	
	public static boolean Etre_Populaire(int noeud, double[] creation, double[] deletion){
		return creation[noeud] == 1 && deletion[noeud] == 0.01;
	}
	
	//##########################################
	// Creation du graphe
	//##########################################

	public static void Creation_graphe(int nb_noeud,  int nb_temps){
	
		int i, j, k;
		double random;
		double[] creation;
		double[] deletion;
		int[][] data;
		data = new int[nb_noeud][nb_noeud];
		creation = new double[nb_noeud];
		deletion = new double[nb_noeud];
		
		
		//Initialisation
		for(i = 0; i<nb_noeud; i++){
			for(j = 0; j<nb_noeud; j++){
				data[i][j] = 0;
			}
		}
		for(i = 0; i<nb_noeud; i++){
			creation[i] = 0.05;
			deletion[i] = 0.05;
		}

		//Ouverture et création
		try{
			File f = new File ("DegreeData.txt");
			FileWriter gw = new FileWriter (f);
	
			for(i = 1; i<nb_noeud; i++){
				for(j = 1; j<nb_noeud; j++){
					for(k = 1; k<nb_temps; k++){
						if(i != j){
							random = Math.random();
							if(data[i][j]==0 && (creation[i] * creation[j]) > random){
								//Modification de la proba pour i et j
								Ajout_proba(i, Commun.degre(i, data, nb_noeud), creation, deletion);
								Ajout_proba(j, Commun.degre(j, data, nb_noeud), creation, deletion);
								
								//Création des données
								data[i][j]=1;
								gw.write (String.valueOf(i));
								gw.write (" ");
								gw.write (String.valueOf(j));
								gw.write (" ");
								gw.write (String.valueOf(k));
								gw.write (" ");
							}
							if(data[i][j]==1 && (deletion[i] * deletion[j])> random){
								//Modification de la proba pour i et j
								Retire_proba(i, Commun.degre(i, data, nb_noeud), creation, deletion);
								Retire_proba(j, Commun.degre(j, data, nb_noeud), creation, deletion);
								//Termination des données
								data[i][j]=0;
								gw.write (String.valueOf(k));
								gw.write ("\n");
							}
							if(data[i][j]==1 && k==nb_temps-1){
								data[i][j]=0;
								gw.write (String.valueOf(k));
								gw.write ("\n");
							}
						}
					}
				}
			}
			gw.close();
		}
	
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

}
