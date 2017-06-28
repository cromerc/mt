package mt;

import org.w3c.dom.Document;

import java.util.ArrayList;

class Enlace{
    char si;
    char sj;
    char movimiento;
    Estado qj;

    public Enlace(char si,Estado qj,char sj, char move){
        setMovimiento(move);
        setSj(sj);
        setQj(qj);
        setSi(si);
    }

    private void setSi(char si){
        this.si = si;
    }

    private void setSj(char sj){
        this.sj = sj;
    }

    private void setMovimiento(char movimiento){
        this.movimiento = movimiento;
    }

    private void setQj(Estado qj){
        this.qj = qj;
    }

    @Override
    public String toString(){
        return ","+si+") = (q"+qj.q+","+sj+","+movimiento+")";
    }
}

class Estado{
    int q;
    ArrayList<Enlace> link;

    public Estado(int q) {
        this.q = q;
        link = new ArrayList<Enlace>();
    }

    public boolean createLink(char si,Estado qj,char sj, char move){
        if(link.isEmpty()) link.add(new Enlace(si,qj,sj,move));
        for(int i=0;i<link.size();i++){
            if(link.get(i).si == si) return false;
        }
        link.add(new Enlace(si,qj,sj,move));
        return true;
    }

    @Override
    public String toString(){
        String _return = "";
        for(int i=0; i<link.size();i++){
            _return += "(q"+q+link.get(i)+"\n";
        }
        return _return;
    }
}

class Automata {
    ArrayList<Estado> estados;
    public Automata(Document document){
        estados = new ArrayList<Estado>();
        Estado aux;
        for(int i=0;i<document.getElementsByTagName("transicion").getLength();i++){
            char move = document.getElementsByTagName("movimiento").item(i).getTextContent().charAt(0);
            if(move == 'E' || move == 'R' || move == 'L' || move == '*'){
                int qi = Integer.parseInt(document.getElementsByTagName("qi").item(i).getTextContent());
                int qj = Integer.parseInt(document.getElementsByTagName("qj").item(i).getTextContent());
                char si = document.getElementsByTagName("si").item(i).getTextContent().charAt(0);
                char sj = document.getElementsByTagName("sj").item(i).getTextContent().charAt(0);
                /*if(estados.isEmpty() && qi != qj){
                    estados.add(qi, new Estado(qi));
                    estados.add(qj, new Estado(qj));
                }else if(estados.isEmpty() && qi == qj) estados.add(qi, new Estado(qi));*/
                if(estados.size() <= qi) estados.add(qi, new Estado(qi));
                if(estados.size() <= qj) estados.add(qj, new Estado(qj));
                if(!estados.get(qi).createLink(si,estados.get(qj),sj,move)){
                    if(qi == qj) System.out.println("Recursivo");
                    else System.out.println("En"+qi+" a "+qj+"Transicion para "+si+" ya esta creada");
                }
            }else{
                System.out.println("Movimiento invalido de cinta");
                System.exit(1);
            }
        }
    }
}
