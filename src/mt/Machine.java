/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;
import org.w3c.dom.Document;

public class Machine {
    Automata machine;

    public Machine(Document document) {
        machine = new Automata(document);
    }

    boolean comprobarCadena(StringBuilder cinta,int estadofinal) {
        Estado qi = machine.estados.get(0);
        int cabezal = 0;
        do{
            int i;
            for(i=0;i<qi.link.size();i++){
                if(qi.link.get(i).si == cinta.charAt(cabezal)){
                    cinta.setCharAt(cabezal,qi.link.get(i).sj);
                    switch (qi.link.get(i).movimiento){
                        case 'L':{
                            cabezal--;
                            if(cabezal == (-1)){
                                cinta.insert(0,"#");
                                cabezal++;
                            }
                            break;
                        }
                        case 'R':{
                            cabezal++;
                            if(cabezal == cinta.length()) cinta.insert(cabezal,"#");
                            break;
                        }
                        default:{/*Se mantiene*/}
                    }
                    qi = qi.link.get(i).qj;
                    i = -1;
                }
            }
            if(qi.q == estadofinal) return true;
            return false;
        }while(true);
    }
}

