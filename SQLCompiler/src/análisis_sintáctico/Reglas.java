/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package análisis_sintáctico;

import EDD.Cola;
import analizador_lexico.Token;
import analizador_lexico.Tokenizer;

/**
 *
 * @author utente
 */
public class Reglas {

    private Object[] parametros = new Object[6];

    //Parsing de la sentencia con el token Select ya consumido
    // Si es una regla secuencial, sumar al contador antes de buscar otro token
    public Object[] select() {
        if (!lista_columnas()) {
            return null;
        }

        if (!Sintáctico.tipo_actual().equals("FROM")) {
            return null;
        }
        Sintáctico.indexColaTokens++;
        if (!lista_tablas()) {
            return null;
        }
        if (!where()) {
            //si no halla where que retorne los parametros que se han reunido hasta ahora
            return parametros;
        }

        return parametros;
    }

    public boolean lista_columnas() {
        return columna();
    }

    public boolean lista_tablas() {
        return nombre_tabla();
    }

    // <expresión_aritmética>
    // TODO: Array para agregar más columnas... tal vez solo necesitamos recorrer Tokens en lugar de usar un Array
    //Nueva implementacion, ahora columnas puede recoger un arreglo de columnas
    public boolean columna() {
        //Array donde guardaremos las columnas que encontremos en la cola de tokens
        Cola<Token> colaColumnas = new Cola<>();
        boolean esperaColumna = true; //despues del select esperamos una columna
        String tipoToken;
        while (true) {
            //col1, col2... coln FROM
            //traemos el tipo del token actual
            tipoToken = Sintáctico.tipo_actual();

            if (tipoToken.equals(Tokenizer.ID) || tipoToken.equals(Tokenizer.ASTERISK) || tipoToken.equals(Tokenizer.NUMBER) || tipoToken.equals(Tokenizer.STRING)) {
                //Si es un token y no se esta esperando una columna
                if (!esperaColumna) {
                    System.out.println("Error gramatical, se esperaba :" + Tokenizer.COMMA);
                    return false;
                }
                //Si se se esperaba una columna agregar el token ya sea ID, * , number o string
                colaColumnas.agregar(Sintáctico.token_actual());
                //Avanzamos al siguiente token
                Sintáctico.indexColaTokens++;
                //Actualizamos el esperaColumna
                esperaColumna = false;

            } else if (tipoToken.equals(Tokenizer.COMMA)) {
                //Si el tipotoken es comma pero se esperaba una columna
                if (esperaColumna) {
                    System.out.println("Error de sintaxis: Se esperaba columna" + Tokenizer.ID);
                    return false;
                }
                //Si no es esperaba una columna estamos aptos para recibir al coma
                //Avanzamos al siguiente token
                Sintáctico.indexColaTokens++;
                //Actualizamos el esperaColumna
                esperaColumna = true;
            } else if (tipoToken.equals(Tokenizer.FROM)) {

                if (esperaColumna) {
                    System.out.println("Error de sintaxis: Se esperaba" + Tokenizer.ID);
                    return false;
                }
                //Hemos llegado al final del reconocimiento lista columnas
                parametros[0] = colaColumnas;

                //Analisis gramatical de lista columnas completado (faltaria reconocer expresiones aritmeticas)
                //actualmente solo se reconoce columnas ID y se guardan en una colaDeColumnas
                return true;
            } else {
                System.out.println("Error de sintaxis: Se esperaba + " + Tokenizer.FROM);
                return false;
            }
        }

    }

    public boolean nombre_tabla() {
        if (!Sintáctico.tipo_actual().equals("ID")) {
            return false;
        }
        parametros[1] = Sintáctico.valor_actual();
        //Avanzamos al siguiente token
        Sintáctico.indexColaTokens++;
        return true;
    }

    public boolean where() {
        //Implementacion del analisis sintactico de where
        if (!Sintáctico.tipo_actual().equals(Tokenizer.WHERE)) {
            //El token actual no es WHERE
            //Retornar falso
            return false;
        }
        //Where reconocido, avanzamos al siguiente token
        Sintáctico.indexColaTokens++;
        //definimos una cola de tokensLogicos
        Cola<Token> colaTokensLogicos = new Cola<>();
        
        //1.Recogemos los tokens
        while(true){
            
            Token token = Sintáctico.token_actual();
            colaTokensLogicos.agregar(token);
            //Si estamos en el ultimo indice salir del while
            if(Sintáctico.indexColaTokens == Sintáctico.colaTokens.getSize() - 1){
                break;
            }
            //Avanzar al siguiente token
            Sintáctico.indexColaTokens++;         
        }
        
        //2. Verificar que los tokens esten gramaticalmente correctos
       //parsing de las sentencias de cola tokens
 
        return false;
    }
}
