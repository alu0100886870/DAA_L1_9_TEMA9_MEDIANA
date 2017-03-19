package trabajo;import java.util.Arrays;

import com.sun.org.apache.bcel.internal.generic.Select;

/**
 * @author root
 *
 */
public class MedianaDeUnArrayDesordenado {

	static boolean debug = false;

	
	static int select(int[] vector, int izquierda, int derecha, int n) {
		if(izquierda == derecha)
			return vector[izquierda];
		
		// ordena el K'esimo elemento en el array
		int k = particion(vector, izquierda, derecha);
		
		int length = k - izquierda + 1;
		System.out.println("es length:" + length + "   igual al indice del pivote:" + k);
		if(length == k) {
			System.out.println("Es igual");
			return vector[k];
		}
		else if (length < k) {
			System.out.println("Es menor");
			return select(vector, n, izquierda, k - 1); 
		}
		else {
			System.out.println("Es mayor");
			return select(vector, n - length, k + 1, derecha); 
		}
	}
	
	
    /**
     * Este método recibe un vector dos índices dentro de este, 
     * izquierdo y derecho y el índice del elemento pivote. Coloca
     * el pivote al final del array y va comparando V[i]  
     * desde i = límite izquierdo hasta el derecho con el pivote. 
     * Si es menor intercambia el elemento en la posición almacenada 
     * (inicialmente el límite izquierdo) con el elemento V[i]. 
     * Seguidamente incrementa el índice de la posición almacenada. 
     * Cuando llega a el límite derecho intercambia el pivote con 
     * la posición almacenada. Esto deja todos los valores menores 
     * a este a la izquierda. Por último devuelve el índice en el 
     * que se encuentra el pivote. 
     * @param vector
     * @param izquierda
     * @param derecha
     * @param indicePivote
     * @return
     */
    static int particion(int[] vector, int izquierda, int derecha) {
    	int pivote = obtenerPivote(vector, izquierda, derecha);
		// Encuentra la posición ordenada de pivotVale y devuelve su índice
		while (izquierda < derecha) {
			while (vector[izquierda] < pivote) izquierda++;
			while (vector[derecha] > pivote) derecha--;

			if (vector[izquierda] == vector[derecha]) {
				izquierda++;
			} else if (izquierda < derecha) {
				swap(vector, izquierda, derecha);
			}
		}
		return derecha;
    }
    
	
    
    /**
     * Este método Encuentra el valor de pivote, tal que siempre 
     * está "más cerca" de la mediana real. Lo hace partiendo el 
     * array en arrays de 5 elementos. Ordenandolos y guardando
     * un array con la mediana de cada uno de los sub arrays. Despúes
     * llama recursivamente para encontrar la mediana dentro de ese array. 
     * 
     * @param vector
     * @param izquierda
     * @param derecha
     * @return
     */
    private static int obtenerPivote(int vector[], int izquierda, int derecha) {
    	// Si el numero de elementos en el array es pequeño, retorna la mediana actual
		if (derecha - izquierda + 1 <= 9) {
			Arrays.sort(vector);
			return vector[vector.length / 2];
		}

		// De lo contrario dividir el array en subarray de 5 elementos cada uno, y
		// encontrar recursivamente la mediana

		// Array para mantener subArray "5 elementos", el último subArray puede tener menos de
		// 5 elementos
		int temp[] = null;


		// Array para contener las medianas de todos los 'SubArrays de 5 elementos'
		int medianas[] = new int[(int) Math.ceil((double) (derecha - izquierda + 1) / 5)];
		int indiceMediana = 0;

		while (izquierda <= derecha) {
			// obtener el tamaño del siguiente elemento, puede ser menor de 5
			temp = new int[Math.min(5, derecha - izquierda + 1)];

			// copiar los siguientes 5 (o menos) elementos, en el subarray
			for (int j = 0; j < temp.length && izquierda <= derecha; j++) {
				temp[j] = vector[izquierda];
				izquierda++;
			}

			// Ordenamos el subarray
			Arrays.sort(temp);

			//Encontrar mediana y almacenarla en el array de medianas
			medianas[indiceMediana] = temp[temp.length / 2];
			indiceMediana++;
		}

		// LLamada recursiva para encontrar la mediana de medianas
		return obtenerPivote(medianas, 0, medianas.length - 1);
	}
    
    /**
     * Intercambia dos elementos en un array.
     * @param vector
     * @param a
     * @param b
     */
    static void swap(int[] vector, int a, int b) {
    	int aux = vector[a];
    	vector[a] = vector[b];
    	vector[b] = aux;
    }
    
    /**
     * Imprime un array pasado como parámetro.
     * @param vector
     * @return
     */
    static String print(int[] vector) {
    	String str = "[";
    	for (int i = 0; i < vector.length; i++) {
			str += vector[i];
    		str += i != (vector.length - 1) ? "," : "";
		}
    	str += "]";
    	return str;
    }
    
    public static void main(String[] args) {
    	int[] vector = new int[5];
    	for (int i = 0; i < vector.length; i++) {
			vector[i] = (int) (((Math.random() * 100)  + 1));
		}
    	System.out.println("Vector: " + print(vector));
//    	System.out.println(partition(vector,0,vector.length - 1, (vector.length / 2) - 1));
    	System.out.println("mediana:" + select(vector,0,vector.length - 1, (vector.length / 2) + 1));
    	System.out.println("Vector: " + print(vector));
    	java.util.Arrays.sort(vector);
    	System.out.println("Vector ordenado: " + print(vector));
    	System.out.println("mediana ordenado:" + vector[vector.length / 2]);

	}
}
