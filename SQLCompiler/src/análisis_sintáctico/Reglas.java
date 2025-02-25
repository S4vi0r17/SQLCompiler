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

        //Si el siguiente token es diferente de FROM retornar null
        System.out.println(Sintáctico.tipo_actual());
        System.out.println(Sintáctico.token_actual());
        
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
        
        int esOperacion;
        

        while (true) {
            //col1, col2... coln FROM
            //traemos el tipo del token actual
            tipoToken = Sintáctico.tipo_actual();

            
            if (tipoToken.equals(Tokenizer.ID) || tipoToken.equals(Tokenizer.ASTERISK) || tipoToken.equals(Tokenizer.NUMBER) || tipoToken.equals(Tokenizer.STRING) || tipoToken.equals(Tokenizer.OPEN_P)) {

                //Si es un token y no se esta esperando una columna
                if (!esperaColumna) {
                    System.out.println("Error gramatical, se esperaba :" + Tokenizer.COMMA);
                    return false;
                }
               
                esOperacion = operacion_aritmetica();              
                if (esOperacion != 0) {
                    
                    // DEBUG
                    System.out.println(esOperacion);
                    
                    for (int i = Sintáctico.indexColaTokens ; i < esOperacion ; i ++) {
                        Token to = (Token) Sintáctico.colaTokens.buscar_por_orden(i);
                        
                        // DEBUG
                        System.out.println(to.getTokenValor());
                        
                        to.setTipo("ARIT");
                        colaColumnas.agregar(to);
                    }        
                    Sintáctico.indexColaTokens = esOperacion;
                    
                    // DEBUG
                    System.out.println("Indice después de OA: " + Sintáctico.indexColaTokens);
                    
                    esperaColumna = false;
                    continue;
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
                // DEBUG
                colaColumnas.imprimirCola();
                
               //Hemos llegado al final del reconocimiento lista columnas
                parametros[0] = colaColumnas;

                //Analisis gramatical de lista columnas completado (faltaria reconocer expresiones aritmeticas)
                //actualmente solo se reconoce columnas ID y se guardan en una colaDeColumnas
                return true;

            }
            else{
                System.out.println("Error de sintaxis: Se esperaba " + Tokenizer.FROM);

                return false;
            }           
            
        }

    }

    public int operacion_aritmetica() {
        int indiceCopia = Sintáctico.indexColaTokens;
        String tipoActual;
        
        boolean tieneOperador = false;
        boolean esperaOperando = true;
        int esperaCierre = 0;
        
        while (true) {
            
            Token to = (Token) Sintáctico.colaTokens.buscar_por_orden(indiceCopia);
            tipoActual = to.getTipo();
            
            if (tipoActual.equals(Tokenizer.ID) || tipoActual.equals(Tokenizer.STRING) || tipoActual.equals(Tokenizer.NUMBER)) {
                if (!esperaOperando) {
                    System.out.println("Error de sintaxis: Se esperaba OPERADOR");
                    return 0;
                }
                indiceCopia++;
                esperaOperando = false;
                
            } else if (tipoActual.equals(Tokenizer.PLUS) || tipoActual.equals(Tokenizer.MINUS) || tipoActual.equals(Tokenizer.ASTERISK) || tipoActual.equals(Tokenizer.DIV)) {
                if (esperaOperando) {
                    System.out.println("Error de sintaxis: Se esperaba OPERANDO");
                    return 0;
                }
                indiceCopia++;
                esperaOperando = true;
                tieneOperador = true;
                
            } else if (tipoActual.equals(Tokenizer.OPEN_P)) {
                indiceCopia++;
                esperaOperando = true;
                esperaCierre++;
                
            } else if (tipoActual.equals(Tokenizer.CLOSE_P)) {
                if (esperaCierre == 0) {
                    System.out.println("Error de sintaxis: Se cerró paréntesis innecesariamente");
                    return 0;
                }
                indiceCopia++;
                esperaOperando = false;
                esperaCierre--;
               
            } else if ((tipoActual.equals(Tokenizer.COMMA) || tipoActual.equals(Tokenizer.FROM)) && tieneOperador == true) {
                if (esperaCierre != 0) {
                    System.out.println("Error de sintaxis: Hay un paréntesis no cerrado");
                    return 0;
                }
                return indiceCopia;
            }
            else {
                System.out.println("No es expresión");
                return 0;
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
        Sintáctico.colaTokens.imprimirCola();
        if(Sintáctico.indexColaTokens >= Sintáctico.colaTokens.getSize()){
            return false;
        }
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
        while (true) {

            Token token = Sintáctico.token_actual();
            colaTokensLogicos.agregar(token);
            //Si estamos en el ultimo indice y agregamos el ultimo token salir del while
            if (Sintáctico.indexColaTokens == Sintáctico.colaTokens.getSize() - 1) {
                break;
            }
            //Avanzar al siguiente token
            Sintáctico.indexColaTokens++;
        }

        //2. Verificar que los tokens esten gramaticalmente correctos
        //parsing de las sentencias de cola tokens
        int indexColaLogico = 0;
        boolean esperaOperando = true;
        boolean esUltimoToken = false;
        boolean inicioTermino = true;
        //iteramos toda la cola de tokens logicos
        while (indexColaLogico < colaTokensLogicos.getSize()) {
            Token token = colaTokensLogicos.buscar_por_orden(indexColaLogico);
            String tokenTipo = token.getTipo();
            
            //Estamos en el ultimo token
            if(indexColaLogico == colaTokensLogicos.getSize() - 1){
                esUltimoToken = true;
            }
            
            if (tokenTipo.equals(Tokenizer.NOT)) {
                
                //El where no termina en NOT
                if(esUltimoToken){
                    return false;
                }
                if (inicioTermino) {
                    //Consumimos el NOT, no estamos al inicio
                    inicioTermino = false;
                    esperaOperando = true;
                    //Avanzamos token
                    indexColaLogico++;
                } else {
                    return false;
                }

            } else if (tokenTipo.equals(Tokenizer.ID) || tokenTipo.equals(Tokenizer.NUMBER) || tokenTipo.equals(Tokenizer.STRING)) {
                
                if(esUltimoToken){
                    //solo hay un token y es un ID, numero o string
                    if(indexColaLogico == 0){
                        break;
                    }
                    else{
                        Token tokenAnterior = colaTokensLogicos.buscar_por_orden(indexColaLogico - 1);
                        if(isAComparisonOperator(tokenAnterior.getTipo())){
                            break;
                        }
                    }
                }
                
                if (esperaOperando) {
                    esperaOperando = false;
                    inicioTermino = false;
                    indexColaLogico++;
                } else {
                    System.out.println("Opeerando no esperado: " + token.getTokenValor());
                    return false;
                }
            } //Si es un operador comparacion
            else if (isAComparisonOperator(tokenTipo)) {
                
                if(esUltimoToken){
                    System.out.println("Se esperaba una comparacion");
                    return false;
                }
                
                if (!esperaOperando) {
                    esperaOperando = true;
                    indexColaLogico++;
                } else {
                    return false;
                }
            } //Si es un operador logico
            else if (tokenTipo.equals(Tokenizer.AND) || tokenTipo.equals(Tokenizer.OR)) {
                
                if(esUltimoToken){
                    System.out.println("Se esperaba otra expresion logica");
                    return false;
                }
                
                if (!inicioTermino && !esperaOperando) {
                    inicioTermino = true;
                    esperaOperando = true;
                    indexColaLogico++;
                } else {
                    return false;
                }
            }
            else{
                //un token incongruente para el where
                System.out.println("Token no valido en esta expresion");
                return false;
            }

        }
        
        parametros[2] = colaTokensLogicos;
        
        return true;   
    }
    
    private boolean isAComparisonOperator(String tokenTipo){
        return tokenTipo.equals(Tokenizer.EQUAL) || tokenTipo.equals(Tokenizer.GREATER) || tokenTipo.equals(Tokenizer.LESS) || tokenTipo.equals(Tokenizer.LESS_EQUAL) || tokenTipo.equals(Tokenizer.GREATER_EQUAL) || tokenTipo.equals(Tokenizer.NOT_EQUAL[0]) || tokenTipo.equals(Tokenizer.NOT_EQUAL[1]);
    }
    
}
