package com.grupoasinfo.jsonapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/general")
public class MainController {

    @GetMapping("/")
    public String main(){
        return "Welcome to the Main Page";
    }

    @GetMapping("/returnJSON")
    public String getJson(){
        List<String[]> newList = new ArrayList<>();
        newList = getMatrizUrls(returnList());

        //NODO PADRE
        JSONObject root = new JSONObject();
        root.put("label", newList.get(0)[1]);
        JSONArray items = new JSONArray();
        root.put("items", items);

        getNombresNodos(newList, 2, items); //Obtiene segundo Nodo

        poblarTercerNodo(newList, 2, root); //Obtiene tercer nodo

        poblarCuartoNodo(newList, 3 , root); //Obtiene 4to nodo

        poblarLastNode(newList, 5, 4, root);

        return root.toString();
    }

    private void poblarLastNode(List<String[]> newList, int fila, int nivelInicial, JSONObject root){
        List<String []> nodos = new ArrayList<>();

        nodos = getLastNodePadreHijo(newList, fila, nivelInicial);

        JSONArray node = root.getJSONArray("items");

        for (int i = 0; i < node.length(); i++){
            JSONObject parent = node.getJSONObject(i);

            JSONArray array = new JSONArray();
            array = parent.getJSONArray("items");

            for (int j = 0; j < array.length(); j++){
                JSONObject parent2 = array.getJSONObject(j);

                JSONArray array1 = new JSONArray();
                array1 = parent2.getJSONArray("items");

                JSONObject parent3 = array1.getJSONObject(0);

                JSONArray newArr = new JSONArray();

                if(parent3.getString("label").equals("empleados")){
                    parent3.put("items", newArr);
                }

                for (int x = 0; x < nodos.size(); x++){
                    JSONObject son = new JSONObject();

                    if (nodos.get(x)[0].equals(parent3.getString("label"))){
                        newArr.put(son);
                        if(nodos.get(x)[1].contains(".xhtml")){
                            son.put("routerLink", returnList().get(newList.size()-1));
                        }
                        son.put("label", nodos.get(x)[1]);
                    }
                }
            }

        }
    }

    private void poblarCuartoNodo(List<String[]> newList, int nivelInicial, JSONObject root){
        List<String []> nodos = new ArrayList<>();

        nodos = getPadreHijos(newList, nivelInicial);

        for (int i = 0; i < nodos.size(); i ++){
            System.out.println(nodos.get(i)[0] + "\t" + nodos.get(i)[1]);
        }

        JSONArray node = root.getJSONArray("items");

        for (int i = 0; i < node.length(); i++){
            JSONObject parent = node.getJSONObject(i);

            JSONArray array = new JSONArray();
            array = parent.getJSONArray("items");

            for (int j = 0; j < array.length(); j++){
                JSONObject parent2 = array.getJSONObject(j);

                System.out.println("PARENT2" + parent2);

                JSONArray newArr = new JSONArray();
                parent2.put("items", newArr);


                for (int x = 0; x < nodos.size(); x++){
                    JSONObject son = new JSONObject();

                    if (nodos.get(x)[0].equals(parent2.getString("label"))){
                        newArr.put(son);
                        if(nodos.get(x)[1].contains(".xhtml")){
                            son.put("routerLink", returnList().get(x));
                        }
                        son.put("label", nodos.get(x)[1]);
                    }
                }
            }

        }
    }

    private void poblarTercerNodo(List<String[]> newList, int nivelInicial, JSONObject root){
        List<String []> nodos = new ArrayList<>();

        nodos = getPadreHijos(newList, nivelInicial);

        JSONArray node = root.getJSONArray("items");

        for (int i = 0; i < node.length(); i++){
            JSONObject parent = node.getJSONObject(i);

            JSONArray array = new JSONArray();
            array = parent.getJSONArray("items");

            for (int j = 0; j < nodos.size(); j++){

                JSONObject son = new JSONObject();

                if (nodos.get(j)[0].equals(parent.getString("label"))){
                    array.put(son);
                    son.put("label", nodos.get(j)[1]);
                }
            }

        }
    }

    private List<String[]> getLastNodePadreHijo(List<String[]> lista, int fila, int nivelInicial){
        List<String[]> list = new ArrayList<>();

        String [] objeto = new String[2];
        objeto[0] = lista.get(fila - 1)[nivelInicial];
        objeto[1] = lista.get(fila - 1)[nivelInicial + 1];

        list.add(objeto);

        return list;
    }

    private void getNombresNodos(List<String[]> list, int nivel, JSONArray items){
        String objetoActual;

        for (int i = 0; i < list.size(); i ++){
            objetoActual = list.get(i)[nivel];
            if (i < list.size() - 1){
                if (!objetoActual.equals(list.get(i+1)[nivel])){
                    objetoActual = list.get(i)[nivel];
                    JSONObject son = new JSONObject();
                    items.put(son);
                    son.put("label", objetoActual);

                    JSONArray itemsInternos = new JSONArray();
                    son.put("items", itemsInternos);
                }
            }else{
                if (!objetoActual.equals(list.get(i-1)[nivel])){
                    objetoActual = list.get(i)[nivel];
                    JSONObject son = new JSONObject();
                    items.put(son);
                    son.put("label", objetoActual);

                    JSONArray itemsInternos = new JSONArray();
                    son.put("items", itemsInternos);
                }
            }
        }
    }

    private List<String[]> getPadreHijos(List<String[]> lista, int nivelInicial){
        List<String[]> list = new ArrayList<>();

        for (int i = 0 ; i < lista.size(); i++){
            String[] objeto = new String[2];
            try{
                objeto[0] = lista.get(i)[nivelInicial];
                objeto[1] = lista.get(i)[nivelInicial + 1];
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Excep");
            }
            list.add(objeto);

//            String[] objeto = new String[2];
//            objeto[0] = lista.get(i)[nivelInicial];
//            objeto[1] = lista.get(i)[nivelInicial + 1];


        }

        for (int i = 0; i < list.size(); i++){
            for (int j = i + 1 ; j < list.size(); j++){
                if (list.get(i)[0].equals(list.get(j)[0]) && list.get(i)[1].equals(list.get(j)[1])){
                    list.remove(j);
                    j--;
                }
            }
        }

        return list;
    }

    private List<String[]> getMatrizUrls(List<String> lista){
        List<String[]> separatedList = new ArrayList<>();
        for (String chain : lista){
            separatedList.add(chain.split("/"));
        }

        return separatedList;
    }

    private List<String> returnList(){
        List<String> lista = new ArrayList<>();
        lista.add("/paginas/compras/procesos/facturaProveedor.xhtml");
        lista.add("/paginas/compras/procesos/recepcionProveedor.xhtml");
        lista.add("/paginas/compras/Reportes/reporteA.xhtml");
        lista.add("/paginas/compras/Reportes/reporteB.xhtml");
        lista.add("/paginas/nomina/configuracion/empleados/empleado.xhtml");
        lista.add("/paginas/compras/Reportes/reporteC.xhtml");
        lista.add("/paginas/nomina/configuracion/empleados/asistencia/registro.xhtml");
        lista.add("/paginas/nomina/configuracion/empleados/asistencia/salida.xhtml");
        lista.add("/paginas/compras//reporteC.xhtml");

        return lista;
    }
}
