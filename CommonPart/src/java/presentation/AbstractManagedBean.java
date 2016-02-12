/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import advanced.Statics;
import entities.AbstractEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.context.RequestContext;

/**
 *
 * @author k
 * @param <O>
 */
public abstract class AbstractManagedBean<O extends AbstractEntity> {
    O object;
    String rootdir;
    String paramchain;
    
    public String openList(String params){
        return "/"+rootdir+"/list"
                +"?faces-redirect=true"
                +"&param="+params;
    }
    
    public String openFill(String id){
        return "/"+rootdir+"/fill"
                +"?faces-redirect=true"
                +"&param="+nextParams("/"+rootdir+"/list"+Statics.delimiter_3+id);//предыдущая цепочка + страница на возврат + ИД сущности
    }
    
    public String nextParams(String link){ //нужен для открытия новой формы: используется, чтобы сохранить в цепи предыдущие параметры открытия
        return increaseChain(paramchain, link);
    }
  
    public String getBack(){
        return getBackPage()
                +"?faces-redirect=true"
                +"&param="+cropChain(paramchain);
    }
    
    String getBackPage(){
        return getLink(paramchain).split(Statics.delimiter_3)[0];
    }
    
    public abstract void defineObject();
    
    public abstract O getObject(Number id);
    
    public abstract Collection<O> getList_objects_all();
    
    public Boolean isNewEntity(){   //функция нужна отдельно для определения того, какие кнопки отображать
                                                        //defineObject срабатывает после отрисовки кнопок, поэтому ссылаться на ИД объекта неьлзя, на момент отрисовки он ещё не определён
        return Long.parseLong(getParam(1))==0;
    }
    
    
    String getLink (String chain){
        return getElemSplit_last(chain, Statics.delimiter_1);
    }
    
    String getElemSplit_last(String string, String splitter){
        if (string==null || string.equals("")) return "";
        String[] array = string.split(splitter);
        return array[array.length-1];
    }
    
    String getElemSplit_cropLast(String string, String splitter){
        if (string==null || string.equals("")) return "";
        String[] array = string.split(splitter);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<array.length-1; i++){
            sb.append(array[i]);
            sb.append(splitter);
        }
        return Statics.cropLastDelimiter(sb.toString(), splitter);
    }
    
    String increaseChain (String chain, String link){
        return chain+Statics.delimiter_1+link;
    }
    
    String cropChain (String chain){
        return getElemSplit_cropLast(chain, Statics.delimiter_1);
    }
    
    public String getParam (int n){
        return getLink(paramchain).split(Statics.delimiter_3)[n];
    }
    
    public void select_start(Set<Number> ids) {
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        //options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 500);
        options.put("contentWidth", 1200);
        options.put("includeViewParams", true);
        
        Map<String, List<String>> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Number x : ids){
            sb.append(x); sb.append(Statics.delimiter_3);}
        values.add(sb.toString());
        params.put("param", values);
        
        RequestContext.getCurrentInstance().openDialog("/"+rootdir+"/add.xhtml", options, params);
    }
    
    public abstract Collection<O> getList_objects_exclude();
    
    List<O> getList_objects_exclude(List<O> list, char c){
        if (paramchain==null || paramchain.equals("")) return list;
        Set<Number> objsToExclude = new HashSet<>();
        for (String x : paramchain.split(Statics.delimiter_3))
            switch (c){
                case 'i': objsToExclude.add(Integer.parseInt(x)); break;
                case 'l': objsToExclude.add(Long.parseLong(x)); break;
                case 's': objsToExclude.add(Short.parseShort(x)); break;
            }
        
        for (Iterator<O> it = list.iterator(); it.hasNext(); ){
            O x = it.next();
            if (objsToExclude.contains(x.getId())) it.remove();
        }
        return list;
    }
    
    public <O> void select_do(O item) {
        RequestContext.getCurrentInstance().closeDialog(item);
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }
    
    public String getParamchain() {
        return paramchain;
    }

    public void setParamchain(String paramchain) {
        this.paramchain = paramchain;
    }
}
