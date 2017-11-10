import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;


public class Interconnexion {

	// Cette fonction construit la CDF
	public static double Proba(int N){
		double result = 0.001*N*N*N+ 0.3854*N*N + 1913.9;
		return result;
	}
	
	// Je ne sais plus ce que cela sert.
	public static double Variance(){
		double variance = Math.random();
		variance = variance - 0.5;
		variance = variance * 50;
		return variance;
	}
	
	// La fonction de Durée interconnexion:
	public static double Inter(int N){
		double result;
		
		result = 0.00001*N*N*N*N- 0.0018*N*N*N+0.1676*N*N- 8.7611*N + 327.58;
		return result;
	}
	
	// La fonction de Durée de la connexion:
	public static double Duree(int N){
		double result = 0;
		result = -0.000006*N*N*N*N*N*N+ 0.0002*N*N*N- 0.0134*N*N+ 0.3454*N + 2.8088;
		return result;
	}
	
	// On construit enfin le graph aléatoire.
	public static void Creation_graphe(int nb_noeud,  int nb_temps){
		
		int i, j, k;
		double random;
		double[][] creation;
		double[][] deletion;
		int[][] data;
		data = new int[nb_noeud][nb_noeud];
		creation = new double[nb_noeud][nb_noeud];
		deletion = new double[nb_noeud][nb_noeud];
		int nb_connexion;
		int nb_apparut = 0;
		
		
		//Initialisation
		for(i = 0; i<nb_noeud; i++){
			for(j = 0; j<nb_noeud; j++){
				data[i][j] = 0;
			}
		}
		for(i = 0; i<nb_noeud; i++){
			for(j = 0; j<nb_noeud; j++){
				creation[i][j] = 0.01;
				deletion[i][j] = 0.01;
			}
		}

		//Ouverture et création
		try{
			File f = new File ("InterconnexionData.txt");
			FileWriter gw = new FileWriter (f);
	
			for(i = 1; i<nb_noeud; i++){
				for(j = 1; j<nb_noeud; j++){
					if(i != j ){
						nb_apparut++;
						nb_connexion = 0;
						k = (int) Math.random() * nb_temps + 1;
						data[i][j]=1;
						gw.write (String.valueOf(i));
						gw.write (" ");
						gw.write (String.valueOf(j));
						gw.write (" ");
						gw.write (String.valueOf(k));
						gw.write (" ");
					
						while(k<nb_temps){
							nb_connexion++;
							k = (int) (k + Inter(nb_connexion) + Variance());
							gw.write (String.valueOf(k));
							gw.write ("\n");
							gw.write (String.valueOf(i));
							gw.write (" ");
							gw.write (String.valueOf(j));
							gw.write (" ");
							
							k = (int) (k + Duree(nb_connexion) + Variance());
							gw.write (String.valueOf(k));
							gw.write (" ");	
						}
						gw.write ("" + nb_temps);
						gw.write ("\n");
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
