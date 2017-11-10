import java.util.*;
import java.io.*;


public class Commun{

	public static int voisin(int graphe_i, int graphe_j, int[][] graphe, int nb_node){
		int i;
		int nb_voisin = 0;

		for(i=0; i<nb_node; i++){
			if(graphe[i][graphe_i] == 1 && graphe[i][graphe_j] == 1){
				nb_voisin++;
			}
		}
		return nb_voisin;
	}

	public static int degre(int graphe_i, int[][] graphe, int nb_node){
		int i;
		int nb_degre = 0;

		for(i=0; i<nb_node; i++){
			if(graphe[i][graphe_i] == 1){
				nb_degre++;
			}
		}
		return nb_degre;
	}

    public static void Affichage(String fichier){

        List<String> items = new ArrayList<String>();
        int counter = 0;
        int i;
        int j, k, graphe_i, graphe_j, nb_voisin, coefficient;
		coefficient = 0;
		int duration = 0;
		int nb_node = 0;
		int test[];
		test = new int[100000];

        try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String line;
            StringTokenizer splitter;
            int[][] data;
			int[][] graphe;
			int[][] tab_coeff;
			int[][] tab_degre;
			int[] interconnexion;
			int d;
			int inter = 0;
			int activation = 0;
			
			//#####################################
            // Enregistrement des donnees
			//#####################################
            while ((line=br.readLine())!=null){
                items.add(line);
            }

            data = new int[items.size()][4];
            for (String item : items) {
                splitter = new StringTokenizer(item, " ");
                data[counter][0] = Integer
                    .parseInt((String) splitter.nextElement());
                data[counter][1] = Integer
                    .parseInt((String) splitter.nextElement());
                data[counter][2] = Integer
                    .parseInt((String) splitter.nextElement());
                data[counter][3] = Integer
                    .parseInt((String) splitter.nextElement());
                counter++;
            }

			/*for (i=0; i<counter; i++) {
				System.out.println(data[i][0] + " " + data[i][1] + " " + data[i][2] + " " + data[i][3]);
			}*/


            for(i = 0; i<counter; i++){
                duration = Math.max(data[i][0], duration);
				nb_node =  Math.max(data[i][2], nb_node);
            }

            System.out.println("Nombre de noeud : " + nb_node);
            System.out.println("Nombre de arete : " + counter);
            System.out.println("Duration : " + duration);

			//#####################################
			// Creation du graphe
			//#####################################
			graphe = new int[nb_node + 1][nb_node + 1];
			for(i = 0; i<nb_node + 1; i++){
				for(j = 0; j<nb_node + 1; j++){
					graphe[i][j] = 0;
				}
			}


			tab_coeff = new int[10][4];
			tab_degre = new int[50][3];
			interconnexion = new int[100000];
			
			for(k = 0; k<100000; k++){
				interconnexion[k] = 0;
			}
			
			for(k = 0; k<50; k++){
				tab_degre[k][0] = 0;
				tab_degre[k][1] = 0;
				tab_degre[k][2] = 0;
			}

			
			//#####################################
            // Création de la matrice
			//#####################################
			
			
			int mm = 0;
			for(k = 0; k<duration/2; k++){

				for(i = 0; i<counter; i++){
					if(data[i][0] > k){
						break;
					}
					//Instant i, on modifie la matrice.
					if(data[i][0] == k){
						graphe_i = data[i][1];
						graphe_j = data[i][2];
						if(data[i][3]==0){
							graphe[graphe_i][graphe_j] = 1;
							graphe[graphe_j][graphe_i] = 1;
						}
						if(data[i][3]==1){
							graphe[graphe_i][graphe_j] = 0;
							graphe[graphe_j][graphe_i] = 0;
						}
					}
				}
				// On a maintenant une matrice qui correspond ¨¤ l'instant k.
				
				
				
				
				//#####################################
	            // Interconnexion
				//#####################################
				
				if(graphe[5][47] == 0){
					activation = 1;
					interconnexion[inter] = interconnexion[inter] + 1;
				}
					
				if(graphe[5][47] == 1 && activation == 1){
					activation = 0;
					inter++;
				}
	

				
				//#####################################
	            // Voisin commun
				//#####################################
				/*
				for(j = 2; j<12; j++){
					nb_voisin = voisin(1, j, graphe, nb_node + 1);

					if(graphe[1][j] == 1 && nb_voisin > coefficient){
						tab_coeff[j-2][0] = tab_coeff[j-2][0] + 1;
					}
					if(graphe[1][j] == 1 && nb_voisin <= coefficient){
						tab_coeff[j-2][1] = tab_coeff[j-2][1] + 1;
					}
					if(graphe[1][j] == 0 && nb_voisin > coefficient){
						tab_coeff[j-2][2] = tab_coeff[j-2][2] + 1;
					}
					if(graphe[1][j] == 0 && nb_voisin <= coefficient){
						tab_coeff[j-2][3] = tab_coeff[j-2][3] + 1;
					}
				}
				*/
				
				
				
				
				//#####################################
	            // Degré de chaque sommet
				//#####################################

				/*
				for(j = 1; j<50; j++){
					d = degre(j, graphe, nb_node+1);
					
					//System.out.println("le degre à l'instant " + k + " est " + d + "pour le noeud numéro : " + j);
					if(j == 28 && mm<8000){
						test[mm] = d;
						mm++;
					}
					if(tab_degre[j-1][0] >= 3 && tab_degre[j-1][0] <= d){
						tab_degre[j][1] = tab_degre[j][1] + 1;
						tab_degre[j][0] = d;
					}
					
					if(tab_degre[j-1][0] >= 3 && tab_degre[j-1][0] >= d){
						tab_degre[j][2] = tab_degre[j][2] + 1;
						tab_degre[j][0] = d;
					}
				}*/
				
			}
			
			/*
			for(j=0; j<45; j++){
				System.out.println("augment " + tab_degre[j][1] + " descent " + tab_degre[j][2]);
			}*/
			
			/*
			 
			for(j=0; j<100; j++){
				System.out.println("interconnexion 1 et 2 : " + interconnexion[j]);
			}*/
			
			
				/*
				try{
					File ff=new File("resultat28.txt"); // définir l'arborescence
					ff.createNewFile();
					FileWriter ffw=new FileWriter(ff);
					for(k = 0; k<9999; k++){
						ffw.write ("" + test[k]);
						ffw.write ("\r\n");
					}
					ffw.close(); // fermer le fichier à la fin des traitements
					} 
				catch (Exception e) {}
				 */

			
			try{
				File ff=new File("Interconnexion_result3.txt"); // définir l'arborescence
				ff.createNewFile();
				FileWriter ffw=new FileWriter(ff);
				for(k = 0; k<9999; k++){
					ffw.write ("" + interconnexion[k]);
					ffw.write ("\r\n");
				}
				ffw.close(); // fermer le fichier à la fin des traitements
				} 
			catch (Exception e) {}

			
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }


    public static void main(String args[]){
        System.out.println("\nRollerNet : ");
        //Affichage("RollerNet_dataCS.txt");
        System.out.println("\nInfocom06 : ");
        //Affichage("Infocom_dataCS.txt");
        //Degree.Creation_graphe(50,  2000);
        Interconnexion.Creation_graphe(50,  2000);
        //Affichage("InterconnexionData.txt");
        //Tab2.Creation();
    }

}






