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
    
    
    /**
     * Открывает форму просмотра всех записей
     * 
     * @param params параметры
     * @return переход на страницу просмотра (всех) записей
     */
    public String openList(String params){
        return "/"+rootdir+"/list"
                +"?faces-redirect=true"
                +"&param="+params;
    }
    
    /**
     * Открывает форму редактирования одной записи - для типовых случаев
     * Необходимо добавить перегруженный метод, если параметров больше 1 ИДа
     * 
     * @param id ИД записи в БД
     * Если ИД = 0, это сигнал к тому, что запись новая
     * @return переход на страницу редактирования
     * 
     */
    public String openFill(String id){
        return "/"+rootdir+"/fill"
                +"?faces-redirect=true"
                +"&param="+nextParams("/"+rootdir+"/list"+Statics.delimiter_3+id);//предыдущая цепочка + страница на возврат + ИД сущности
    }
    
    /**
     * для открытия новой формы, чтобы сохранить в цепи предыдущие
     * параметры открытия
     * @param link новая цепь параметров
     * @return строку цепей параметров
     */
    public String nextParams(String link){
        return increaseChain(paramchain, link);
    }
  
    /**
     * для возврата на предыдущую страницу с предыдущими параметрами
     * @return адрес для возврата с параметрами
     */
    public String getBack(){
        return getBackPage()
                +"?faces-redirect=true"
                +"&param="+cropChain(paramchain);
    }
    
    /**
     * для получения из строки цепей параметров страницы,
     * на которую возвращаться
     * @return путь к предыдущей странице
     */
    String getBackPage(){
        return getLink(paramchain).split(Statics.delimiter_3)[0];
    }
    
    /**
     * для вытаскивания полной записи из БД при получении ИДа из строки
     */
    public abstract void defineObject();
    
    /**
     * для поиска сущности по ИДу
     * @param id ИД в БД
     * @return полную сущность
     */
    public abstract O getObject(Number id);
    
    /**
     * для выдачи всех записей этой сущности из БД
     * @return коллекцию сущностей
     */
    public abstract Collection<O> getList_objects_all();
    
    /**
     * для определения того, какие кнопки отображать на странице
     * в зависимости от того, новая сущность или изменяется имеющаяся
     * 
     * defineObject срабатывает после отрисовки кнопок,
     * поэтому ссылаться на ИД объекта неьлзя,
     * на момент отрисовки он ещё не определён
     * 
     * @return да, если новая, нет, если редактируется имеющаяся
     */
    public Boolean isNewEntity(){   //функция нужна отдельно для определения того, какие кнопки отображать
        return Long.parseLong(getParam(1))==0;
    }
    
    /**
     * для выдачи последнего звена из цепи наборов параметров
     * @param chain строка с разделителями
     * @return 
     */
    String getLink (String chain){
        return getElemSplit_last(chain, Statics.delimiter_1);
    }
    
    /**
     * Возвращает последний элемент из строки с разделителями
     * 
     * @param string строка, из которой вернуть
     * @param splitter разделитель
     * @return 
     */
    String getElemSplit_last(String string, String splitter){
        if (string==null || string.equals("")) return "";
        String[] array = string.split(splitter);
        return array[array.length-1];
    }
    
    
    /**
     * Возвращает строки с разделителями, предварительно отрезав от неё
     * последний элемент
     * 
     * @param string строка, от которой отрезать
     * @param splitter разделитель
     * @return 
     */
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
    
    /**
     * для добавления к цепочке ещё одного звена
     * @param chain строка с разделителями №1
     * @param link добавляемое звено
     * @return строку с разделителями, в которой элементов на один больше
     */
    String increaseChain (String chain, String link){
        return chain+Statics.delimiter_1+link;
    }
    
    /**
     * для удаления последнего элемента
     * @param chain строка с разделителями №1
     * @return строку, где последнего элемента нет
     */
    String cropChain (String chain){
        return getElemSplit_cropLast(chain, Statics.delimiter_1);
    }
    
    /**
     * для выдачи n-ного параметра из последнего набора цепи параметров
     * @param n номер искомого параметра (с 0-го)
     * @return n-ный параметр из последнего набора в цепи
     */
    public String getParam (int n){
        return getLink(paramchain).split(Statics.delimiter_3)[n];
    }
    
    /**
     * открывает всплывающую форму для выбора из общего списка
     * @param ids какие сущности (по ИДам) выкинуть из предлагаемого списка
     */
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
    
    /**
     * для возвращения коллекции, из которой выброшены некоторые элементы
     * выброшенные элементы берутся из строки с параметрами
     * @return коллекцию сущностей
     */
    public abstract Collection<O> getList_objects_exclude();
    
    /**
     * для реализации предыдущего метода малой кровью
     * @param list коллекция без выбросов
     * @param c вид ИДов элементов, которые будут выброшены
     * @return коллекцию сущностей
     */
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
    
    /**
     * для закрытия всплывающей формы выбора с возвратом выбранной сущности
     * @param <O>
     * @param item 
     */
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
