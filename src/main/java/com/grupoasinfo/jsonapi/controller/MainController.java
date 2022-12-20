package com.grupoasinfo.jsonapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/general")
public class MainController {

    @GetMapping("/")
    public String main(){
        return "Welcome to the Main Page";
    }

    @GetMapping("/returnJSON")
    public String getJson(){
        List<String> lista = new ArrayList<>();
        lista.add("/paginas/compras/procesos/facturaProveedor.xhtml");
        lista.add("/paginas/compras/procesos/recepcionProveedor.xhtml");
        lista.add("/paginas/compras/Reportes/reporteA.xhtml");
        lista.add("/paginas/compras/Reportes/reporteB.xhtml");
        lista.add("/paginas/nomina/configuracion/empleados/empleado.xhtml");

        List<String[]> newList = new ArrayList<>();
        newList = getMatrizUrls(lista);

        for (String[] obj : newList){
            for (String chain : obj){
                System.out.println(chain);
            }
        }

        return "HOLA";
    }

    private List<String[]> getMatrizUrls(List<String> lista){
        List<String[]> separatedList = new ArrayList<>();
        for (String chain : lista){
            separatedList.add(chain.split("/"));
        }

        return separatedList;
    }
}
