import java.util.*;
import java.io.*;


public class Matrix{

	// cette fonction fait la normalisation des graphes, de ramener les instants initiaux au 0.
    public static void Normalisation(int[][] matrice, int taille, int min_duration){
        int i;
        for(i = 0; i<taille; i++){
            matrice[i][2] = matrice[i][2] - min_duration;
            matrice[i][3] = matrice[i][3] - min_duration;
        }
    }


	// Cette fonction recherche les informations importantes des grands graphes.
    public static void Affichage(String fichier){

        List<String> items = new ArrayList<String>();
        int counter = 0;
        int max_duration = 0;
        int min_duration = 0;
        int duration = 0;
        int nb_node = 0;
        int nb_arete = 0;
        int i;
        int j;

        try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String line;
            StringTokenizer splitter;
            int[][] data;
            int[][] dataCS;
	    int[][] dataIC;


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





	//##########################################
        // Examination et Noramalisation des donnees
	//##########################################

            nb_arete = counter;
            min_duration = data[0][2];
            for(i = 0; i<counter; i++){
                min_duration = Math.min(data[i][2], min_duration);
            }

            Normalisation(data, nb_arete, min_duration);

            for(i = 0; i<counter; i++){
                nb_node = Math.max(data[i][1], nb_node);
                max_duration = Math.max(data[i][3], max_duration);
                min_duration = Math.min(data[i][2], min_duration);
            }
            duration = max_duration - min_duration +1;

            System.out.println("Nombre de noeud : " + nb_node);
            System.out.println("Nombre de arete : " + nb_arete);
            System.out.println("Duration : " + duration);






	//##########################################
        // Regroupement des donnees sous une autre forme qui est plus facile à étudier.
	//##########################################
            dataCS = new int[nb_arete * 2][4];
 
            for(i = 0; i < nb_arete; i++){
                dataCS[i][0] = data[i][2];
                dataCS[i][1] = data[i][0];
                dataCS[i][2] = data[i][1];
                dataCS[i][3] = 0;
            }  

            for(i = nb_arete + 1; i<nb_arete * 2; i++){
                dataCS[i][0] = data[i-nb_arete][3] + 1;
                dataCS[i][1] = data[i-nb_arete][0];
                dataCS[i][2] = data[i-nb_arete][1];
                dataCS[i][3] = 1;
            }

            int min;
			int min_indice = 0;
            int[] min_tab;
            min_tab = new int[4];
		
            for(i = 0; i < nb_arete * 2; i++){
		    min = 1000000;
                for(j = i+1; j < nb_arete * 2; j++){
                    if(dataCS[j][0] < min){
                        min = dataCS[j][0];
				min_indice = j;
                    }
                }
                min_tab[0] = dataCS[i][0];
                min_tab[1] = dataCS[i][1];
                min_tab[2] = dataCS[i][2];
                min_tab[3] = dataCS[i][3];

                dataCS[i][0] = dataCS[min_indice][0];
                dataCS[i][1] = dataCS[min_indice][1];
                dataCS[i][2] = dataCS[min_indice][2];
                dataCS[i][3] = dataCS[min_indice][3];

                dataCS[min_indice][0] = min_tab[0];
                dataCS[min_indice][1] = min_tab[1];
                dataCS[min_indice][2] = min_tab[2];
                dataCS[min_indice][3] = min_tab[3];
            }

            /*for(i = 0; i<nb_arete * 2 ; i++){
                System.out.println(dataCS[i][0] + " " + dataCS[i][1] + " " + dataCS[i][2] + " " + dataCS[i][3]);
            }*/

	int connect1, connect2, nb_connect, duration_connect, k;



			//##########################################
			// Inter-contact.
			//##########################################
			dataIC = new int[nb_node][nb_node];
			for(i = 0; i<10; i++){
				for(j = 0; j<nb_node; j++){
					nb_connect = 0;
					duration_connect = 0;
					connect2 = 0;
					for(k = 0; k<nb_arete*2; k++){
						if(i==dataCS[k][1] && j==dataCS[k][2]){
							connect1 = connect2;
							connect2 = dataCS[k][0];
							if(dataCS[k][3] == 1){
								nb_connect++;
								duration_connect = duration_connect + connect2 - connect1;
							}
						}
					}
					if(nb_connect == 0){
					dataIC[i][j] = 0;
					}
					else{
						duration_connect = duration_connect/nb_connect;
						dataIC[i][j] = duration_connect;
					}
				}
			}
			System.out.println("Inter_connection moyen 1 & 2 : " + dataIC[1][2]);
			System.out.println("Inter_connection moyen 1 & 3 : " + dataIC[1][3]);
			System.out.println("Inter_connection moyen 1 & 4 : " + dataIC[1][4]);
			System.out.println("Inter_connection moyen 1 & 5 : " + dataIC[1][5]);
			System.out.println("Inter_connection moyen 1 & 6 : " + dataIC[1][6]);
			System.out.println("Inter_connection moyen 1 & 7 : " + dataIC[1][7]);
			System.out.println("Inter_connection moyen 1 & 8 : " + dataIC[1][8]);
			System.out.println("Inter_connection moyen 1 & 9 : " + dataIC[1][9]);
			System.out.println("Inter_connection moyen 1 & 10 : " + dataIC[1][10]);


			//##########################################
			//Degre moyen.
			//##########################################
			
			int[] degree;
			degree = new int[duration+2];
			int instant = 0;
	
			for(i = 0; i<duration+2; i++){
				degree[i] = 0;
			}

			for(i = 0; i<49; i++){
				for(j = 0; j<nb_arete*2; j++){
					if(dataCS[j][0] == i && dataCS[j][3] == 0){
						degree[i] = degree[i] + 1;
					}
					if(dataCS[j][0] == i && dataCS[j][3] == 1){
						degree[i] = degree[i] - 1;
					}
				}
				degree[i+1] = degree[i];
			}

			File f = new File ("teste.txt");
			FileWriter fw = new FileWriter (f);

			for(i = 0; i<49; i++){
        		fw.write (String.valueOf(i));
				fw.write (" ");
				fw.write (String.valueOf(degree[i]));
       			fw.write ("\n"); 
			}   
			fw.close();
			


			//##########################################
			//Creation Deletion links
			//##########################################
			/*
			int[][] fraction;
			fraction = new int[duration+2][2];
	
			for(i = 0; i<duration+2; i++){
				fraction[i][1] = -1;
				fraction[i][0] = -1;
			}

			for(i = 0; i<5000; i++){
				for(j = 0; j<nb_arete*2; j++){
					if(dataCS[j][0] == i && dataCS[j][3] == 0){
						fraction[i][0] = fraction[i][0] + 1;
					}
					if(dataCS[j][0] == i && dataCS[j][3] == 1){
						fraction[i][1] = fraction[i][1] + 1;
					}
				}
			}


			File g = new File ("fraction_Infocom.txt");
			FileWriter gw = new FileWriter (g);

			for(i = 0; i<5000; i++){
        		gw.write (String.valueOf(i));
				gw.write (" : Creation : ");
				gw.write (String.valueOf((double) fraction[i][0]/(fraction[i][0] + fraction[i][1])));
				gw.write (". Deletion : ");
				gw.write (String.valueOf((double) fraction[i][1]/(fraction[i][0] + fraction[i][1])));
       			gw.write ("\n"); 
			}   
			gw.close();

			*/

            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }




		//##########################################
		// Ramdom Graph Markov
		//##########################################

	public static void Markov(int nb_noeud, double creation, double deletion, int nb_fois){

		int i, j, k;
		double random;
		int[][] data;
		data = new int[nb_noeud][nb_noeud];

		for(i = 0; i<nb_noeud; i++){
			for(j = 0; j<nb_noeud; j++){
				data[i][j] = 0;
			}
		}

		try{
			File f = new File ("MarkovData.txt");
			FileWriter gw = new FileWriter (f);
 
			for(i = 1; i<nb_noeud; i++){
				for(j = 1; j<nb_noeud; j++){
					for(k = 1; k<nb_fois; k++){
						if(i != j){
							random = Math.random();
							if(data[i][j]==0 && creation>random){
								data[i][j]=1;
								gw.write (String.valueOf(i));
								gw.write (" ");
								gw.write (String.valueOf(j));
								gw.write (" ");
								gw.write (String.valueOf(k));
								gw.write (" ");
							}
							if(data[i][j]==1 && deletion>random){
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




    public static void main(String args[]){
        System.out.println("\nRollerNet : ");
        Affichage("RollerNet");
        System.out.println("\nInfocom06 : ");
        Affichage("Infocom06");
		//System.out.println("\n30 noeuds & 0.7 Create & 0.4 Deletion & 1000 Time : ");
		//Affichage("30_07_04_1000.txt");
		//Markov(30, 0.7, 0.4, 1000);
    }

}






